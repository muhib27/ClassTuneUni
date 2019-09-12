package com.classtune.classtuneuni.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.StMsgAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.message.StCourseDiscussion;
import com.classtune.classtuneuni.message.StCourseMsgResponse;
import com.classtune.classtuneuni.message.StSendMsgResponse;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.PaginationScrollListener;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

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
import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatDetailsFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks, PaginationAdapterCallback {

    StMsgAdapter stMsgAdapter;
    UIHelper uiHelper;
    RecyclerView recyclerView;
    private List<StCourseDiscussion> courseDiscussionList;
    LinearLayoutManager linearLayoutManager;
    LinearLayout sendLl, upload;
    EditText msg;
    private static final int PICKFILE_RESULT_CODE = 1;

    private static final String[] STORAGE_AND_CAMERA =
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private static final int RC_STORAGE_CAMERA_PERM = 124;

    private static final int PAGE_START = 0;
    boolean f = false;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    public ChatDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_details, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.rv_item);
        msg = view.findViewById(R.id.msg);
        sendLl = view.findViewById(R.id.send);
        sendLl.setOnClickListener(this);

        upload = view.findViewById(R.id.upload);
        upload.setOnClickListener(this);

        uiHelper = new UIHelper(getActivity());
        courseDiscussionList = new ArrayList<>();
        stMsgAdapter = new StMsgAdapter(getActivity());

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                callMsgApiNext(GlobalOfferedCourseSectionId);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        recyclerView.setAdapter(stMsgAdapter);

        if(AppSharedPreference.getUserType().equals("3"))
        {
            callMsgApi(GlobalOfferedCourseSectionId, true);

        }
        else {

           // callOfferedCoursesApi();
        }


        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    callMsgApi(GlobalOfferedCourseSectionId, true);

                }
                else {
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                    //callOfferedCoursesApi();
                }
            }
        });
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
    @AfterPermissionGranted(RC_STORAGE_CAMERA_PERM)
    public void readStorageStateTask() {
        if (hasStorageAndCameraPermission()) {

            // Toast.makeText(this, "TODO: Phone State things", Toast.LENGTH_LONG).show();

            browse();
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


    private void browse(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");

        try {
            startActivityForResult(intent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {

//                    Toast.makeText(this, R.string.no_filemanager_installed,
//                            Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:

                if (resultCode == RESULT_OK) {

                    String filePath = null;
                    long fileSize = 0;
                    String displayName = null;
                    Uri uri = data.getData();
                    Cursor c = getActivity().getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA,
                            MediaStore.MediaColumns.MIME_TYPE,
                            MediaStore.MediaColumns.DISPLAY_NAME,
                            MediaStore.MediaColumns.SIZE
                    }, null, null, null);
                    if (c != null && c.moveToFirst()) {
                        int id = c.getColumnIndex(MediaStore.Images.Media.DATA);
                        if (id != -1) {
                            filePath = data.getData().getPath();
                        }
                        displayName = c.getString(2);
                        fileSize = c.getLong(3);
                    }
                    if (filePath != null) {
//                        final File dir = new File(getActivity().getFilesDir() + filePath);
//                        dir.mkdirs(); //create folders where write files
//                        final File file = new File(dir, displayName);
                        Uri selectedImageUri = data.getData();
                        String path = uiHelper.getPath(getActivity(), selectedImageUri);
                        //mEditText.setText(filePath);
                        //String strFileSize = getString(R.string.get_content_info,
                        //      displayName, Long.toString(fileSize));
                        //textFile.setText(strFileSize);
                        AttachmentModel attachmentModel = new AttachmentModel(displayName, path);
                        //callPhotoApi(GlobalOfferedCourseSectionId, attachmentModel);
                        createConfirmDialog(GlobalOfferedCourseSectionId, attachmentModel);
                       // addAttachment(attachmentModel);
                    }
                    if (c != null && !c.isClosed()) {
                        c.close();
                    }
                }
                break;

        }
    }

    private void callMsgApi(String globalOfferedCourseSectionId, boolean b) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if(b)
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSubjectMessage(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, 0)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StCourseMsgResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StCourseMsgResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StCourseMsgResponse stCourseMsgResponse = value.body();
                        if (stCourseMsgResponse.getStatus().getCode() == 200) {
                            stMsgAdapter.clear();
//
                            courseDiscussionList = stCourseMsgResponse.getData().getCourseDiscussion();
//

                            stMsgAdapter.addAllData(courseDiscussionList);
                            TOTAL_PAGES = stCourseMsgResponse.getData().getTotal_page();

                            if (currentPage < (TOTAL_PAGES-1)) stMsgAdapter.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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


    private void callMsgApiNext(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }


        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSubjectMessage(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StCourseMsgResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StCourseMsgResponse> value) {
                        //uiHelper.dismissLoadingDialog();

                        StCourseMsgResponse stCourseMsgResponse = value.body();
                        if (stCourseMsgResponse.getStatus().getCode() == 200) {
                            stMsgAdapter.removeLoadingFooter();
                            isLoading = false;
//
                            courseDiscussionList = stCourseMsgResponse.getData().getCourseDiscussion();
//

                            stMsgAdapter.addAllData(courseDiscussionList);

                            if (currentPage < (TOTAL_PAGES-1)) stMsgAdapter.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                       // uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                      //  uiHelper.dismissLoadingDialog();
                    }
                });


    }


    private void callPhotoApi(String globalOfferedCourseSectionId, AttachmentModel attachmentModel) {


        List<String> filePaths = new ArrayList<>();


        File myFile = new File(attachmentModel.getFilePath());
        String st = myFile.getPath();
        String ss = AppSharedPreference.getApiKey();
        String sss = myFile.getName();

        RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());
        RequestBody globalOfferedCourseSectionIdrb = RequestBody.create(MediaType.parse("multipart/form-data"), globalOfferedCourseSectionId);

        // create RequestBody instance from file
        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", myFile.getName(), requestFile);


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        //uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().stSentPhot(body,globalOfferedCourseSectionIdrb, apiKey)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StSendMsgResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StSendMsgResponse> value) {
                       // uiHelper.dismissLoadingDialog();

                        if (value.code() == 200) {
                            //Toast.makeText(getActivity(), "Image Upload Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Image Upload failed", Toast.LENGTH_SHORT).show();
                        //uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //uiHelper.dismissLoadingDialog();
                    }
                });

    }


    private void callSendMsgApi(String globalOfferedCourseSectionId, final String text) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        //uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().stSendMessage(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, text)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StSendMsgResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StSendMsgResponse> value) {
                   //     uiHelper.dismissLoadingDialog();

                        StSendMsgResponse stSendMsgResponse = value.body();
                        if (stSendMsgResponse.getStatus().getCode() == 200) {
                            callMsgApi(GlobalOfferedCourseSectionId, false);
                            msg.setText("");
//

//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                      //  uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                      //  uiHelper.dismissLoadingDialog();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                if(!msg.getText().toString().trim().isEmpty())
                    callSendMsgApi(GlobalOfferedCourseSectionId, msg.getText().toString().trim());
                break;
            case R.id.upload:
                readStorageStateTask();
                break;
        }
    }


//    private List<Item> buildItemList() {
//        List<Item> itemList = new ArrayList<>();
//        for (int i=0; i<10; i++) {
//            Item item = new Item("Item "+i, buildSubItemList());
//            itemList.add(item);
//        }
//        return itemList;
//    }
//
//    private List<SubItem> buildSubItemList() {
//        List<SubItem> subItemList = new ArrayList<>();
//        for (int i=0; i<3; i++) {
//            SubItem subItem = new SubItem("Sub Item "+i, "Description "+i);
//            subItemList.add(subItem);
//        }
//        return subItemList;
//    }


    private void createConfirmDialog(String globalOfferedCourseSectionId, final AttachmentModel attachmentModel) {

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.send_photo_dialog_view);



        Button cancel = dialog.findViewById(R.id.cancel);
        Button send = dialog.findViewById(R.id.send);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhotoApi(GlobalOfferedCourseSectionId, attachmentModel);
                dialog.dismiss();

            }
        });


        dialog.show();

    }

    @Override
    public void retryPageLoad() {

    }
}
