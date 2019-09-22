package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.RelatedCourseAdapter;
import com.classtune.classtuneuni.adapter.StCourseAdapter;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.CourseDetailsResponse;
import com.classtune.classtuneuni.exam.ExamPolicyResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
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
public class CourseDetailsFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {
    private LinearLayout allCourseLayout, shareLl;
    private Button enrollNow;
    RecyclerView recyclerView;
    private List<Course> courseList;
//    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    RelatedCourseAdapter relatedCourseAdapter;

    String courseId = "";

    String contentSt = "কেন্দ্রীয় ব্যাংকে রক্ষিত বৈদেশিক মুদ্রার মজুত বা রিজার্ভ চুরির ক্ষেত্র প্রস্তুত করে রেখেছিল বাংলাদেশ ব্যাংক নিজেই। নিরাপত্তাব্যবস্থা ছিল অরক্ষিত, সংশ্লিষ্ট কর্মকর্তারা ছিলেন দায়িত্বহীন। আর চূড়ান্ত সর্বনাশ ঘটানো হয় সুইফট সার্ভারের সঙ্গে স্থানীয় নেটওয়ার্ক জুড়ে দিয়ে। এর ছয় মাসের মধ্যেই গোপন সংকেত বা পাসওয়ার্ড জেনে নিয়ে চুরি হয় ৮ কোটি ১০ লাখ ১ হাজার ৬২৩ মার্কিন ডলার (বাংলাদেশি মুদ্রায় ৮১০ কোটি টাকা)। \n" +
            "\n" +
            "বাংলাদেশ ব্যাংকের রিজার্ভ থেকে অর্থ চুরি হয় প্রায় সাড়ে তিন বছর আগে, ২০১৬ সালের ৪ ফেব্রুয়ারি রাতে। বাংলাদেশ ব্যাংক এক দিন পরে চুরির তথ্য জানতে পারলেও তা গোপন রাখে আরও ২৪ দিন। আর বিষয়টি অর্থমন্ত্রীকে বাংলাদেশ ব্যাংক আনুষ্ঠানিকভাবে জানায় ৩৩তম দিনে।";

    private WebView webView;

    LinearLayoutManager layoutManager;
    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getArguments().getString("courseId")!=null)
        courseId = getArguments().getString("courseId");
        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);
        webView = view.findViewById(R.id.webView);
        allCourseLayout = view.findViewById(R.id.allCourseLayout);
        allCourseLayout.setOnClickListener(this);

        shareLl = view.findViewById(R.id.shareLl);
        shareLl.setOnClickListener(this);

        enrollNow = view.findViewById(R.id.enrollNow);
        enrollNow.setOnClickListener(this);

        courseList = new ArrayList<>();

        courseList.add(new Course("Title"));
        courseList.add(new Course("Title"));
        courseList.add(new Course("Title"));

        relatedCourseAdapter = new RelatedCourseAdapter(getActivity(), this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(relatedCourseAdapter);

        //relatedCourseAdapter.addAllData(courseList);
        //populateData();
        if(courseId != null){
            callCourseDetailsApi();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.allCourseLayout:
                Fragment fragment = new TeacherAllCourseFragment();
                gotoFragment(fragment, "teacherAllCourseFragment", "1");
                break;
            case R.id.shareLl:
                shareCourse();
                break;
            case R.id.enrollNow:
                enrollDialog();
                break;
        }
    }

    private void enrollDialog() {
        final EditText et1, et2, et3, et4, et5, et6;

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.course_enroll_dialog_view);

        et1 = dialog.findViewById(R.id.editText1);
        et2 = dialog.findViewById(R.id.editText2);
        et3 = dialog.findViewById(R.id.editText3);
        et4 = dialog.findViewById(R.id.editText4);
        et5 = dialog.findViewById(R.id.editText5);
        et6 = dialog.findViewById(R.id.editText6);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et1.getText().toString().trim().length() >0){
                    et1.clearFocus();
                    et2.requestFocus();
                }
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et2.getText().toString().trim().length() >0){
                    et2.clearFocus();
                    et3.requestFocus();
                }
            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et3.getText().toString().trim().length() >0){
                    et3.clearFocus();
                    et4.requestFocus();
                }
            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et4.getText().toString().trim().length() >0){
                    et4.clearFocus();
                    et5.requestFocus();
                }
            }
        });
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et5.getText().toString().trim().length() >0){
                    et5.clearFocus();
                    et6.requestFocus();
                }
            }
        });


        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et6.getText().toString().trim().length() >0){
                    et6.clearFocus();
                    //et6.requestFocus();
                }
            }
        });

        Button create = dialog.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    dialog.dismiss();

            }
        });
        Button cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void shareCourse() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
//        shareIntent.putExtra(Intent.EXTRA_SUBJECT, movieResults.get(position).getTitle().getRendered());
//        shareIntent.putExtra(Intent.EXTRA_TITLE, movieResults.get(position).getTitle().getRendered());
//        shareIntent.putExtra(Intent.EXTRA_TEXT, movieResults.get(position).getLink());
        getActivity().startActivity(Intent.createChooser(shareIntent, "Share link using"));

    }

    @Override
    public void retryPageLoad() {

    }

    private void populateData() {
        if (contentSt != null)
            webView.loadData(contentSt, "text/html; charset=utf-8", "UTF-8");
//        if (subjectName != null)
//            title.setText(subjectName);
//        if (titleSt != null)
//            subCode.setText(titleSt);
    }

    private void gotoFragment(Fragment fragment, String tag, String courseId) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("courseId", courseId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callCourseDetailsApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getCourseView(AppSharedPreference.getApiKey(), courseId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CourseDetailsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CourseDetailsResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        CourseDetailsResponse courseDetailsResponse = value.body();
                       // examPolicyAdapter.clear();
                        if (courseDetailsResponse.getStatus().getCode() == 200) {
                           // policyList = examPolicyResponse.getData().getPolicies();

                            //examPolicyAdapter.addAllData(policyList, false);
                            //examPolicyAdapter.disableEditing();

//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                           // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
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
}
