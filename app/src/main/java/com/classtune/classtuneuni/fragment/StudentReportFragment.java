package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AssignmentStudentListAdapter;
import com.classtune.classtuneuni.adapter.StudentReportAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.assignment.Participant;
import com.classtune.classtuneuni.attendance.AttendanceReportResponse;
import com.classtune.classtuneuni.attendance.StAttendanceData;
import com.classtune.classtuneuni.exam.ExamPolicyResponse;
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

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentReportFragment extends Fragment {

    StudentReportAdapter studentReportAdapter;
    RecyclerView recyclerView;
    private List<StAttendanceData> stAttendanceDataList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    public StudentReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        stAttendanceDataList = new ArrayList<>();

        studentReportAdapter = new StudentReportAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(studentReportAdapter);

        ((MainActivity) Objects.requireNonNull(getActivity())).tabRl.setVisibility(View.VISIBLE);
        ((MainActivity) Objects.requireNonNull(getActivity())).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (((MainActivity) Objects.requireNonNull(getActivity())).mTabHost != null) {
                    int pos = ((MainActivity) getActivity()).mTabHost.getCurrentTab();

                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                    callStudentReport(GlobalOfferedCourseSectionId);
                }
            }
        });



    }
    private boolean _hasLoadedOnce= false;
    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);


        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                callStudentReport(GlobalOfferedCourseSectionId);
                _hasLoadedOnce = true;
            }
        }
    }

    private void callStudentReport(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStudentReport(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<AttendanceReportResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<AttendanceReportResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        AttendanceReportResponse attendanceReportResponse = value.body();
                        studentReportAdapter.clear();
                        if (attendanceReportResponse.getStatus().getCode() == 200) {
                            stAttendanceDataList = attendanceReportResponse.getData().getAttendance();

                            if(stAttendanceDataList != null &&stAttendanceDataList.size()>0)
                            studentReportAdapter.addAllData(stAttendanceDataList);
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
}
