package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.LoginActivity;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.assignment.Status;
import com.classtune.classtuneuni.model.LoginApiModel;
import com.classtune.classtuneuni.model.UniversityModel;
import com.classtune.classtuneuni.response.RegisTrationResponse;
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
    TextView register, tv_forget_password;

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

        if (AppSharedPreference.getRememberMe()) {
            if (!AppSharedPreference.getUserEmail().isEmpty())
                etUserName.setText(AppSharedPreference.getUserEmail());
            if (!AppSharedPreference.getUserPassword().isEmpty())
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
        } else {

            callLoginApi(username, password);
        }

    }

    private void callLoginApi(final String email, final String password) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        // ((MainActivity)getActivity()).gifImageView.setVisibility(View.VISIBLE);
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
                        //uiHelper.dismissLoadingDialog();
                        LoginApiModel loginApiModel = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (loginApiModel!=null && loginApiModel.getStatus().getCode() != null && loginApiModel.getStatus().getCode() == 200) {
                            //    passwordChangeDialog();

                            AppSharedPreference.setUserNameAndPassword(loginApiModel.getData().getUserData().getId(), loginApiModel.getData().getUserData().getEmail(), password, loginApiModel.getData().getApiKey(), rememberMe.isChecked(), loginApiModel.getData().getUserData().getUserType(), loginApiModel.getData().getUserData().getImage(), loginApiModel.getData().getUserData().getName(), loginApiModel.getData().getUserData().getStudentId(), loginApiModel.getData().getUserData().getMobile());
                            //callMenuApi();
                            AppSharedPreference.setUserStatus(loginApiModel.getCourseCount());

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else if(loginApiModel.getStatus().getCode() != null && loginApiModel.getStatus().getCode() == 205){
                            AppSharedPreference.setUserNameAndPassword(loginApiModel.getData().getUserData().getId(), loginApiModel.getData().getUserData().getEmail(), password, loginApiModel.getData().getApiKey(), rememberMe.isChecked(), loginApiModel.getData().getUserData().getUserType(), loginApiModel.getData().getUserData().getImage(), loginApiModel.getData().getUserData().getName(), loginApiModel.getData().getUserData().getStudentId(), loginApiModel.getData().getUserData().getMobile());
                            //callMenuApi();
                            AppSharedPreference.setUserStatus(loginApiModel.getCourseCount());
                            showRegistrationDialog("Your account is not verified yet. " + getResources().getString(R.string.message_to_verify),loginApiModel.getData().getApiKey());

                        }
                        else {
                            uiHelper.dismissLoadingDialog();
                            uiHelper.showMessageDialog("Login credential not matched");
                        }
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
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();

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
            case R.id.tv_forget_password:
//                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
//                startActivity(intent);
                showDialog();
                break;

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

    String email = "";
    boolean valid = true;

    public void showDialog() {

        ArrayAdapter<UniversityModel> adapter;
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forget_password);

        final EditText editText = dialog.findViewById(R.id.et_email);

        editText.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        Button submit = dialog.findViewById(R.id.ok);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid = true;
                email = editText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    editText.setError(getString(R.string.required));
                    valid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editText.setError(getString(R.string.invalid_email));
                    valid = false;
                } else {
                    editText.setError(null);
                }
                if (valid) {
                    dialog.dismiss();
                    callForgetPassword(email);
                }
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

    private void callForgetPassword(final String email) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        // ((MainActivity)getActivity()).gifImageView.setVisibility(View.VISIBLE);
        uiHelper.showLoadingDialog("Please wait...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().forgetPassword(email)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Status>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Status> value) {
                        uiHelper.dismissLoadingDialog();
                        Status status = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (status.getStatus().getCode() != null && status.getStatus().getCode() == 200) {
                            //    passwordChangeDialog();
                            uiHelper.showMessageDialog("Please check your email to reset password");

                        } else {
                            uiHelper.showMessageDialog("Login credential not matched");
                        }

                    }


                    @Override
                    public void onError(Throwable e) {
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();

                    }
                });
    }

    Dialog dialog;
    long mLastClickTime = 0;
    TextView msgText;
    private void showRegistrationDialog(String msg, final String key) {
        mLastClickTime = 0;
        ArrayAdapter<UniversityModel> adapter;
        dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_verification_code);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final EditText code = dialog.findViewById(R.id.et_name);
        msgText = dialog.findViewById(R.id.text);
        msgText.setText(msg);

        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final TextView login = dialog.findViewById(R.id.login);
        login.setVisibility(View.GONE);


        Button cancel = dialog.findViewById(R.id.resend);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog.dismiss();
                if (SystemClock.elapsedRealtime() - mLastClickTime < 30000){
                    long s = 30 - ((SystemClock.elapsedRealtime() - mLastClickTime)/1000);
                    Toast.makeText(getActivity(), "Please press again after " + s + " seconds", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    mLastClickTime = SystemClock.elapsedRealtime();
                    callResendApi();
                }

            }
        });

        Button ok = dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.getText().toString().trim().length()>4) {
                    callStVerificationApi(key, code.getText().toString().trim());
                    dialog.dismiss();
                }
                else {
                    code.setError(getString(R.string.eight_character));
                }
            }
        });

        dialog.show();

    }

    private void callStVerificationApi(final String apikey, final String code) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


//        String st = userType;
        RetrofitApiClient.getApiInterface().studentVerification(apikey, code)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<RegisTrationResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<RegisTrationResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        RegisTrationResponse regisTrationResponse = value.body();


//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());

                        if (regisTrationResponse.getStatus().getCode() != null && regisTrationResponse.getStatus().getCode() == 200) {
                            //    passwordChangeDialog();

//                            AppSharedPreference.setUserNameAndPassword(regisTrationResponse.getData().getUserData().getId(), email, password, regisTrationResponse.getData().getApiKey(), false, regisTrationResponse.getData().getUserData().getUserType(), regisTrationResponse.getData().getUserData().getImage(), regisTrationResponse.getData().getUserData().getName(), regisTrationResponse.getData().getUserData().getStudentId(), regisTrationResponse.getData().getUserData().getMobile());
//                            AppSharedPreference.setUserStatus(regisTrationResponse.getCourseCount());
//                            fragment = new UploadProfilePicFragment();
//                            gotoFragment(fragment, "uploadProfilePicFragment");
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            if(getActivity()!=null)
                            getActivity().finish();


                        } else {
                            uiHelper.dismissLoadingDialog();
                            showRegistrationDialog(getResources().getString(R.string.security_code_wrong), apikey);
                        }
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

    private void callResendApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
      //  uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().resendCode(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Status>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Status> value) {
                        uiHelper.dismissLoadingDialog();

                        Status status = value.body();
                        // examPolicyAdapter.clear();
                        if (status.getStatus().getCode()!=null && status.getStatus().getCode() == 200) {
                            msgText.setText(getResources().getString(R.string.message_to_verify));
                        } else {
                            // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        //uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //uiHelper.dismissLoadingDialog();
                    }
                });
    }

}
