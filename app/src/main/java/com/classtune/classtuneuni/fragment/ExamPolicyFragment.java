package com.classtune.classtuneuni.fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ExamPolicyAdapter;
import com.classtune.classtuneuni.adapter.SubjectResultAdapter;
import com.classtune.classtuneuni.exam.ExamPolicyResponse;
import com.classtune.classtuneuni.exam.Policy;
import com.classtune.classtuneuni.model.SubjectResultModel;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.result.CourseResultData;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamPolicyFragment extends Fragment implements SubjectResultAdapter.ItemListener{
    RecyclerView recyclerView;
    private List<Policy> policyList;
    LinearLayoutManager linearLayoutManager;
    ExamPolicyAdapter examPolicyAdapter;
    UIHelper uiHelper;
    private TextView subCode, totalObtained, hundred, edit;
    int totalSt;
    String subCodeSt = "";
    List<String> editedList;

    public ExamPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_policy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (((MainActivity) Objects.requireNonNull(getActivity())).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity) getActivity()).tabRl.setVisibility(View.GONE);

        subCodeSt = getArguments().getString("sub_code");

        editedList = new ArrayList<>();

        uiHelper = new UIHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
//        edit = view.findViewById(R.id.edit);
//        edit.setOnClickListener(this);
        subCode = view.findViewById(R.id.subCode);
        subCode.setText(subCodeSt);
//        totalObtained = view.findViewById(R.id.totalObtained);
//        hundred = view.findViewById(R.id.hundred);

        policyList = new ArrayList<>();


        examPolicyAdapter = new ExamPolicyAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(examPolicyAdapter);



        callSubjectPolicyApi(GlobalOfferedCourseSectionId);

//        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String s) {
//                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
//                if(AppSharedPreference.getUserType().equals("3"))
//                {
//                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
//                    GlobalCourseId = ss.getCourseCode();
//                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
//                    callSubjectResultApi(GlobalOfferedCourseSectionId);
//
//                }
//                else {
//                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
//                    GlobalCourseId = ss.getCourseId();
//                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
//                }
//            }
//        });
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

    private void callSubjectPolicyApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSubjectpolicy(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ExamPolicyResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ExamPolicyResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        ExamPolicyResponse examPolicyResponse = value.body();
                        examPolicyAdapter.clear();
                        if (examPolicyResponse.getStatus().getCode() == 200) {
                            policyList = examPolicyResponse.getData().getPolicies();

                            examPolicyAdapter.addAllData(policyList, false);
                            //examPolicyAdapter.disableEditing();

//                            Log.v("tt", noticeList.toString());
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

    private void populateData(CourseResultData data, int totalSt) {
//        if(data.getGrade().getGrade()!=null)
//            grade.setText(data.getGrade().getGrade());

//        if(data.getTotalMarks()!=null)
//            totalObtained.setText("" + data.getTotalMarks());
//        hundred.setText(""+ totalSt + "%");
    }



//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.edit:
//                String st = "";
//                if (edit.getText().toString().equals("Edit")) {
//                    edit.setText("Save");
//                    examPolicyAdapter.clear();
//                    examPolicyAdapter.addAllData(policyList, true);
//                } else {
//                    edit.setText("Edit");
//                    for(int i = 0; i<examPolicyAdapter.mValues.size(); i++)
//                    {
//                        st = examPolicyAdapter.mValues.get(i).getName() + "|" +   examPolicyAdapter.mValues.get(i).getPercentage();
//                        editedList.add(st);
//                    }
//                    examPolicyAdapter.clear();
//                    examPolicyAdapter.addAllData(policyList, true);
//                }
//                break;
//        }
//    }
}
