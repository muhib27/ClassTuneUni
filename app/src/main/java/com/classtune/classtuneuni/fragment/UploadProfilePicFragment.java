package com.classtune.classtuneuni.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.URLHelper;
import com.google.gson.JsonElement;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.AudioPickActivity;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.activity.VideoPickActivity;
import com.vincent.filepicker.filter.entity.AudioFile;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.vincent.filepicker.filter.entity.NormalFile;
import com.vincent.filepicker.filter.entity.VideoFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import static com.vincent.filepicker.activity.AudioPickActivity.IS_NEED_RECORDER;
import static com.vincent.filepicker.activity.BaseActivity.IS_NEED_FOLDER_LIST;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadProfilePicFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    UIHelper uiHelper;
    Button registerBtn;
    LinearLayout skipBtn;
    Fragment fragment;
    FloatingActionButton fab;
    String filePathSt = "";
    de.hdodenhof.circleimageview.CircleImageView profileImg;

    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_STORAGE_CAMERA_PERM = 124;

    public UploadProfilePicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_pic_upload_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        uiHelper = new UIHelper(getActivity());
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        profileImg = view.findViewById(R.id.profile_image);
        skipBtn = view.findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(this);

        registerBtn = view.findViewById(R.id.register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragment = new SuccessFragment();
//                gotoFragment(fragment, "successFragment");
                callImageUploadApi();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                readStorageStateTask();
                break;
            case R.id.skipBtn:
                gotoMain();
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
                        .into(profileImg);
                else
                    Toast.makeText(getActivity(), "Image is too large", Toast.LENGTH_LONG).show();
            }
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


    private void callImageUploadApi() {

        List<String> filePaths = new ArrayList<>();

//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//
//
//        builder.addFormDataPart("api_key", AppSharedPreference.getApiKey());
//
//
//        if (attachmentModelList.get(0).getFilePath() != null && !attachmentModelList.get(0).getFilePath().isEmpty()) {
//            // for (int i = 0; i < attachmentModelList.size(); i++) {
//            File file = new File(attachmentModelList.get(0).getFilePath());
//            builder.addFormDataPart("photo", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//            //  }
//        } else
//            return;

//
//        MultipartBody requestBody = builder.build();

        File myFile = new File(attachmentModelList.get(0).getFilePath());
        String st = myFile.getPath();
        String ss = AppSharedPreference.getApiKey();
        String sss = myFile.getName();

          RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());

        // create RequestBody instance from file
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", myFile.getName(), requestFile);


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().userImageUpload(body, apiKey)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();

                        if (value.code() == 200) {
                           // Toast.makeText(getActivity(), "Image Upload Success", Toast.LENGTH_SHORT).show();
                            fragment = new SuccessFragment();
                            gotoFragment(fragment, "successFragment");
                        } else
                            Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    private void browseFile() {
        Intent intent2 = new Intent(getActivity(), ImagePickActivity.class);
        intent2.putExtra(IS_NEED_CAMERA, true);
        intent2.putExtra(Constant.MAX_NUMBER, 1);
        intent2.putExtra(IS_NEED_FOLDER_LIST, true);
        startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);
    }

    private void gotoMain(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
}
