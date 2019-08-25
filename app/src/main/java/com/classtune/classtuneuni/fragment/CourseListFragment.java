package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.CourseListAdapter;
import com.classtune.classtuneuni.adapter.TeacherExamListAdapter;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.CourseListResponse;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.response.NoticeResonse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

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
public class CourseListFragment extends Fragment implements CourseListAdapter.ItemListener, View.OnClickListener {

    RecyclerView recyclerView;
    private List<Course> courseList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    CourseListAdapter courseListAdapter;
    FloatingActionButton fabAdd;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        courseList = new ArrayList<>();
//
//        examList = new ArrayList<>();

//        examList.add(new CourseModel( "Class Test 2","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
//        examList.add(new CourseModel( "Class Test 3","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
//        examList.add(new CourseModel( "Class Test 4","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
//        examList.add(new CourseModel( "Class Test 5","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));


        courseListAdapter = new CourseListAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseListAdapter);

        callCourseListApi();
    }

    @Override
    public void onItemClick(CourseModel item, int pos) {

    }

    private void callCourseListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getTeacherCourseList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CourseListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CourseListResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        CourseListResponse courseListResponse = value.body();
                        if (courseListResponse.getStatus().getCode() == 200) {
//
                            courseList = courseListResponse.getData().getCourses();
//
//
//                            List<String> dateList = new ArrayList<>();
//                            for (int r = 0; r < noticeList.size(); r++) {
//                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
//                                if (!dateList.contains(sub))
//                                    dateList.add(sub);
//                            }

//                            itemList = buildItemList(noticeList, dateList);
                            courseListAdapter.addAllData(courseList);
//                            Log.v("tt", noticeList.toString());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                Fragment fragment = new TeacherAddCourseFragment();
                gotoFragment(fragment, "teacherAddCourseFragment");
                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
