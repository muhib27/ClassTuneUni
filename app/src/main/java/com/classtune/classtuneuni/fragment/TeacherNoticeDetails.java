package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.notice.NoticcCourseSection;
import com.classtune.classtuneuni.notice.NoticeDetails;
import com.classtune.classtuneuni.notice.NoticeDetailsResponse;
import com.classtune.classtuneuni.response.CourseSection;
import com.classtune.classtuneuni.response.NoticeInfo;
import com.classtune.classtuneuni.response.NoticeOfferResponse;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherNoticeDetails extends Fragment implements View.OnClickListener {


    TextView title, course, section, date, description;
    UIHelper uiHelper;
    Button back, edit, delete;
    String noticeId ="";
    List<NoticcCourseSection> noticeSectionList;


    public TeacherNoticeDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_notice_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // ((MainActivity)getActivity()).bottomBar.selectTabAtPosition(4);
        if(((MainActivity) Objects.requireNonNull(getActivity())).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getArguments() !=null && getArguments().getString("noticeId") !=null  )
        noticeId = getArguments().getString("noticeId");
        uiHelper = new UIHelper(getActivity());

        noticeSectionList = new ArrayList<>();

        title = view.findViewById(R.id.title);
        course = view.findViewById(R.id.course);
        section = view.findViewById(R.id.section);
        date = view.findViewById(R.id.date);
        description = view.findViewById(R.id.description);

//        back = view.findViewById(R.id.back);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);
//
//        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);


        if (AppSharedPreference.getUserType().equals("3")) {
            callStNoticeDetails(noticeId);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        else {
            callNoticeDetails(noticeId);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }

    }

    private void callNoticeDetails(String noticeId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getNoticeDetails(AppSharedPreference.getApiKey(), noticeId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NoticeDetailsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NoticeDetailsResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        NoticeDetailsResponse noticeDetailsResponse = value.body();
                        if (noticeDetailsResponse.getStatus().getCode() == 200) {
                            populateData(noticeDetailsResponse.getData());

                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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

    private void callStNoticeDetails(String noticeId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStNoticeDetails(AppSharedPreference.getApiKey(), noticeId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NoticeDetailsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NoticeDetailsResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        NoticeDetailsResponse noticeDetailsResponse = value.body();
                        if (noticeDetailsResponse.getStatus().getCode() == 200) {
                            populateStudentData(noticeDetailsResponse.getData().getSingleNotice());

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                          //  Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
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

    private void populateData(NoticeDetails data) {
        noticeSectionList = data.getCourseSection();
        getCourseSection(data.getCourseSection());
        title.setText(data.getNotices().getTitle());
        date.setText(data.getNotices().getCreatedAt());
        description.setText(data.getNotices().getDescriptions());
      //  description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }
    private void populateStudentData(NoticeInfo singleNotice) {
//        noticeSectionList = data.getCourseSection();
//        getCourseSection(data.getCourseSection());
        title.setText(singleNotice.getTitle());
        if(singleNotice.getCreatedAt()!= null && singleNotice.getCreatedAt().contains(" "))
        {
            String[] parts = singleNotice.getCreatedAt().split(" ");
            if(parts.length>0)
            date.setText(AppUtility.getDateString(parts[0], AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER));
        }
        description.setText(singleNotice.getDescriptions());
        course.setText(singleNotice.getCourseName());
        section.setText(singleNotice.getInstructor());
        //  description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

    }

    String courseText="", sectionText="";
    private void getCourseSection(List<NoticcCourseSection> courseSection) {
        for(int i=0; i<courseSection.size();i++){
            courseText = courseText + courseSection.get(i).getCourseName() + " " + "\n";
            sectionText = sectionText + courseSection.get(i).getSectionName() + " ";
        }
        course.setText(courseText);
        section.setText(sectionText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                break;
            case R.id.edit:
                callNoticeSection();
                break;
            case R.id.delete:
                callDeleteApi(noticeId);
                break;
        }
    }

    Fragment fragment;
    private void callDeleteApi(String noticeId) {
        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().deleteNotice(AppSharedPreference.getApiKey(), noticeId)

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

                            fragment =new  NoticeListFragment();
                            gotoFragment(fragment, "noticeListFragment");
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callNoticeSection() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getTeacherNitceType(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NoticeOfferResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NoticeOfferResponse> value) {
                        uiHelper.dismissLoadingDialog();
                        noticeOfferResponseList = new ArrayList<>();

                        NoticeOfferResponse noticeOfferResponse = value.body();
                        if (noticeOfferResponse.getStatus().getCode() == 200) {

                            noticeOfferResponseList = noticeOfferResponse.getNoticeOfferData().getSections();

                            createNoticeDialog();
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

    View view1;
    LinearLayout ll;
    CheckBox checkBox;
    ViewGroup main;
    List<Section> noticeOfferResponseList;
    List<String> list1;

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

    private String getNoticeIdString(List<String> list1) {
        String idString = "";
        for (int i = 0; i < list1.size(); i++) {
            if (i == (list1.size() - 1))
                idString = idString + list1.get(i);
            else
                idString = idString + list1.get(i) + "|";
        }
        return idString;
    }

    private void createNoticeDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_notice_dialog_view);


        title = dialog.findViewById(R.id.title);
        description = dialog.findViewById(R.id.description);
//        spinner = dialog.findViewById(R.id.input1);
//        List<String> list = new ArrayList<String>();
//        list.add("List1");
//        list.add("List2");
//        spinner.setItems(list);
//
//        List<String> sl = spinner.getSelectedStrings();
        //addSection(noticeOfferResponseList, dialog);


        //  View view1 = inflater.inflate(R.layout.checkbox_section_view, null);

        main = dialog.findViewById(R.id.sectionLl);
        main.removeAllViews();
        for (int i = 0; i < noticeOfferResponseList.size(); i++) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.checkbox_section_view, main, false);
            checkBox = view1.findViewById(R.id.cb);
            // ll = (LinearLayout) view1.findViewById(R.id.ll);
            for(int j = 0; j<noticeSectionList.size(); j++) {
               // noticeSectionList.get(j).g
                checkBox.setText(noticeOfferResponseList.get(i).getName());
                checkBox.setId(Integer.parseInt(noticeOfferResponseList.get(i).getId()));
            }
            main.addView(checkBox, i);
        }


//        ArrayAdapter<Section> adapter =
//                new ArrayAdapter<Section>(getActivity(),  android.R.layout.simple_spinner_dropdown_item, noticeOfferResponseList);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        list1 = new ArrayList<>();
        Button create = dialog.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleSt = "", descriptionSt = "";
                if (!title.getText().toString().trim().isEmpty())
                    titleSt = title.getText().toString().trim();
                if (!description.getText().toString().trim().isEmpty())
                    descriptionSt = description.getText().toString().trim();

                list1 = getTotalSection();
                if (list1.size() > 0) {
                    noticeId = getNoticeIdString(list1);
//                    if (!titleSt.isEmpty() && !noticeId.isEmpty())
//                        callAddNoticeApi(titleSt, descriptionSt, noticeId);
                    dialog.dismiss();
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
}
