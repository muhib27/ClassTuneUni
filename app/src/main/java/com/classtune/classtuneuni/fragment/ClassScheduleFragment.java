package com.classtune.classtuneuni.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ClassScheduleAdapter;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.class_schedule.StClsScheduleResponse;
import com.classtune.classtuneuni.model.ClassScheduleModel;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassScheduleFragment extends Fragment implements ClassScheduleAdapter.ItemListener {

    UIHelper uiHelper;
    RecyclerView recyclerView;
    private List<Routine> scheduleList;
    LinearLayoutManager linearLayoutManager;
    ClassScheduleAdapter classScheduleAdapter;
    private String TAB = "";
    public TabHost tabHost;
    public RelativeLayout tabRl;
    String[] tabs;

    private static String LocalOfferedCourseSectionId = "";

    public ClassScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);

        TAB = AppSharedPreference.getStTabString();
        tabHost = (TabHost)view.findViewById(android.R.id.tabhost);

        tabHost.setup();



        tabRl = view.findViewById(R.id.tab);

        if(!TAB.isEmpty())
        {
            tabs = TAB.split("\\|");
            stAddSection(tabs);
            Log.v("dd", ""+tabs.length);
        }



        uiHelper = new UIHelper(getActivity());

//        ((MainActivity) Objects.requireNonNull(getActivity())).tabRl.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerView);

        scheduleList = new ArrayList<>();


        classScheduleAdapter = new ClassScheduleAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(classScheduleAdapter);

        //if(AppSharedPreference.getUserType().equals("3")) {
        callStAllClassSchedule();
       // }

//        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String s) {
//                if (((MainActivity) Objects.requireNonNull(getActivity())).mTabHost != null) {
//                    int pos = ((MainActivity) getActivity()).mTabHost.getCurrentTab();
//                    if (AppSharedPreference.getUserType().equals("3")) {
//                        StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
//                        GlobalCourseId = ss.getCourseCode();
//                        GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
//                        callStClassSchedule(GlobalOfferedCourseSectionId);
//
//                    } else {
//                        AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
//                        GlobalCourseId = ss.getCourseId();
//                        GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
//                        callStClassSchedule(GlobalOfferedCourseSectionId);
//                    }
//                }
//            }
//        });
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int pos = tabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
//                    StCourseSection ss = AppSharedPreference.getStUserTab(tabId, pos);
//                    GlobalCourseId = ss.getCourseCode();
//                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    if(pos == 0)
                    {
                        LocalOfferedCourseSectionId = "";
                        callStAllClassSchedule();
                    }
                    else {
                        LocalOfferedCourseSectionId = tabHost.getCurrentTabTag().toString();
                        callStClassSchedule(LocalOfferedCourseSectionId);
                    }



                }
                else {
//                    AssignmentSection ss = AppSharedPreference.getUserTab(tabId, pos);
//                    GlobalCourseId = ss.getCourseId();
//                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                }

            }
        });


    }


    @Override
    public void onItemClick(ClassScheduleModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
      //  loadFragment();
    }

    private void loadFragment() {
        // load fragment
        ExamDetailsFragment examDetailsFragment = new ExamDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, examDetailsFragment, "examDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callStClassSchedule(String courseId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStClassSchedule(AppSharedPreference.getApiKey(), courseId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StClsScheduleResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StClsScheduleResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StClsScheduleResponse stClsScheduleResponse = value.body();
                        classScheduleAdapter.clear();
                        if (stClsScheduleResponse.getStatus().getCode() == 200) {
                            //populateData(studentAttendanceResponse.getData());
                            //scheduleList = new ArrayList<>();
                            classScheduleAdapter.addAllData(stClsScheduleResponse.getData().getRoutine(), "");

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
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

    private void callStAllClassSchedule() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStAllClassSchedule(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StClsScheduleResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StClsScheduleResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StClsScheduleResponse stClsScheduleResponse = value.body();
                        classScheduleAdapter.clear();
                        if (stClsScheduleResponse.getStatus().getCode() == 200) {
                            //populateData(studentAttendanceResponse.getData());
                            //scheduleList = new ArrayList<>();
                            classScheduleAdapter.addAllData(stClsScheduleResponse.getData().getRoutine(), "All");

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
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

    private void stAddSection(String[] tabArray) {
        setupStTab(new TextView(getActivity()), "All");
        for (int i = 0; i < tabArray.length ; i++) {
            setupStTab(new TextView(getActivity()), tabArray[i]);

        }
//        AppSharedPreference.setStTabString(ST_TAB_STRING);
//        AppSharedPreference.setStUserTab(sections.get(0), 0);
//        tabRl.setVisibility(View.GONE);
//        if(sections.size()>0)
//            GlobalOfferedCourseSectionId = sections.get(0).getCourseOfferSectionId();


    }

    private void setupStTab(final View view, String s) {
        if(s.equals("All")){
            View tabview = createStAllTabView(tabHost.getContext(), s);
            TabHost.TabSpec setContent = tabHost.newTabSpec(s).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
                public View createTabContent(String tag) {
                    return view;
                }
            });
            tabHost.addTab(setContent);
        }
        else {
            String[] parts = s.split("/");

            View tabview = createStTabView(tabHost.getContext(), parts);
            TabHost.TabSpec setContent = tabHost.newTabSpec(parts[2]).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
                public View createTabContent(String tag) {
                    return view;
                }
            });
            tabHost.addTab(setContent);
        }

    }

    private static View createStTabView(final Context context, String[] parts) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(parts[0]);
        tv.setTag(parts[2]);
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(parts[1]);
        tvsmall.setTag(parts[2]);
        view.setTag(parts[0]);
        return view;
    }

    private static View createStAllTabView(final Context context, String parts) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_all, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(parts);
        tv.setTag(parts);
//        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
//        tvsmall.setText("");
//        tvsmall.setTag(parts);
//        view.setTag(parts);
        return view;
    }

}
