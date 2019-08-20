package com.classtune.classtuneuni.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.course_resonse.CourseOfferSectionResponse;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAssignmentFragment extends Fragment {

    RadioGroup rg;
    RadioButton yes, no;
    ViewGroup main, attachmentView;
    TextView browse;

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

        uiHelper = new UIHelper(getActivity());
        rg = view.findViewById(R.id.rg);
        yes = rg.findViewById(R.id.yes);
        no = rg.findViewById(R.id.no);

        main = view.findViewById(R.id.sectionLl);
        attachmentView = view.findViewById(R.id.attachmentLl);

        marksLl = view.findViewById(R.id.marksLl);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(i == R.id.yes)
                {
                    marksLl.setVisibility(View.VISIBLE);
                }
                else
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
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                } catch (ActivityNotFoundException e) {
                    // No compatible file manager was found.
//                    Toast.makeText(this, R.string.no_filemanager_installed,
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        callOfferedSectionListApi();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                //   if(resultCode==RESULT_OK){
//                    String FilePath = data.getData().getPath();
//                    textFile.setText(FilePath + data.getData());
                if(resultCode==RESULT_OK){
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
                        //mEditText.setText(filePath);
                        //String strFileSize = getString(R.string.get_content_info,
                          //      displayName, Long.toString(fileSize));
                        //textFile.setText(strFileSize);
                        AttachmentModel attachmentModel = new AttachmentModel(displayName, filePath);
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
        int text = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < main.getChildCount(); i++) {
            View nextChild = main.getChildAt(i);
            //if(nextChild.getId() == i){
            if (((CheckBox) nextChild).isChecked()) {
                text = ((CheckBox) nextChild).getId();
                //  }
                list.add(String.valueOf(text));
            }

        }

        return list;
    }

    static int count = 0;
    TextView textView;
    View view1;
    CheckBox checkBox;

    private void addSection(List<Section> sectionName) {
        main.removeAllViews();
        for (int i = 0; i < sectionName.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.checkbox_section_view, main, false);
            checkBox = view1.findViewById(R.id.cb);
            // ll = (LinearLayout) view1.findViewById(R.id.ll);
            checkBox.setText(sectionName.get(i).getName());
            checkBox.setId(Integer.parseInt(sectionName.get(i).getId()));
            main.addView(checkBox, i);
        }
    }

    private void addAttachment(AttachmentModel attachmentModel) {
        //attachmentView.removeAllViews();
        //for (int i = 0; i < sectionName.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.attachment_view, attachmentView, false);
            //checkBox = view1.findViewById(R.id.cb);

           // checkBox.setText(sectionName.get(i).getFileName());
            //checkBox.setId(Integer.parseInt(sectionName.get(i).getFilePath()));
        attachmentView.addView(view1);
       // }
    }

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
                .subscribe(new Observer<Response<CourseOfferSectionResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CourseOfferSectionResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        CourseOfferSectionResponse courseOfferSectionResponse = value.body();
                        if (courseOfferSectionResponse.getStatus().getCode() == 200) {
                            addSection(courseOfferSectionResponse.getData().getSections());

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
}
