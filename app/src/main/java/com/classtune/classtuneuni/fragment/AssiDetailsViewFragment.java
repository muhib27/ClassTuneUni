package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

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
    UIHelper uiHelper;
    private WebView webView;

    private String current ="";

    String assignmentId = "", attachmentId = "";


    public AssiDetailsViewFragment() {
        // Required empty public constructor
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

        uiHelper = new UIHelper(getActivity());

        assignmentId = getArguments().getString("assignmentId");
        attachmentId = getArguments().getString("id");


        webView = view.findViewById(R.id.webview);

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

                    callStAssignmentViewApi(assignmentId);
                }


                break;
            case R.id.attachment:
                if(!current.equals("attachment")) {

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
