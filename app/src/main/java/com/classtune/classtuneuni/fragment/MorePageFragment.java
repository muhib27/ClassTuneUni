package com.classtune.classtuneuni.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.LoginActivity;
import com.classtune.classtuneuni.model.User;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MorePageFragment extends Fragment implements View.OnClickListener {

    Fragment fragment;
    UIHelper uiHelper;
    RelativeLayout rl_8, rl_1, rl_2, rl_3, rl_4, result, rl_6, rl_7;

    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, name, studentId;

    public MorePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());

        iv1 = view.findViewById(R.id.profile_image);
        iv2 = view.findViewById(R.id.iv2);
        iv3 = view.findViewById(R.id.iv3);
        iv4 = view.findViewById(R.id.iv4);
        iv5 = view.findViewById(R.id.iv5);
        iv6 = view.findViewById(R.id.iv6);
        iv7 = view.findViewById(R.id.iv7);

        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);
        tv7 = view.findViewById(R.id.tv7);

        name = view.findViewById(R.id.name);
        studentId = view.findViewById(R.id.studentId);



        rl_1 = view.findViewById(R.id.rl_1);
        rl_1.setOnClickListener(this);

        rl_2 = view.findViewById(R.id.rl_2);
        rl_2.setOnClickListener(this);

        rl_3 = view.findViewById(R.id.rl_3);
        rl_3.setOnClickListener(this);

        rl_4 = view.findViewById(R.id.rl_4);
        rl_4.setOnClickListener(this);

        result = view.findViewById(R.id.result);
        result.setOnClickListener(this);

        rl_6 = view.findViewById(R.id.rl_6);
        rl_6.setOnClickListener(this);

        rl_7 = view.findViewById(R.id.rl_7);
        rl_7.setOnClickListener(this);

        rl_8 = view.findViewById(R.id.rl_8);
        rl_8.setOnClickListener(this);


        if (AppSharedPreference.getUserType().equals("2")) {

            iv2.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.course_offer));
            tv2.setText("Course");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_1:
                if (AppSharedPreference.getUserType().equals("3")) {
                    fragment = new StudentProfileFragment();
                    gotoFragment(fragment, "studentProfileFragment");
                } else {
//                    fragment = new EnrollStartFragment();
//                    gotoFragment(fragment, "enrollStartFragment");
                }
                break;
            case R.id.rl_8:
                callLogOutApi();
                break;
            case R.id.rl_2:
                if (AppSharedPreference.getUserType().equals("2")) {
                    fragment = new CourseListFragment();
                    gotoFragment(fragment, "courseListFragment");
                } else {
                    fragment = new CodeEntryFragment();
                    gotoFragment(fragment, "codeEntryFragment");
                }
                break;
            case R.id.rl_3:
                if (AppSharedPreference.getUserType().equals("3")) {
                    fragment = new ClassScheduleFragment();
                    gotoFragment(fragment, "classScheduleFragment");
                } else {
//                    fragment = new EnrollStartFragment();
//                    gotoFragment(fragment, "enrollStartFragment");
                }
                break;
            case R.id.rl_4:
                fragment = new ExamListFragment();
                gotoFragment(fragment, "examListFragment");
                break;
            case R.id.result:
                fragment = new SubjectResultFragment();
                gotoFragment(fragment, "subjectResultFragment");
                break;
            case R.id.rl_6:
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    fragment = new StudentAttendanceFragment();
                    gotoFragment(fragment, "studentAttendanceFragment");
                }
                else {
                    fragment = new TeacherAttendanceFragment();
                    gotoFragment(fragment, "teacherAttendanceFragment");
                }
                break;
        }
    }


    private void callLogOutApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getLogout(AppSharedPreference.getApiKey(), AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();

                        if (value.code() == 200)
                            userLogout();

                    }


                    @Override
                    public void onError(Throwable e) {

//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });
    }

    private void userLogout() {
        User user = new User("", "", "", "");
//        AppSharedPreference.setUserBasicInfo(user);
//        if (AppSharedPreference.getRememberMe()) {
//            AppSharedPreference.setUserNameAndPassword(AppSharedPreference.getUserName(), AppSharedPreference.getUserPassword(), "", true);
//
//        } else {
//            AppSharedPreference.setUserNameAndPassword("", "", "", false);
//        }
        AppSharedPreference.setUsingFirstTime(true);
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
