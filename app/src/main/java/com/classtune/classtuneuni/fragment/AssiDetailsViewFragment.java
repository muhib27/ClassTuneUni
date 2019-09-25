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
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AttachmentAdapter;
import com.classtune.classtuneuni.adapter.CourseListAdapter;
import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.model.AttachmentModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AssiDetailsViewFragment extends Fragment  implements View.OnClickListener {
    private TextView content, attachment, name, studentId;
    private EditText marks;
    private WebView webView;
    RecyclerView recyclerView;
    private List<AssinmentAttachment> attachmentModelList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    AttachmentAdapter attachmentAdapter;
    LinearLayout stInfo;


    private String current ="";

    String assignmentId = "", attachmentId = "";


    public AssiDetailsViewFragment() {
        // Required empty public constructor
    }

    public static AssiDetailsViewFragment newInstance(String text) {

        AssiDetailsViewFragment f = new AssiDetailsViewFragment();
        Bundle b = new Bundle();
        b.putString("text", text);
        //    b.putInt("img", image);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assi_details_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        uiHelper = new UIHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);


        assignmentId = getArguments().getString("assignmentId");
        attachmentId = getArguments().getString("id");
        attachmentModelList = new ArrayList<>();

        attachmentAdapter = new AttachmentAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(attachmentAdapter);




        webView = view.findViewById(R.id.webview);

        recyclerView.setVisibility(View.GONE);

        stInfo = view.findViewById(R.id.stInfo);
        if(AppSharedPreference.getUserType().equals("3"))
            stInfo.setVisibility(View.GONE);
        content = view.findViewById(R.id.content);
        content.setOnClickListener(this);
        attachment = view.findViewById(R.id.attachment);
        attachment.setOnClickListener(this);
        name = view.findViewById(R.id.name);
        studentId = view.findViewById(R.id.studentId);

        marks = view.findViewById(R.id.marks);

        if(!assignmentId.isEmpty())
        {
            content.setBackgroundColor(getResources().getColor(R.color.appColor));
            content.setTextColor(getResources().getColor(R.color.white));

            attachment.setBackgroundColor(getResources().getColor(R.color.menu_divider));
            attachment.setTextColor(getResources().getColor(R.color.ashTextColor));

            current = "content";

            callStAssignmentViewApi(assignmentId);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.content:
                if(!current.equals("content")) {
                    content.setBackgroundColor(getResources().getColor(R.color.appColor));
                    content.setTextColor(getResources().getColor(R.color.white));

                    attachment.setBackgroundColor(getResources().getColor(R.color.menu_divider));
                    attachment.setTextColor(getResources().getColor(R.color.ashTextColor));

                    current = "content";
                    recyclerView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);

                  //  callStAssignmentViewApi(assignmentId);
                }


                break;
            case R.id.attachment:
                if(!current.equals("attachment")) {
                    webView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    attachment.setBackgroundColor(getResources().getColor(R.color.appColor));
                    attachment.setTextColor(getResources().getColor(R.color.white));

                    content.setBackgroundColor(getResources().getColor(R.color.menu_divider));
                    content.setTextColor(getResources().getColor(R.color.ashTextColor));
                    current = "attachment";
                }
                break;
        }

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
                            populateData(teacherAssignmentResponse.getData().getAssignment().getAssignment().getDescription());
                            attachmentModelList = new ArrayList<>();
                            attachmentModelList = teacherAssignmentResponse.getData().getAssignment().getAttachments();

                            attachmentAdapter.addAllData(attachmentModelList);


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

    private void callStAttachmentViewApi(String attachmentId) {


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
                            populateData(teacherAssignmentResponse.getData().getAssignment().getAssignment().getDescription());


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

    private void populateData(String description) {
        webView.loadData(description, "text/html; charset=utf-8", "UTF-8");
    }
}
