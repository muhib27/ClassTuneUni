package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.CourseStudentSectionAdapter;
import com.classtune.classtuneuni.adapter.EnrollCodeAdapter;
import com.classtune.classtuneuni.course_resonse.CorsSecStudentResponse;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.CourseSctionData;
import com.classtune.classtuneuni.course_resonse.OfferedCourseDetails;
import com.classtune.classtuneuni.course_resonse.OfferedCourseResponse;
import com.classtune.classtuneuni.course_resonse.SectionCourse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
import com.google.gson.JsonElement;

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
public class SectionStudentListFragment extends Fragment implements View.OnClickListener {

    String course_offer_sections_id ="";
    static String course_offers_id = "";
    RecyclerView recyclerView;
    private List<Course> courseList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    CourseStudentSectionAdapter courseStudentSectionAdapter;
    FloatingActionButton fabAdd;
    TextView courseName, code, credit, duration, section;

    public SectionStudentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_student_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        course_offer_sections_id = getArguments().getString("course_offer_sections_id");

        courseName = view.findViewById(R.id.courseName);
        code = view.findViewById(R.id.code);
        credit = view.findViewById(R.id.credit);
        duration = view.findViewById(R.id.duration);
        section = view.findViewById(R.id.section);

//        fabAdd = view.findViewById(R.id.fab_add);
//        fabAdd.setOnClickListener(this);
        uiHelper = new UIHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);

        courseList = new ArrayList<>();


        courseStudentSectionAdapter = new CourseStudentSectionAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseStudentSectionAdapter);

        callNoticeDetails(course_offer_sections_id);
    }


    private void callNoticeDetails(String courseId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getCourseStudentSection(AppSharedPreference.getApiKey(), courseId)

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
                        if (corsSecStudentResponse.getStatus().getCode() == 200) {
                            populateData(corsSecStudentResponse.getData().getSectionCourse());
                            courseStudentSectionAdapter.addAllData(corsSecStudentResponse.getData().getStudents());

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.fab_add:
//                break;
        }
    }



    private void populateData(SectionCourse data) {
//        course_offers_id = courseOffer.getId();
        courseName.setText(data.getTitle());
        code.setText(data.getCode());
        credit.setText(data.getCreditPoint());
        duration.setText(data.getStartDate() + " - " + data.getEndDate());
        //section.setText(data.g);

    }


}
