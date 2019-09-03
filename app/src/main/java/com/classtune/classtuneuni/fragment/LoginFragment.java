package com.classtune.classtuneuni.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.LoginActivity;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.model.LoginApiModel;
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
public class LoginFragment extends Fragment implements View.OnClickListener {
    public String username = "", password = "";
    EditText etUserName;
    EditText etPassword;
    CheckBox rememberMe;
    Button btnLogin;
    TextView register;

    UIHelper uiHelper;

    ProgressDialog progressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setMessage("Authenticating...");


        register = view.findViewById(R.id.register);
        register.setOnClickListener(this);

        etUserName = view.findViewById(R.id.et_email);
        TextView tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        tvForgetPassword.setOnClickListener(this);
        //etUserName.setText("ovi@gmail.com");
        rememberMe = view.findViewById(R.id.rememberMe);
        etPassword = view.findViewById(R.id.et_password);
        //etPassword.setText("123456");
        btnLogin = view.findViewById(R.id.login);
        btnLogin.setOnClickListener(this);

        if(AppSharedPreference.getRememberMe())
        {
            if(!AppSharedPreference.getUserEmail().isEmpty())
                etUserName.setText(AppSharedPreference.getUserEmail());
            if(!AppSharedPreference.getUserPassword().isEmpty())
                etPassword.setText(AppSharedPreference.getUserPassword());
            rememberMe.setChecked(true);
        }

    }

    private void validateFieldAndCallLogIn() {
        username = etUserName.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etUserName.setFocusable(true);
            etUserName.setError(getString(R.string.java_login_enter_email));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setFocusable(true);
            etPassword.setError(getString(R.string.java_login_enter_password));
            etPassword.requestFocus();
        }
        else {

            callLoginApi(username, password);
        }

    }

    private void callLoginApi(final String email, final String password) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().userLogin(email, password, AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<LoginApiModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<LoginApiModel> value) {
                        uiHelper.dismissLoadingDialog();
                        LoginApiModel loginApiModel = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (loginApiModel.getStatus().getCode()!= null && loginApiModel.getStatus().getCode() == 200) {
                            //    passwordChangeDialog();

                            AppSharedPreference.setUserNameAndPassword(loginApiModel.getData().getUserData().getId(),loginApiModel.getData().getUserData().getEmail(), password, loginApiModel.getData().getApiKey(), rememberMe.isChecked(), loginApiModel.getData().getUserData().getUserType(), loginApiModel.getData().getUserData().getImage(), loginApiModel.getData().getUserData().getName(), loginApiModel.getData().getUserData().getStudentId());
                            //callMenuApi();

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else
                            uiHelper.dismissLoadingDialog();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();

//                        } else {
//
//                            Toast.makeText(getApplicationContext(), wrapper.getStatus().getMsg(), Toast.LENGTH_SHORT).show();
//                        }
                    }


                    @Override
                    public void onError(Throwable e) {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
//                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
//                validateFieldAndCallLogIn();
                validateFieldAndCallLogIn();
                break;
            case R.id.register:
                Fragment fragment = new UserSelectionFragment();
                loadFragment(fragment, "userSelectionFragment");

                break;
//            case R.id.tv_forget_password:
//                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
//                startActivity(intent);
//                break;

            default:
                break;
        }
    }

    private void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainer, fragment, tag);

        transaction.commit();
    }

}
