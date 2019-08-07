package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.response.RegisTrationResponse;
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
public class TeacherAddCourseFragment extends Fragment {

    private EditText courseName, courseCode, creditPoint;
    Button addBtn;

    UIHelper uiHelper;
    public TeacherAddCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_add_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());
        courseName = view.findViewById(R.id.courseName);
        courseCode = view.findViewById(R.id.courseCode);
        creditPoint = view.findViewById(R.id.credit);
        addBtn = view.findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFieldAndCallApi();
            }
        });
    }

    String coursename, coursecode, creditpoint;

    private void validateFieldAndCallApi() {

        boolean valid = true;
        coursename = courseName.getText().toString().trim();
        coursecode = courseCode.getText().toString().trim();
        creditpoint = creditPoint.getText().toString().trim();


        if (TextUtils.isEmpty(coursename)) {
            this.courseName.setError(getString(R.string.required));
            valid = false;
        } else {
            this.courseName.setError(null);
        }



        if (TextUtils.isEmpty(coursecode)) {
//        if (password.length() < 6) {
            courseCode.setError(getString(R.string.required));
            valid = false;
        } else {
            courseCode.setError(null);
        }

        if (TextUtils.isEmpty(creditpoint)) {
            creditPoint.setError(getString(R.string.required));
            valid = false;
        } else {
            creditPoint.setError(null);
        }



        if (!valid) {

            return;
        }
        callAddCourseApi(coursename, coursecode, creditpoint);


    }

    private void callAddCourseApi(final String coursename, final String coursecode, final String creditpoint) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);



        RetrofitApiClient.getApiInterface().addCourse(coursename, coursecode, creditpoint, AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();
                       // RegisTrationResponse regisTrationResponse = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (value.code()  == 200) {
                            //    passwordChangeDialog();

//                            fragment = new UploadProfilePicFragment();
//                            gotoFragment(fragment, "uploadProfilePicFragment");
                        } else
                            uiHelper.dismissLoadingDialog();

                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

}
