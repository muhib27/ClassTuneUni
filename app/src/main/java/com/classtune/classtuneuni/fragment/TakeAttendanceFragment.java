package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.TakeAttendanceAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.course_resonse.CorsSecStudentResponse;
import com.classtune.classtuneuni.exam.ExamPolicyResponse;
import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.model.STAttendanceModel;
import com.classtune.classtuneuni.model.Student;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class TakeAttendanceFragment extends Fragment implements TakeAttendanceAdapter.ItemListener, View.OnClickListener {
    TabHost mTabHost, mTabHost1;
    RelativeLayout tabRl;
    RecyclerView recyclerView;
    private ArrayList<Student> studentList;
    LinearLayoutManager linearLayoutManager;
    TakeAttendanceAdapter takeAttendanceAdapter;
    UIHelper uiHelper;
    List<String> absentList;
    String absent = "";
    private Button save, reset;
    private TextView classNo, classDate;

    private String classDateFormatServerString = "", classAttendanceFormatServerString = "";

//    private MyFragmentTabHost mTabHostAttendanceTeacher;

    public TakeAttendanceFragment() {
        // Required empty public constructor
    }
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_take_attendance, container, false);
//        mTabHostAttendanceTeacher = rootView
//                .findViewById(R.id.tabhost_attendance_teacher);
//
//        initialiseTabHost(savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());
        ((MainActivity) Objects.requireNonNull(getActivity())).tabRl.setVisibility(View.VISIBLE);
        ((MainActivity) Objects.requireNonNull(getActivity())).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (((MainActivity) Objects.requireNonNull(getActivity())).mTabHost != null) {
                    int pos = ((MainActivity) getActivity()).mTabHost.getCurrentTab();

                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                    callSectionStudentList(GlobalOfferedCourseSectionId, classDateFormatServerString);
                }
            }
        });

        absentList = new ArrayList<>();
        classNo = view.findViewById(R.id.classNo);
        classDate = view.findViewById(R.id.date);

        classDate.setText(getCurrentDate());

        classDate.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        save = view.findViewById(R.id.save_btn);
        reset = view.findViewById(R.id.reset_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                absent = "";
                for(int i=0; i<takeAttendanceAdapter.mValues.size();i++){
                   if(takeAttendanceAdapter.mValues.get(i).getStatus().equals("0"))
                       if(!absent.isEmpty())
                           absent = absent +  "|" +  takeAttendanceAdapter.mValues.get(i).getStudentId();
                       else
                           absent = absent + takeAttendanceAdapter.mValues.get(i).getStudentId();

                    //if(studentList.get(i).getAbsent().equals())
                }
                Log.v("TAG", absent);
                callTakeAttendance(GlobalOfferedCourseSectionId, absent);

            }
        });


        studentList = new ArrayList<>();




        takeAttendanceAdapter = new TakeAttendanceAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(takeAttendanceAdapter);

        callSectionStudentList(GlobalOfferedCourseSectionId, classDateFormatServerString);
    }


    @Override
    public void onItemClick(STAttendanceModel item, int pos) {

    }


    private void callSectionStudentList(String courseId, String classDateFormatServerString) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSectionStudentList(AppSharedPreference.getApiKey(), courseId, classDateFormatServerString)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CorsSecStudentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CorsSecStudentResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        CorsSecStudentResponse corsSecStudentResponse = value.body();
                        takeAttendanceAdapter.clear();
                        if (corsSecStudentResponse.getStatus().getCode() == 200) {
//                            populateData(corsSecStudentResponse.getData().getSectionCourse());
                            if(corsSecStudentResponse.getData().getClassNo()!=null)
                            classNo.setText(""+corsSecStudentResponse.getData().getClassNo());
                            takeAttendanceAdapter.addAllData(corsSecStudentResponse.getData().getStudents());

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });
    }


    private void callTakeAttendance(String globalOfferedCourseSectionId, String absentSt) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().TakeAttendance(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, absentSt, classDateFormatServerString)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CommonStatus>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CommonStatus> value) {
                        uiHelper.dismissLoadingDialog();

                        CommonStatus commonStatus = value.body();
                        if (commonStatus.getCode() == 200) {

                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
      //  System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd, MMM yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat dateAtt = new SimpleDateFormat(AppUtility.DATE_FORMAT_SERVER);
        classAttendanceFormatServerString = dateAtt.format(c);

        return formattedDate;
    }
    private void showDatepicker() {
        DatePickerFragment picker = new DatePickerFragment();
        picker.setCallbacks(endDatePickerCallback);
        picker.show(getFragmentManager(), "datePicker");
    }

    DatePickerFragment.DatePickerOnSetDateListener endDatePickerCallback = new DatePickerFragment.DatePickerOnSetDateListener() {

        @Override
        public void onDateSelected(int month, String monthName, int day,
                                   int year, String dateFormatServer, String dateFormatApp,
                                   Date date) {
            // TODO Auto-generated method stub
            classDate.setText(dateFormatApp);
            // chooseEndDateTextView.setText(dateFormatApp);
            classDateFormatServerString = dateFormatServer;
            classAttendanceFormatServerString = dateFormatServer;
            callSectionStudentList(GlobalOfferedCourseSectionId, classDateFormatServerString);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.date:
                showDatepicker();
                break;
        }
    }


//    private void initialiseTabHost(Bundle args) {
//
//        TabInfo tabInfo = null;
//        mTabHostAttendanceTeacher.setup(getActivity(),
//                getChildFragmentManager(),
//                R.id.realtabcontent_attendance_teacher);
//        MyFragmentTabHost.TabSpec spec = mTabHostAttendanceTeacher
//                .newTabSpec("Tab");
//        spec.setIndicator(getIndicatorView("Tab"));
//        addTab(this.mTabHostAttendanceTeacher, spec, (tabInfo = new TabInfo(
//                "Tab",
//                TakeRollAttendanceFragment.class, args)));
//
//        spec = mTabHostAttendanceTeacher
//                .newTabSpec("Tab2");
//        spec.setIndicator(getIndicatorView(getString(R.string.title_roll_call_tab)));
//        addTab(this.mTabHostAttendanceTeacher, spec, (tabInfo = new TabInfo(
//                "Tab2",
//                ExamListFragment.class, args)));
//
//        Log.e("SURRENT_TAB", "is: " + mTabHostAttendanceTeacher.getCurrentTab());
//
//        // Default to first tab
//        // this.onTabChanged(AppConstant.TAB_ROLLCALL_TEACHER);
//        //
//        // mTabHostAttendanceTeacher.setOnTabChangedListener(this);
//
//        mTabHostAttendanceTeacher.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//
//            @Override
//            public void onTabChanged(String s) {
//
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                int width = displaymetrics.widthPixels;
//
//                final TabWidget tabWidget = mTabHostAttendanceTeacher.getTabWidget();
//                final int screenWidth = width;
//                final int leftX = tabWidget.getChildAt(mTabHostAttendanceTeacher.getCurrentTab()).getLeft();
//                int newX = 0;
//
//                newX = leftX + (tabWidget.getChildAt(mTabHostAttendanceTeacher.getCurrentTab()).getWidth() / 2) - (screenWidth / 2);
//                if (newX < 0) {
//                    newX = 0;
//                }
//                //horizontalScrollView.smoothScrollTo(newX, 0);
//
//            }
//        });
//
//
//    }
//
//    /**
//     *
//     */
//    private class TabInfo {
//        private String tag;
//        private Class<?> clss;
//        private Bundle args;
//        private Fragment fragment;
//
//        TabInfo(String tag, Class<?> clazz, Bundle args) {
//            this.tag = tag;
//            this.clss = clazz;
//            this.args = args;
//        }
//    }
//
//    /**
//     *
//     *
//     */
//    class TabFactory implements TabHost.TabContentFactory {
//
//        private final Context mContext;
//
//        /**
//         * @param context
//         */
//        public TabFactory(Context context) {
//            mContext = context;
//        }
//
//        /**
//         * (non-Javadoc)
//         *
//         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
//         */
//        public View createTabContent(String tag) {
//            View v = new View(mContext);
//            v.setMinimumWidth(0);
//            v.setMinimumHeight(0);
//            return v;
//        }
//
//    }
//
//    /**
//     * @param activity
//     * @param tabHost
//     * @param tabSpec
//     * @param clss
//     * @param args
//     */
//    private void addTab(MyFragmentTabHost tabHost,
//                        MyFragmentTabHost.TabSpec tabSpec, TabInfo tabInfo) {
//        // Attach a Tab view factory to the spec
//        tabSpec.setContent(new TabFactory(getActivity()));
//        String tag = tabSpec.getTag();
//
//        // Check to see if we already have a fragment for this tab, probably
//        // from a previously saved state. If so, deactivate it, because our
//        // initial state is that a tab isn't shown.
//        tabInfo.fragment = getChildFragmentManager().findFragmentByTag(tag);
//        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
//            FragmentTransaction ft = getChildFragmentManager()
//                    .beginTransaction();
//            ft.detach(tabInfo.fragment);
//            ft.commit();
//            getChildFragmentManager().executePendingTransactions();
//        }
//
//        tabHost.addTab(tabSpec, tabInfo.clss, null);
//    }
//
//    private View getIndicatorView(String text) {
//        View tabIndicator = LayoutInflater.from(getActivity()).inflate(
//                R.layout.tab_indicator_attendance,
//                this.mTabHostAttendanceTeacher.getTabWidget(), false);
//        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
//        title.setText(text);
//        return tabIndicator;
//    }

}
