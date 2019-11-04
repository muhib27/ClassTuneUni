package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.ListAdapter;
import com.classtune.classtuneuni.assignment.Status;
import com.classtune.classtuneuni.model.UniversityModel;
import com.classtune.classtuneuni.response.RegisTrationResponse;
import com.classtune.classtuneuni.response.UniData;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.hbb20.CountryCodePicker;

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
public class RegistrationFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {
    Spinner spinner;
    Button continueBtn;
    Fragment fragment;
    private EditText userName, userEmail, userPassword, userRePassword, studentId, phoneNo;
    private CheckBox agreeCb;
    TextView termCondition, uniName;
    UIHelper uiHelper;
    LinearLayout uniNameLl, stIdLl;
    CountryCodePicker ccp;
    private ImageView uniImg;

    private String username = "", password = "", email = "", repassword = "", userType = "", uniCode = "", uniname = "", studentid = "", phone = "", countryCode = "", totalPhone = "";


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userType = getArguments().getString("userType");
        uiHelper = new UIHelper(getActivity());
//        spinner = view.findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        userName = view.findViewById(R.id.et_name);
        phoneNo = view.findViewById(R.id.et_phone);
        userEmail = view.findViewById(R.id.et_email);
        userPassword = view.findViewById(R.id.et_password);
        userRePassword = view.findViewById(R.id.et_con_password);
//        uniImg = view.findViewById(R.id.uniImg);
//        uniImg.setOnClickListener(this);
        ccp = view.findViewById(R.id.ccp);
        //ccp.setCountryForPhoneCode(376);


        studentId = view.findViewById(R.id.studentId);

        stIdLl = view.findViewById(R.id.stIdLl);

//        uniName = view.findViewById(R.id.et_uni_name);
//        uniName.setOnClickListener(this);
//        uniNameLl = view.findViewById(R.id.uniName);
//        uniNameLl.setOnClickListener(this);
//        uniNameLl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog();
//            }
//        });

//        uniName.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if(s != null && s.toString().trim().length()>0)
//                   // field2.setText("");
//                    searchApiCall(s.toString().trim());
//            }
//        });


        termCondition = view.findViewById(R.id.term);
        termCondition.setOnClickListener(this);

        agreeCb = view.findViewById(R.id.cb);

        continueBtn = view.findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);

        if (userType.equals("3")) {
            stIdLl.setVisibility(View.VISIBLE);
            // uniNameLl.setVisibility(View.GONE);
        } else {
            // uniNameLl.setVisibility(View.VISIBLE);
            stIdLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.et_uni_name:
//            case R.id.uniName:
//            case R.id.uniImg:
//                showDialog();
//                break;

            case R.id.continueBtn:
                validateFieldAndCallLogIn();

//                fragment = new UploadProfilePicFragment();
//                gotoFragment(fragment, "uploadProfilePicFragment");
                break;
            case R.id.term:
//                fragment = new UploadProfilePicFragment();
//                gotoFragment(fragment, "uploadProfilePicFragment");
                break;
        }
    }


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Teacher"))
                userType = "2";
            else if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Student"))
                userType = "3";
            else
                userType = "";
//            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
//                // ToDo when first item is selected
//            } else {
//                Toast.makeText(parent.getContext(),
//                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_LONG).show();
//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

            userType = "";
        }

    }


    private void validateFieldAndCallLogIn() {

        boolean valid = true;
        phone = phoneNo.getText().toString().trim();
        username = userName.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        repassword = userRePassword.getText().toString().trim();

        countryCode = ccp.getSelectedCountryCode();
        if (phone.length() > 0) {
            if (countryCode.equals("880")) {
                String s = phone.substring(0, 1);
                if (s.equals("0"))
                    totalPhone = countryCode + "-" + phone.substring(1, phone.length());
                else
                    totalPhone = countryCode + "-" + phone;
            } else
                totalPhone = countryCode + "-" + phone;
        }


        if (TextUtils.isEmpty(username)) {
            this.userName.setError(getString(R.string.required));
            valid = false;
        } else {
            this.userName.setError(null);
        }


        if (TextUtils.isEmpty(email)) {
            userEmail.setError(getString(R.string.required));
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError(getString(R.string.invalid_email));
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
//        if (password.length() < 6) {
            userPassword.setError(getString(R.string.required));
            valid = false;
        } else {
            userPassword.setError(null);
        }


        if (TextUtils.isEmpty(repassword)) {
            userRePassword.setError(getString(R.string.required));
            valid = false;
        } else if (!repassword.equals(password)) {
            this.userRePassword.setError(getString(R.string.password_mismatch_reg));
            valid = false;
        } else {
            userRePassword.setError(null);
        }
        if (valid && password.length() < 6) {
            Toast.makeText(getActivity(), getString(R.string.password_length), Toast.LENGTH_SHORT).show();
            return;
        }

//        if(userType.equals("3")){
//            studentid = studentId.getText().toString().trim();
//            if (TextUtils.isEmpty(studentid)) {
//                this.studentId.setError(getString(R.string.required));
//                valid = false;
//            } else {
//                this.studentId.setError(null);
//            }
//        }
//        else {
//            if (TextUtils.isEmpty(uniname) || TextUtils.isEmpty(uniCode)) {
//                if (uniCode.isEmpty()) {
//                    if (uniname.isEmpty()) {
//                        uniName.setError(getString(R.string.required));
//                        valid = false;
//                    }
//                }
//
//            } else {
//                uniName.setError(null);
//            }
//        }

        if (userType.equals("3")) {
//            uniname = uniName.getText().toString().trim();
//            if (TextUtils.isEmpty(uniname)) {
//                this.uniName.setError(getString(R.string.required));
//                valid = false;
//            } else {
//                this.uniName.setError(null);
//            }
//            if (uniCode.isEmpty())
//                callStRegistrationApiName(username, email, password, repassword, studentid, totalPhone, uniname);
            // else
//                callStRegistrationApi(username, email, password, repassword, studentid, totalPhone, uniCode);
        } else {
//            if (!uniCode.isEmpty())
//                callRegistrationWithNameApi(username, email, password, repassword, uniname);
//            else
            //  callRegistrationApi(username, email, password, repassword, uniCode);
        }
//
//        if (TextUtils.isEmpty(userType)) {
////        if (password.length() < 6) {
//            //userPassword.setError(getString(R.string.required));
//            Toast.makeText(getActivity(), "Please Select User Type", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }

        if (!agreeCb.isChecked()) {
            agreeCb.setError(getString(R.string.chckbox_needed));
            valid = false;
        }
        if (!valid) {

            return;
        }
        //Toast.makeText(getActivity(), "goto server", Toast.LENGTH_SHORT).show();
        callStRegistrationApi(username, email, password, repassword, studentid, totalPhone);


    }


    private void callRegistrationApi(final String name, final String email, final String password, final String repassword, final String unicode) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        String st = userType;
        RetrofitApiClient.getApiInterface().userRegWithCode(email, password, repassword, name, userType, unicode, AppSharedPreference.getFcm())

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

                            AppSharedPreference.setUserNameAndPassword(regisTrationResponse.getData().getUserData().getId(), email, password, regisTrationResponse.getData().getApiKey(), false, regisTrationResponse.getData().getUserData().getUserType(), regisTrationResponse.getData().getUserData().getImage(), regisTrationResponse.getData().getUserData().getName(), regisTrationResponse.getData().getUserData().getStudentId(), regisTrationResponse.getData().getUserData().getMobile());
                            AppSharedPreference.setUserStatus(regisTrationResponse.getCourseCount());
                            fragment = new UploadProfilePicFragment();
                            gotoFragment(fragment, "uploadProfilePicFragment");
                        } else
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

    private void callStRegistrationApi(final String name, final String email, final String password, final String repassword, final String studentId, String phone) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        String st = userType;
        RetrofitApiClient.getApiInterface().studentRegistration(email, password, repassword, name, userType, studentId, AppSharedPreference.getFcm(), phone)

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

                            AppSharedPreference.setUserNameAndPassword(regisTrationResponse.getData().getUserData().getId(), email, password, regisTrationResponse.getData().getApiKey(), false, regisTrationResponse.getData().getUserData().getUserType(), regisTrationResponse.getData().getUserData().getImage(), regisTrationResponse.getData().getUserData().getName(), regisTrationResponse.getData().getUserData().getStudentId(), regisTrationResponse.getData().getUserData().getMobile());
                            AppSharedPreference.setUserStatus(regisTrationResponse.getCourseCount());
//                            fragment = new UploadProfilePicFragment();
//                            gotoFragment(fragment, "uploadProfilePicFragment");

                            showRegistrationDialog(getResources().getString(R.string.message_to_verify_reg), regisTrationResponse.getData().getApiKey());
                        } else
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

    private void callStRegistrationApiName(final String name, final String email, final String password, final String repassword, final String studentId, String phone, String uniname) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        String st = userType;
        RetrofitApiClient.getApiInterface().studentRegistrationName(email, password, repassword, name, userType, studentId, AppSharedPreference.getFcm(), phone, uniname)

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

                            AppSharedPreference.setUserNameAndPassword(regisTrationResponse.getData().getUserData().getId(), email, password, regisTrationResponse.getData().getApiKey(), false, regisTrationResponse.getData().getUserData().getUserType(), regisTrationResponse.getData().getUserData().getImage(), regisTrationResponse.getData().getUserData().getName(), regisTrationResponse.getData().getUserData().getStudentId(), regisTrationResponse.getData().getUserData().getMobile());
                            AppSharedPreference.setUserStatus(regisTrationResponse.getCourseCount());
                            fragment = new UploadProfilePicFragment();
                            gotoFragment(fragment, "uploadProfilePicFragment");
                        } else
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

    private void callRegistrationWithNameApi(final String name, final String email, final String password, final String repassword, final String uniname) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Authenticating...");
//        HashMap params = new HashMap();
//        params.put("username", username);
//        params.put("password", password);


        RetrofitApiClient.getApiInterface().userRegWithName(email, password, repassword, name, userType, uniname, AppSharedPreference.getFcm())
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
                            AppSharedPreference.setUserNameAndPassword(regisTrationResponse.getData().getUserData().getId(), email, password, regisTrationResponse.getData().getApiKey(), false, regisTrationResponse.getData().getUserData().getUserType(), regisTrationResponse.getData().getUserData().getImage(), regisTrationResponse.getData().getUserData().getName(), regisTrationResponse.getData().getUserData().getStudentId(), regisTrationResponse.getData().getUserData().getMobile());
                            AppSharedPreference.setUserStatus(regisTrationResponse.getCourseCount());
                            fragment = new UploadProfilePicFragment();
                            gotoFragment(fragment, "uploadProfilePicFragment");
                        } else
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


    public void searchApiCall(final String text) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                universitySearch(text);
            }
        }, 1000);
    }


    Handler handler = new Handler();


    private void universitySearch(final String text) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (!uiHelper.isDialogActive())
//            uiHelper.showLoadingDialog("Authenticating...");


        RetrofitApiClient.getApiInterface().getUniversity(text)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UniversityModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<UniversityModel> value) {
                        //  uiHelper.dismissLoadingDialog();
                        UniversityModel universityModel = value.body();


                        universityModelList = new ArrayList<>();
//                        Log.e("login", "onResponse: "+value.body());
//                        Wrapper wrapper = GsonParser.getInstance().parseServerResponse2(
//                                value.body());
//
                        if (universityModel.getStatus().getCode() != null && universityModel.getStatus().getCode() == 200) {
                            universityModelList = universityModel.getData();
                            listAdapter = new ListAdapter(getActivity(), R.layout.university_list, universityModelList);
                            lv.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();
//                            listAdapter.notifyDataSetChanged();

                        } else if (universityModel.getStatus().getCode() != null && universityModel.getStatus().getCode() == 204) {
                            universityModelList = new ArrayList<>();
                            listAdapter = new ListAdapter(getActivity(), R.layout.university_list, universityModelList);
                            lv.setAdapter(listAdapter);
                            listAdapter.notifyDataSetChanged();
                            uiHelper.dismissLoadingDialog();
                        }
//                        else
//                            uiHelper.dismissLoadingDialog();
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

//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //  uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //   uiHelper.dismissLoadingDialog();
                    }
                });


    }

    List<UniData> universityModelList;
    ListAdapter listAdapter;
    ListView lv;

    private void showDialog() {
        universityModelList = new ArrayList<>();
//        UniData uniData =new UniData("sss", "1");
//        universityModelList.add(uniData);
//        uniData = new UniData("sss", "2");
//        universityModelList.add(uniData);

        uniCode = "";
        ArrayAdapter<UniversityModel> adapter;
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_university);

        lv = dialog.findViewById(R.id.listView);
        listAdapter = new ListAdapter(getActivity(), R.layout.university_list, universityModelList);
        lv.setAdapter(listAdapter);

        final SearchView searchView = dialog.findViewById(R.id.searchView);

        // show soft keyboard
        searchView.requestFocus();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        searchView.setOnQueryTextListener(this);

//        editText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//
//                if (cs != null && cs.toString().trim().length() >= 3)
//                    // field2.setText("");
//                    searchApiCall(cs.toString().trim());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
//            }
//        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                uniname = universityModelList.get(i).getName();
                uniCode = universityModelList.get(i).getId();
                uniName.setText(uniname);
                dialog.dismiss();
            }
        });


//        Button ok = dialog.findViewById(R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (searchView.getQuery() != null && searchView.getQuery().toString().trim().length() > 0) {
//                    uniname = searchView.getQuery().toString();
//                    uniName.setText("");
//                }
//                dialog.dismiss();
//            }
//        });
        Button cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s != null && s.toString().trim().length() >= 3)
            // field2.setText("");
            searchApiCall(s.toString().trim());
        return false;
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    Dialog dialog;
    TextView msgText;
    long mLastClickTime = 0;

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

        final TextView login = dialog.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fragment = new LoginFragment();
                gotoFragment(fragment, "loginFragment");
            }
        });

        Button resend = dialog.findViewById(R.id.resend);
        resend.setOnClickListener(new View.OnClickListener() {
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

        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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


        String st = userType;
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
                            fragment = new UploadProfilePicFragment();
                            gotoFragment(fragment, "uploadProfilePicFragment");


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
