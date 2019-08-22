package com.classtune.classtuneuni.fragment;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.assignment.AssignmentSectionResponse;
import com.classtune.classtuneuni.assignment.Status;
import com.classtune.classtuneuni.course_resonse.CourseOfferSectionResponse;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAssignmentFragment extends Fragment implements View.OnClickListener {

    RadioGroup rg;
    RadioButton yes, no;
    ViewGroup main, attachmentView;
    TextView browse;
    private List<String> sectionList;
    private List<AttachmentModel> attachmentModelList;

    private String endDateFormatServerString = "";

    private Button create;
    private EditText title, description, marks;
    private TextView dueDate;
    UIHelper uiHelper;
    LinearLayout marksLl;
    private static final int PICKFILE_RESULT_CODE = 1;

    public CreateAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_assignment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        sectionList = new ArrayList<>();
        attachmentModelList = new ArrayList<>();

        uiHelper = new UIHelper(getActivity());
        rg = view.findViewById(R.id.rg);
        yes = rg.findViewById(R.id.yes);
        no = rg.findViewById(R.id.no);

        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);

        marks = view.findViewById(R.id.marks);
        dueDate = view.findViewById(R.id.endDate);
        dueDate.setOnClickListener(this);

        create = view.findViewById(R.id.create);
        create.setOnClickListener(this);



        main = view.findViewById(R.id.sectionLl);
        attachmentView = view.findViewById(R.id.attachmentLl);

        marksLl = view.findViewById(R.id.marksLl);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i == R.id.yes) {
                    marksLl.setVisibility(View.VISIBLE);
                } else
                    marksLl.setVisibility(View.GONE);
            }
        });

        browse = view.findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//                startActivityForResult(intent,PICKFILE_RESULT_CODE);
              //  intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                } catch (ActivityNotFoundException e) {
                    // No compatible file manager was found.
//                    Toast.makeText(this, R.string.no_filemanager_installed,
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });

      //  callOfferedSectionListApi();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                //   if(resultCode==RESULT_OK){
//                    String FilePath = data.getData().getPath();
//                    textFile.setText(FilePath + data.getData());
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
                        String path = getPath(getActivity(), selectedImageUri);
                        //mEditText.setText(filePath);
                        //String strFileSize = getString(R.string.get_content_info,
                        //      displayName, Long.toString(fileSize));
                        //textFile.setText(strFileSize);
                        AttachmentModel attachmentModel = new AttachmentModel(displayName, path);
                        addAttachment(attachmentModel);
                    }
                    if (c != null && !c.isClosed()) {
                        c.close();
                    }
                }
                break;

        }
    }

    private List<String> getTotalSection() {
        //int text = 0;
        String tag = "";
        List<String> list = new ArrayList<>();
        for (int i = 0; i < main.getChildCount(); i++) {
            View nextChild = main.getChildAt(i);
            //if(nextChild.getId() == i){
            if (((CheckBox) nextChild).isChecked()) {
                tag = ((CheckBox) nextChild).getTag().toString();
                //  }
                list.add(tag);
            }

        }

        return list;
    }

    static int count = 0;
    TextView textView;
    View view1;
    CheckBox checkBox;

    private void addSection(List<AssignmentSection> sectionName) {
        main.removeAllViews();
        for (int i = 0; i < sectionName.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.checkbox_section_view, main, false);
            checkBox = view1.findViewById(R.id.cb);
            // ll = (LinearLayout) view1.findViewById(R.id.ll);
            checkBox.setText(sectionName.get(i).getSectionName());
            checkBox.setTag(Integer.parseInt(sectionName.get(i).getOfferedSectionId()));
            main.addView(checkBox, i);
        }
    }

    int pos;

    private void addAttachment(AttachmentModel attachmentModel) {
        boolean flag = true;
        for(int i = 0; i<attachmentModelList.size();i++) {
            if(attachmentModelList.get(i).getFileName().equals(attachmentModel.getFileName())) {
                flag = false;
                return;
            }
        }
        if(flag)
            attachmentModelList.add(attachmentModel);


        //attachmentView.removeAllViews();
        //for (int i = 0; i < sectionName.size(); i++) {
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.attachment_view, attachmentView, false);
        //checkBox = view1.findViewById(R.id.cb);
        TextView name = view1.findViewById(R.id.name);
        name.setText(attachmentModel.getFileName());

        int i = attachmentView.getChildCount();

        final ImageView imageView = view1.findViewById(R.id.delete);
        imageView.setTag(attachmentModel.getFileName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  int r = Integer.parseInt(imageView.getTag().toString());
                View viewDelete = attachmentView.findViewWithTag(imageView.getTag());
                attachmentView.removeView((View) viewDelete.getParent());
                for(int i = 0; i<attachmentModelList.size();i++) {
                    if(attachmentModelList.get(i).getFileName().equals(imageView.getTag())) {
                        pos = i;
                        break;
                    }
                }

                attachmentModelList.remove(pos);

            }
        });

        // checkBox.setText(sectionName.get(i).getFileName());
        //checkBox.setId(Integer.parseInt(sectionName.get(i).getFilePath()));
        attachmentView.addView(view1, attachmentView.getChildCount());
        // }
    }

//    private List<AttachmentModel> getTotalAttachment() {
//        int text = 0;
//        List<AttachmentModel> list = new ArrayList<>();
//        for (int i = 0; i < attachmentView.getChildCount(); i++) {
//            View nextChild = main.getChildAt(i);
//            //if(nextChild.getId() == i){
//            if (((CheckBox) nextChild).isChecked()) {
//                text = ((CheckBox) nextChild).getId();
//                //  }
//                list.add(String.valueOf(text));
//            }
//
//        }
//
//        return list;
//    }

    private void callOfferedSectionListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getOfferedSectionList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<AssignmentSectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<AssignmentSectionResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        AssignmentSectionResponse assignmentSectionResponse = value.body();
                        if (assignmentSectionResponse.getStatus().getCode() == 200) {
                            addSection(assignmentSectionResponse.getData().getSections());

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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.endDate:
                showEndDatepicker();
                break;
            case R.id.create:
                createAssignment();
                break;
        }
    }

    String titleSt = "", descriptionSt = "", marksSt = "", sectionSt = "";

    private void createAssignment() {

        titleSt = title.getText().toString().trim();


        if (TextUtils.isEmpty(titleSt)) {
            title.setFocusable(true);
            title.setError(getString(R.string.required));
            title.requestFocus();
            return;
        }
        if(yes.isChecked())
        {
            marksSt = marks.getText().toString().trim();
            if (TextUtils.isEmpty(marksSt)) {
                marks.setFocusable(true);
                marks.setError(getString(R.string.required));
                marks.requestFocus();
                return;
            }
        }


//        sectionList = getTotalSection();
//
//        sectionSt = "";
//        for (int i = 0; i < sectionList.size(); i++) {
//            if ((sectionList.size() - 1) == i)
//                sectionSt = sectionSt + sectionList.get(i);
//            else
//                sectionSt = sectionSt + sectionList.get(i) + "|";
//        }
        callAssignmentCreateApi();
    }


    private void showEndDatepicker() {
        DatePickerFragment picker = new DatePickerFragment();
        picker.setCallbacks(endDatePickerCallback);
        picker.show(getFragmentManager(), "enddatePicker");
    }

    DatePickerFragment.DatePickerOnSetDateListener endDatePickerCallback = new DatePickerFragment.DatePickerOnSetDateListener() {

        @Override
        public void onDateSelected(int month, String monthName, int day,
                                   int year, String dateFormatServer, String dateFormatApp,
                                   Date date) {
            // TODO Auto-generated method stub
            dueDate.setText(dateFormatApp);
            // chooseEndDateTextView.setText(dateFormatApp);
            endDateFormatServerString = dateFormatServer;
        }
    };

    RequestBody rbDueDate, rbApiKey, rbTitle, rbDescription;
    MultipartBody.Part body[];

    private void callAssignmentCreateApi() {

        MultipartBody.Part body[] = new MultipartBody.Part[10];

       int pos =  ((MainActivity)getActivity()).mTabHost.getCurrentTab();
       AssignmentSection assignmentSectionTab = AppSharedPreference.getUserTab(((MainActivity)getActivity()).mTabHost.getCurrentTabTag(), pos);
        String assessment = "";
        String courseId = assignmentSectionTab.getCourseId();
        String offered_section_id = assignmentSectionTab.getOfferedSectionId();
        if(yes.isChecked())
        {
            assessment = "1";
            marksSt = marks.getText().toString().trim();
        }


        //File myFile = new File(selectedFilePath);


//        rbApiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());
//        rbTitle = RequestBody.create(MediaType.parse("multipart/form-data"), title.getText().toString().trim());
//        rbDescription = RequestBody.create(MediaType.parse("multipart/form-data"), description.getText().toString().trim());
//        if (!selectedDate.equalsIgnoreCase("Due Date"))
//            rbDueDate = RequestBody.create(MediaType.parse("multipart/form-data"), dueDate.getText().toString());


        List<String> filePaths = new ArrayList<>();


//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//
//
//        builder.addFormDataPart("offered_section_id", offered_section_id);
//        builder.addFormDataPart("course_id", courseId);
//        builder.addFormDataPart("title", title.getText().toString().trim());
//        builder.addFormDataPart("description", description.getText().toString().trim());
//        builder.addFormDataPart("api_key", AppSharedPreference.getApiKey());
//        builder.addFormDataPart("due_date", endDateFormatServerString);
//        builder.addFormDataPart("assessment", assessment);
//        builder.addFormDataPart("max_marks", marksSt);
//
//
//        for (int i = 0; i < attachmentModelList.size(); i++) {
//            File file = new File(attachmentModelList.get(i).getFilePath());
//            builder.addFormDataPart("files[" + i + "]", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
//        }
//
//
//        MultipartBody requestBody = builder.build();


        RequestBody apiKey = RequestBody.create(MediaType.parse("multipart/form-data"), AppSharedPreference.getApiKey());
        RequestBody course_id = RequestBody.create(MediaType.parse("multipart/form-data"), courseId);
        RequestBody offeredSectionId = RequestBody.create(MediaType.parse("multipart/form-data"),offered_section_id);
        RequestBody asseessmentRb = RequestBody.create(MediaType.parse("multipart/form-data"), assessment);
        RequestBody marksRb = RequestBody.create(MediaType.parse("multipart/form-data"),marksSt);
        RequestBody titleRb = RequestBody.create(MediaType.parse("multipart/form-data"), title.getText().toString().trim());
        RequestBody descriptionRb = RequestBody.create(MediaType.parse("multipart/form-data"), description.getText().toString().trim());
        RequestBody dueDateRb = RequestBody.create(MediaType.parse("multipart/form-data"), endDateFormatServerString);

        for (int i = 0; i < attachmentModelList.size(); i++) {

            File myFile = new File(attachmentModelList.get(i).getFilePath());
            // create RequestBody instance from file
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);

            // MultipartBody.Part is used to send also the actual file name
            body[i] = MultipartBody.Part.createFormData("files[]", myFile.getName(), requestFile);
        }


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Submitting your assignment...");
//        String[] users = new String[selectedList.size()];
//        users = selectedList.toArray(users);

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterface().getTaskAssign(body, apiKey, course_id, offeredSectionId, asseessmentRb, marksRb, titleRb, descriptionRb, dueDateRb)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();


                        //Status status = value.body();
                        if (value.code() == 200) {
                            //Log.v("PigeonholeFragment", value.message());
                            Toast.makeText(getActivity(), "Your task successfully submitted", Toast.LENGTH_SHORT).show();
                            //clearForm();
                        } else
                            Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "Task Submission failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    @TargetApi(19)
    @SuppressLint("NewApi")
    public static String getPath(final Activity context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        } else {
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.managedQuery(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
        }
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
}
