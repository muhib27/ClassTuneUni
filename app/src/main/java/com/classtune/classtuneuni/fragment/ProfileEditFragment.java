package com.classtune.classtuneuni.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ListAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.model.LoginApiModel;
import com.classtune.classtuneuni.model.UniversityModel;
import com.classtune.classtuneuni.profile.EditProfileResponse;
import com.classtune.classtuneuni.response.RegisTrationResponse;
import com.classtune.classtuneuni.response.UniData;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.URLHelper;
import com.google.gson.JsonElement;
import com.hbb20.CountryCodePicker;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment implements View.OnClickListener {
    Spinner spinner;
    Button save;
    Fragment fragment;
    private EditText userName, userEmail, userPassword, userRePassword, studentId, phoneNo;
    private CheckBox agreeCb;
    TextView termCondition, uniName;
    UIHelper uiHelper;
    LinearLayout uniNameLl , stIdLl;
    CircleImageView profile_image;
    FloatingActionButton fab;
    CountryCodePicker ccp;
    public static final String BASE_URL= "http://uni.edoozz.com/";
    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_STORAGE_CAMERA_PERM = 124;

    private String username = "", password = "", email = "", repassword = "", userType = "", uniCode = "", uniname = "", studentid = "", phone = "", countryCodeSt = "", totalPhone = "";


    public ProfileEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_edit_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userType = AppSharedPreference.getUserType();
        uiHelper = new UIHelper(getActivity());
//        spinner = view.findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        attachmentModelList = new ArrayList<>();
        userEmail = view.findViewById(R.id.et_email);
        userName = view.findViewById(R.id.et_name);
        phoneNo = view.findViewById(R.id.et_phone);
        studentId = view.findViewById(R.id.studentId);
        userPassword = view.findViewById(R.id.et_password);
        userRePassword = view.findViewById(R.id.et_con_password);
        profile_image = view.findViewById(R.id.profile_image);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(this);
        ccp = view.findViewById(R.id.ccp);
        countryCodeSt = ccp.getSelectedCountryCode();

        if(AppSharedPreference.getUserName()!=null)
        userName.setText(AppSharedPreference.getUserName());
        if(AppSharedPreference.getUserEmail()!=null)
        userEmail.setText(AppSharedPreference.getUserEmail());
        studentId.setText(AppSharedPreference.getUserId());

        String p = AppSharedPreference.getUserPhone();
        if(!p.isEmpty() && p.contains("-"))
        {
            String[] parts = p.split("\\-");
            phoneNo.setText(parts[1]);
            ccp.setCountryForPhoneCode(Integer.parseInt(parts[0]));
        }

        if(getActivity()!=null)
        Glide.with(getActivity())
                .load(URLHelper.BASE_URL + AppSharedPreference.getUserImage())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.news_poster)
                        .fitCenter())
                .into(profile_image);
     //   phoneNo.setText(AppSharedPreference.ge);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                validateFieldAndCallLogIn();

//                fragment = new UploadProfilePicFragment();
//                gotoFragment(fragment, "uploadProfilePicFragment");
                break;
            case R.id.fab:
                readStorageStateTask();
                break;
            case R.id.term:
//                fragment = new UploadProfilePicFragment();
//                gotoFragment(fragment, "uploadProfilePicFragment");
                break;
        }
    }

    @AfterPermissionGranted(RC_STORAGE_CAMERA_PERM)
    public void readStorageStateTask() {
        if (hasStorageAndCameraPermission()) {
            // Have permission, do the thing!
            // Toast.makeText(this, "TODO: Phone State things", Toast.LENGTH_LONG).show();
            //validateFieldAndCallLogIn();
            //showChooser();
            //showDialogAttachment();
            browseFile();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_storage_camera),
                    RC_STORAGE_CAMERA_PERM,
                    STORAGE_AND_CAMERA);
        }
    }

    private boolean hasStorageAndCameraPermission() {
        return EasyPermissions.hasPermissions(getActivity(), STORAGE_AND_CAMERA);
    }

    private void browseFile() {
        Intent intent2 = new Intent(getActivity(), ImagePickActivity.class);
        intent2.putExtra(IS_NEED_CAMERA, true);
        intent2.putExtra(Constant.MAX_NUMBER, 1);
        intent2.putExtra(IS_NEED_FOLDER_LIST, true);
        startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);
    }


    StringBuilder builder;
    ListView attachmentList;
    AttachmentModel attachmentModel;
    List<AttachmentModel> attachmentModelList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    getActivity(),
                    getString(R.string.returned_from_app_settings_to_activity_storage,
                            hasStorageAndCameraPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        } else if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {

            if (resultCode == RESULT_OK) {
                attachmentModelList = new ArrayList<>();
                ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
//                StringBuilder builder = new StringBuilder();
                builder = new StringBuilder();
                for (ImageFile file : list) {
                    String path = file.getPath();
                    File f = new File(path);
                    builder.append(f.getName() + "\n");
                    attachmentModelList.add(new AttachmentModel(f.getName(), path));

                }
                //attachmentAdapter.notifyDataSetChanged();

//                Bitmap bmImg = BitmapFactory.decodeFile(attachmentModelList.get(0).getFilePath());
//                profileImg.setImageBitmap(bmImg);

                File file = new File(attachmentModelList.get(0).getFilePath());
                Uri imageUri = Uri.fromFile(file);
                long fileSizeInKB = file.length() / 1024;
                long fileSizeInMB = fileSizeInKB / 1024;
                if(fileSizeInMB<=3)
                    Glide.with(this)
                            .load(imageUri)
                            .into(profile_image);
                else
                    Toast.makeText(getActivity(), "Image is too large", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void validateFieldAndCallLogIn() {

        boolean valid = true;
        phone = phoneNo.getText().toString().trim();
        username = userName.getText().toString().trim();
        email = userEmail.getText().toString().trim();
        studentid = studentId.getText().toString().trim();
        password = userPassword.getText().toString().trim();
        repassword = userRePassword.getText().toString().trim();


        countryCodeSt = ccp.getSelectedCountryCode();
        if (phone.length() > 0) {
            if (countryCodeSt.equals("880")) {
                String s = phone.substring(0, 1);
                if (s.equals("0"))
                    totalPhone = countryCodeSt + "-" +phone.substring(1, phone.length());
                else
                    totalPhone = countryCodeSt + "-" + phone;
            } else
                totalPhone = countryCodeSt + "-" + phone;
        }
        if (TextUtils.isEmpty(username)) {
            this.userName.setError(getString(R.string.required));
            valid = false;
        } else {
            this.userName.setError(null);
        }
        if (TextUtils.isEmpty(studentid)) {
            this.studentId.setError(getString(R.string.required));
            valid = false;
        } else {
            this.studentId.setError(null);
        }
        if (!repassword.equals(password)) {
            this.userRePassword.setError(getString(R.string.password_mismatch_reg));
            valid = false;
        } else {
            userRePassword.setError(null);
        }

        if (password.length()>0 && password.length()< 6) {
            this.userRePassword.setError(getString(R.string.password_week));
            valid = false;
        }

        if (!valid) {

            return;
        }

//
//        if (TextUtils.isEmpty(email)) {
//            userEmail.setError(getString(R.string.required));
//            valid = false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            userEmail.setError(getString(R.string.invalid_email));
//            valid = false;
//        } else {
//            userEmail.setError(null);
//        }





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
//
//        if (TextUtils.isEmpty(userType)) {
////        if (password.length() < 6) {
//            //userPassword.setError(getString(R.string.required));
//            Toast.makeText(getActivity(), "Please Select User Type", Toast.LENGTH_SHORT).show();
//            valid = false;
//        }


        //Toast.makeText(getActivity(), "goto server", Toast.LENGTH_SHORT).show();

//        if(userType.equals("3"))
//        {
//            callStRegistrationApi(username, email, password, repassword, studentid, phone);
//        }
//        else {
//            if (uniCode.isEmpty())
//                callRegistrationWithNameApi(username, email, password, repassword, uniname);
//            else
//                callRegistrationApi(username, email, password, repassword, uniCode);
//        }
        callEditProfileApi(username, studentid, totalPhone, password);
    }


    private void callEditProfileApi(String username, String studentid, String totalPhone, String password) {

        List<String> filePaths = new ArrayList<>();

        File myFile = null;
        MultipartBody.Part body = null;
        if(attachmentModelList.size()>0) {
            myFile = new File(attachmentModelList.get(0).getFilePath());
        }


        RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());
        RequestBody usernamerb = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody studentidrb = RequestBody.create(MediaType.parse("multipart/form-data"), studentid);
        RequestBody totalPhonerb = RequestBody.create(MediaType.parse("multipart/form-data"), totalPhone);
        RequestBody passwordrb = RequestBody.create(MediaType.parse("multipart/form-data"), password);

        // create RequestBody instance from file
        if(attachmentModelList.size()>0) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);

            // MultipartBody.Part is used to send also the actual file name
            body = MultipartBody.Part.createFormData("photo", myFile.getName(), requestFile);
        }


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().userEditProfile(body, apiKey, usernamerb, studentidrb, totalPhonerb, passwordrb)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<EditProfileResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<EditProfileResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        EditProfileResponse loginApiModel = value.body();
                       // if(value.code()==200){
                        if (loginApiModel.getStatus().getCode()!= null && loginApiModel.getStatus().getCode() == 200) {
                            // Toast.makeText(getActivity(), "Image Upload Success", Toast.LENGTH_SHORT).show();
//                            fragment = new SuccessFragment();
//                            gotoFragment(fragment, "successFragment");
                            AppSharedPreference.setUserNameAndPassword(loginApiModel.getData().getId(),loginApiModel.getData().getEmail(), AppSharedPreference.getUserPassword(), AppSharedPreference.getApiKey(), AppSharedPreference.getRememberMe(), loginApiModel.getData().getUserType(), loginApiModel.getData().getImage(), loginApiModel.getData().getName(), loginApiModel.getData().getStudentId(), loginApiModel.getData().getMobile());
                            if(getActivity()!=null)
                                getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                          //  Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                       // Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
