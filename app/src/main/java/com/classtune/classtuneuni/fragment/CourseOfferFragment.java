package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.course_resonse.CourseOfferSectionResponse;
import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseOfferFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fab;
    View v;
    ViewGroup main;
    private List<String> sectionList;
    Button addcourse;
    TextView startDate, endDate, name, code;
    private String startDateFormatServerString = "";
    private String endDateFormatServerString = "";
    String courseId = "", courseName = "", courseCode = "", sectionSt = "";
    UIHelper uiHelper;

    public CourseOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_course_offer, container, false);

        uiHelper = new UIHelper(getActivity());

        courseId = getArguments().getString("courseId");
        courseName = getArguments().getString("courseName");
        courseCode = getArguments().getString("courseCode");
       // main = (ViewGroup) v.findViewById(R.id.insert_point);
        main = v.findViewById(R.id.sectionLl);
        addcourse = v.findViewById(R.id.addCourse);
        addcourse.setOnClickListener(this);

        startDate = v.findViewById(R.id.startDate);
        endDate = v.findViewById(R.id.endDate);

        name = v.findViewById(R.id.courseName);
        code = v.findViewById(R.id.courseCode);

        if (!courseName.isEmpty())
            name.setText(courseName);

        if (!courseCode.isEmpty())
            code.setText(courseCode);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);

        sectionList = new ArrayList<>();

        callSectionListApi();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.addBtn);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addBtn:
//                addSection();
                showDialog();
                break;

            case R.id.addCourse:
                sectionList = getTotalSection();
                sectionSt = "";
                for (int i = 0; i < sectionList.size(); i++) {
                    if ((sectionList.size() - 1) == i)
                        sectionSt = sectionSt + sectionList.get(i);
                    else
                        sectionSt = sectionSt + sectionList.get(i) + "|";
                }
                if (!startDateFormatServerString.isEmpty() && !endDateFormatServerString.isEmpty() && !sectionSt.isEmpty())
                    callOfferCourseApi(courseId, startDateFormatServerString, endDateFormatServerString, sectionSt);

                break;
            case R.id.startDate:
                showStartDatepicker();
                break;

            case R.id.endDate:
                showEndDatepicker();
                break;
        }
    }

    private void showStartDatepicker() {
        DatePickerFragment picker = new DatePickerFragment();
        picker.setCallbacks(startDatePickerCallback);
        picker.show(getFragmentManager(), "startdatePicker");
    }

    DatePickerFragment.DatePickerOnSetDateListener startDatePickerCallback = new DatePickerFragment.DatePickerOnSetDateListener() {

        @Override
        public void onDateSelected(int month, String monthName, int day,
                                   int year, String dateFormatServer, String dateFormatApp,
                                   Date date) {
            // TODO Auto-generated method stub
            startDate.setText(dateFormatApp);
            //chooseStartDateTextView.setText(dateFormatApp);
            startDateFormatServerString = dateFormatServer;
        }
    };

    private void showEndDatepicker() {
        DatePickerFragment picker = new DatePickerFragment();
        picker.setCallbacks(endDatePickerCallback);
        picker.show(getFragmentManager(), "enddatePicker");
    }

    DatePickerFragment.DatePickerOnSetDateListener endDatePickerCallback = new DatePickerFragment.DatePickerOnSetDateListener() {

        @Override
        public void onDateSelected(int month, String monthName, int day,
                                   int year, String dateFormatServer, String dateFormatApp,
                                   Date date) {
            // TODO Auto-generated method stub
            endDate.setText(dateFormatApp);
            // chooseEndDateTextView.setText(dateFormatApp);
            endDateFormatServerString = dateFormatServer;
        }
    };

//    private List<String> getTotalSection() {
//        String text = "";
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < main.getChildCount(); i++) {
//            View nextChild = main.getChildAt(i);
//            //if(nextChild.getId() == i){
//            text = ((TextView) nextChild).getText().toString();
//            //  }
//            list.add(text);
//
//        }
//
//        return list;
//    }

    private List<String> getTotalSection() {
        int text = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < main.getChildCount(); i++) {
            View nextChild = main.getChildAt(i);
            //if(nextChild.getId() == i){
            if (((CheckBox) nextChild).isChecked()) {
                text = ((CheckBox) nextChild).getId();
                //  }
                list.add(String.valueOf(text));
            }

        }

        return list;
    }

    static int count = 0;
    TextView textView;
    View view1;
    CheckBox checkBox;

    private void addSection(List<Section> sectionName) {
        main.removeAllViews();
        for (int i = 0; i < sectionName.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.checkbox_section_view, main, false);
            checkBox = view1.findViewById(R.id.cb);
            // ll = (LinearLayout) view1.findViewById(R.id.ll);
            checkBox.setText(sectionName.get(i).getName());
            checkBox.setId(Integer.parseInt(sectionName.get(i).getId()));
            main.addView(checkBox, i);
        }
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.section_view, null);
//
//        textView = view.findViewById(R.id.i_am_id);
//        textView.setText(sectionName);
//        textView.setId(count);
//
//        main.addView(textView);
//        count++;
//
//        //  Toast.makeText(getActivity(), ""+ main.getChildCount(), Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
// Set the title
        // dialog.setTitle("Dialog Title");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

// inflate the layout
        dialog.setContentView(R.layout.new_section_create_dialog);

// Set the dialog text -- this is better done in the XML
        final EditText text = dialog.findViewById(R.id.sectionName);
        Button add = dialog.findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text.getText() != null && text.getText().length() > 0)
                    callAddSectionApi(text.getText().toString());
                //addSection(text.getText().toString());
                dialog.dismiss();
            }
        });


// Display the dialog
        dialog.show();

    }


    private void callOfferCourseApi(final String courseId, final String startDate, final String endDate, String sections) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().offerCourse(courseId, startDate, endDate, sections, AppSharedPreference.getApiKey())

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


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (commonStatus.getCode() == 200) {
                            //    passwordChangeDialog();

                            Fragment fragment = new CourseListFragment();
                            gotoFragment(fragment, "courseListFragment");
                        } else
                            uiHelper.dismissLoadingDialog();

                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void callAddSectionApi(String name) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().addSection(AppSharedPreference.getApiKey(), name)

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
                            callSectionListApi();

                         //   Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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

    private void callSectionListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSectionList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CourseOfferSectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CourseOfferSectionResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        CourseOfferSectionResponse courseOfferSectionResponse = value.body();
                        if (courseOfferSectionResponse.getStatus().getCode() == 200) {
                            addSection(courseOfferSectionResponse.getData().getSections());

                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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


    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.commit();
    }



}
