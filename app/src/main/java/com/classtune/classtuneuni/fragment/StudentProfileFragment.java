package com.classtune.classtuneuni.fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ComResultAdapter;
import com.classtune.classtuneuni.adapter.StProfileInfoAdapter;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.ProfileCourseModel;
import com.classtune.classtuneuni.profile.StCourseAssessment;
import com.classtune.classtuneuni.profile.StProfileData;
import com.classtune.classtuneuni.profile.StProfileRsponse;
import com.classtune.classtuneuni.response.StSectionListResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfileFragment extends Fragment implements StProfileInfoAdapter.ItemListener, View.OnClickListener {
    RecyclerView recyclerView;
    private List<StCourseAssessment> stCourseAssessmentList;
    LinearLayoutManager linearLayoutManager;
    StProfileInfoAdapter stProfileInfoAdapter;
    UIHelper uiHelper;
    ImageView editBtn;
    CircleImageView profileImg;
    private TextView name, studentId, email, currentCourse, totalCourse;
    public static final String BASE_URL= "http://uni.edoozz.com/";

    public StudentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper =new UIHelper(getActivity());

        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.recyclerView);

        stCourseAssessmentList = new ArrayList<>();

        profileImg = view.findViewById(R.id.profile_image);
        editBtn = view.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(this);
        name = view.findViewById(R.id.name);
        studentId = view.findViewById(R.id.studentId);
        email = view.findViewById(R.id.email);
        currentCourse = view.findViewById(R.id.currentCourse);
        totalCourse = view.findViewById(R.id.totalCourse);


        name.setText(AppSharedPreference.getUserName());
        email.setText(AppSharedPreference.getUserEmail());
        studentId.setText(AppSharedPreference.getUserId());

        String url =BASE_URL + AppSharedPreference.getUserImage();

        Glide.with(getActivity())
                .load(BASE_URL + AppSharedPreference.getUserImage())
                //.load("http://via.placeholder.com/300.png")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(profileImg);


        stProfileInfoAdapter = new StProfileInfoAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stProfileInfoAdapter);

        callStudentProfile();
    }


    private void callStudentProfile() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStProfile(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StProfileRsponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StProfileRsponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StProfileRsponse stProfileRsponse = value.body();
                        if (stProfileRsponse.getStatus().getCode() == 200) {
                          //  stAddSection(stSectionListResponse.getData().getCourseSection());
                            stCourseAssessmentList = stProfileRsponse.getData().getCourseAssessment();
                            stProfileInfoAdapter.addAllData(stCourseAssessmentList);
                            populateData(stProfileRsponse.getData());

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

    private void populateData(StProfileData data) {
//        //if(data.getProfile().getName()!=null)
//            name.setText(AppSharedPreference.getUserName());
//        //if(data.getProfile().getEmail()!=null)
//            email.setText(AppSharedPreference.getUserEmail());
//        //if(data.getProfile().getStudentId()!=null)
//            studentId.setText(AppSharedPreference.getUserId());
//
//        Glide.with(getActivity())
//                .load(BASE_URL + AppSharedPreference.getUserImage())
//                //.load("http://via.placeholder.com/300.png")
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        // log exception
//                        Log.e("TAG", "Error loading image", e);
//                        return false; // important to return false so the error placeholder can be placed
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(profileImg);

        if(data.getEnrolledCourse()!=null)
            currentCourse.setText(""+data.getEnrolledCourse());
        if(data.getCompletedCourse()!=null)
            totalCourse.setText(""+data.getCompletedCourse());

    }


    @Override
    public void onItemClick(ProfileCourseModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        SubjectResultFragment subjectResultFragment = new SubjectResultFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, subjectResultFragment, "subjectResultFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editBtn:
                break;
        }
    }
}
