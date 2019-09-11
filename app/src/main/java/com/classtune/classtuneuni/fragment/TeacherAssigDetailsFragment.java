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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AttachFromTeacherAdapter;
import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.model.AssignmentModel;
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
public class TeacherAssigDetailsFragment extends Fragment implements View.OnClickListener {

    AttachFromTeacherAdapter attachFromTeacherAdapter;

    UIHelper uiHelper;
    String assignmentId = "";
    private TextView title, instructor, course, dueDate, assignDate, status, total, obtained, description;
    private Button viewSubmission;
    String id = "";
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private List<AssinmentAttachment> attachmentModelList;

    public TeacherAssigDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recyclerView);
        description = view.findViewById(R.id.description);

        attachmentModelList = new ArrayList<>();
        uiHelper = new UIHelper(getActivity());
        assignmentId = getArguments().getString("assignmentId");
        title = view.findViewById(R.id.title);
        instructor = view.findViewById(R.id.name);
        course = view.findViewById(R.id.course);
        dueDate = view.findViewById(R.id.dueDate);
        assignDate = view.findViewById(R.id.assignedDate);
        total = view.findViewById(R.id.total);
        obtained = view.findViewById(R.id.totalObtained);
        status = view.findViewById(R.id.status);
        viewSubmission = view.findViewById(R.id.viewSubmission);
        viewSubmission.setOnClickListener(this);


        attachFromTeacherAdapter = new AttachFromTeacherAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(attachFromTeacherAdapter);


        callStAssignmentViewApi(assignmentId);
    }

    private void callStAssignmentViewApi(String assignmentId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStAssignmentDetails(AppSharedPreference.getApiKey(), assignmentId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TeacherAssignmentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TeacherAssignmentResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        TeacherAssignmentResponse teacherAssignmentResponse = value.body();
                        if (teacherAssignmentResponse.getStatus().getCode() == 200) {
                            populateData(teacherAssignmentResponse.getData().getAssignment().getAssignment(), teacherAssignmentResponse.getData().getAssignment().getSubmission(), teacherAssignmentResponse.getData().getAssignment().getMark());
                            attachmentModelList = new ArrayList<>();
                            attachmentModelList = teacherAssignmentResponse.getData().getAssignment().getAttachments();

                            attachFromTeacherAdapter.addAllData(attachmentModelList);


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

    private void populateData(AssignmentModel assignment, Integer submission, Integer marks) {
        if(assignment.getTitle()!=null)
            title.setText(assignment.getTitle());
        if(assignment.getDescription()!=null)
            description.setText(assignment.getDescription());
        if(assignment.getInstructor()!=null)
            instructor.setText(assignment.getInstructor());
        if(assignment.getCourseCode()!=null)
            course.setText(assignment.getCourseCode());
        if(assignment.getAssignDate()!=null) {
            String str = assignment.getAssignDate();
            String parts[] = str.split(" ");
            if(parts[0].contains("-"))
            assignDate.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
        }
        if(assignment.getDueDate()!=null)
            dueDate.setText(AppUtility.getDateString(assignment.getDueDate(), AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
        if(submission >0) {
            viewSubmission.setVisibility(View.VISIBLE);
            status.setText("Submited");
        }
        else {
            viewSubmission.setVisibility(View.GONE);
            status.setText("Not Submited");

        }
        if(assignment.getMaxMarks()!=null)
            total.setText(assignment.getMaxMarks());

        if(assignment.getMaxMarks()!=null && marks>0)
            obtained.setText(""+marks);

        id = assignment.getId();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.viewSubmission:
                Fragment fragment = new AssiDetailsViewFragment();
                gotoFragment(fragment, "assiDetailsViewFragment", id);

                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag, String id) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("assignmentId", assignmentId);
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
