package com.classtune.classtuneuni.fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AssignmentStudentListAdapter;
import com.classtune.classtuneuni.adapter.CourseStudentSectionAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSubmitData;
import com.classtune.classtuneuni.assignment.AssignmentSubmitedListResponse;
import com.classtune.classtuneuni.assignment.Participant;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.OfferedCourseDetails;
import com.classtune.classtuneuni.course_resonse.OfferedCourseResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherStudentListFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fabAdd;
    String assignmentId = "";
    AssignmentStudentListAdapter assignmentStudentListAdapter;
    RecyclerView recyclerView;
    private List<Participant> participantList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    private TextView course, section, assignDate, dueDate, status, submited, total;

    public TeacherStudentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_student_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignmentId = getArguments().getString("assignmentId");
        uiHelper = new UIHelper(getActivity());

        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerView);

        course = view.findViewById(R.id.course);
        section = view.findViewById(R.id.section);
        assignDate = view.findViewById(R.id.assignedDate);
        dueDate = view.findViewById(R.id.dueDate);
        status = view.findViewById(R.id.status);
        submited = view.findViewById(R.id.submited);
        total = view.findViewById(R.id.total);

        participantList = new ArrayList<>();

        assignmentStudentListAdapter = new AssignmentStudentListAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assignmentStudentListAdapter);

        callAssignmentSubmittedList(assignmentId);
    }


    private void callAssignmentSubmittedList(String assignmentId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAssigSubmitedStudentList(AppSharedPreference.getApiKey(), assignmentId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<AssignmentSubmitedListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<AssignmentSubmitedListResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        AssignmentSubmitedListResponse assignmentSubmitedListResponse = value.body();
                        if (assignmentSubmitedListResponse.getCode() == 200) {
                            populateData(assignmentSubmitedListResponse.getData());
                            assignmentStudentListAdapter.addAllData(assignmentSubmitedListResponse.getData().getParticipants());

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

    private void populateData(AssignmentSubmitData assignmentSubmitData) {
       // course_offers_id = courseOffer.getId();
        if(assignmentSubmitData.getCourseCode()!=null)
        course.setText(assignmentSubmitData.getCourseCode());
        if(assignmentSubmitData.getCourseCode()!=null)
        section.setText(assignmentSubmitData.getSection());
        if(assignmentSubmitData.getAssignDate()!=null) {
            String str = assignmentSubmitData.getAssignDate();
            String parts[] = str.split(" ");
            if(parts[0].contains("-"))
                try {
                    assignDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        if(assignmentSubmitData.getDueDate()!=null)

            try {
                dueDate.setText(AppUtility.getDateString(assignmentSubmitData.getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
            } catch (Exception e) {
                e.printStackTrace();
            }

        if(assignmentSubmitData.getTotalSubmission()!=null)
            submited.setText(""+ assignmentSubmitData.getTotalSubmission());
        if(assignmentSubmitData.getTotalStudents()!=null)
            total.setText(""+ assignmentSubmitData.getTotalStudents());
      //  if(assignmentSubmitData.get)

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
//                Fragment fragment = new TeacherAddCourseFragment();
//                gotoFragment(fragment, "teacherAddCourseFragment");
                break;
        }
    }
}
