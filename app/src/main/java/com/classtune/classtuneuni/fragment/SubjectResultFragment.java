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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ComResultAdapter;
import com.classtune.classtuneuni.adapter.SubjectResultAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.SubjectResultModel;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.result.CourseResultData;
import com.classtune.classtuneuni.result.StCourseResultResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectResultFragment extends Fragment implements SubjectResultAdapter.ItemListener {
    RecyclerView recyclerView;
    private List<SubjectResultModel> resultList;
    LinearLayoutManager linearLayoutManager;
    SubjectResultAdapter subjectResultAdapter;
    UIHelper uiHelper;
    private TextView grade, totalObtained, hundred;
    int totalSt;
    String courseOfferSectionId = "";

    public SubjectResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);

        if (getArguments().getString("id") != null)
            courseOfferSectionId = getArguments().getString("id");
        uiHelper = new UIHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        grade = view.findViewById(R.id.grade_tv);
        totalObtained = view.findViewById(R.id.totalObtained);
        hundred = view.findViewById(R.id.hundred);

        resultList = new ArrayList<>();



        subjectResultAdapter = new SubjectResultAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subjectResultAdapter);

        if(courseOfferSectionId.isEmpty()) {
            callSubjectResultApi(GlobalOfferedCourseSectionId);
        }
        else {
            callSubjectResultApi(courseOfferSectionId);
        }

        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    clearfield();
                    callSubjectResultApi(GlobalOfferedCourseSectionId);

                }
                else {
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                }
            }
        });
    }


    @Override
    public void onItemClick(SubjectResultModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        AssignmentDetailsFragment assignmentDetailsFragment = new AssignmentDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, assignmentDetailsFragment, "assignmentDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callSubjectResultApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSubjectResult(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StCourseResultResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StCourseResultResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StCourseResultResponse stCourseResultResponse = value.body();
                        clearfield();
                        subjectResultAdapter.clear();
                        if (stCourseResultResponse.getStatus().getCode() == 200) {
                            totalSt = 0;
//
                            resultList = stCourseResultResponse.getData().getAssessments();
                            if(resultList!=null)
                            for(int i = 0; i<resultList.size(); i++){
                                totalSt = totalSt + resultList.get(i).getWeight();
                            }
                            populateData(stCourseResultResponse.getData(), totalSt);
                            subjectResultAdapter.addAllData(resultList);
//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            subjectResultAdapter.clear();
                           // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        subjectResultAdapter.clear();
                       // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void populateData(CourseResultData data, int totalSt) {
        if(data.getGrade()!=null && data.getGrade().getGrade()!=null)
            grade.setText(data.getGrade().getGrade());

        if(data.getTotalMarks()!=null)
            totalObtained.setText("" + data.getTotalMarks());
        hundred.setText(""+ totalSt + "%");
    }


    private void clearfield(){
        grade.setText("");
        totalObtained.setText("");
        hundred.setText("");
    }
}
