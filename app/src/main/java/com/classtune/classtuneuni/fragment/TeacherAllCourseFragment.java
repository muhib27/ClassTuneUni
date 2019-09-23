package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.RelatedCourseAdapter;
import com.classtune.classtuneuni.adapter.StCourseAdapter;
import com.classtune.classtuneuni.adapter.TeacherCourseAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.CourseListResponse;
import com.classtune.classtuneuni.course_resonse.Instructor;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.PaginationScrollListener;
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
public class TeacherAllCourseFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {
    RecyclerView recyclerView;
    private List<Course> courseList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    TeacherCourseAdapter stCourseAdapter;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private String id = "";

    private TextView totalCourse, instructor;
    CircleImageView profile_image;

    public TeacherAllCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_all_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());

        if (getArguments().getString("id") != null)
            id = getArguments().getString("id");

        recyclerView = view.findViewById(R.id.recyclerView);

        instructor = view.findViewById(R.id.instructor);
        totalCourse = view.findViewById(R.id.totalCourse);
        profile_image = view.findViewById(R.id.profile_image);

        courseList = new ArrayList<>();

//        courseList.add(new Course("Title"));
//        courseList.add(new Course("Title"));
//        courseList.add(new Course("Title"));

        stCourseAdapter = new TeacherCourseAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(id!=null && !id.isEmpty())
                    callAllCourseListNextApi();
                //callStAssignmentListNextApi(GlobalOfferedCourseSectionId);
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
        recyclerView.setAdapter(stCourseAdapter);

        if(id!=null && !id.isEmpty())
        callAllCourseListApi();

        //stCourseAdapter.addAllData(courseList);

    }



    private void callAllCourseListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSingleTeacherAllCourseList(AppSharedPreference.getApiKey(), 0, id)

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
                        stCourseAdapter.clear();
                        if (courseListResponse.getStatus().getCode() == 200) {
//
                            courseList = courseListResponse.getData().getCourses();
                            populateData(courseListResponse.getData().getInstructor(), courseListResponse.getData().getTotal());

                            stCourseAdapter.addAllData(courseList);
                            TOTAL_PAGES = courseListResponse.getData().getTotalPage();

                            if (currentPage < (TOTAL_PAGES - 1)) stCourseAdapter.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
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

    private void populateData(Instructor insTructor, String total) {
        if(total !=null)
        totalCourse.setText(total);
        if(insTructor.getInstructor()!=null)
            instructor.setText(insTructor.getInstructor());
        if(insTructor.getInstructorImage()!=null) {
            Glide.with(getActivity())
                    .load(insTructor.getInstructorImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.news_poster)
                            .fitCenter())
                    .into(profile_image);
        }


    }

    private void callAllCourseListNextApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        // uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSingleTeacherAllCourseList(AppSharedPreference.getApiKey(), currentPage, id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<CourseListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<CourseListResponse> value) {
                        // uiHelper.dismissLoadingDialog();

                        CourseListResponse courseListResponse = value.body();
                        if (courseListResponse.getStatus().getCode() == 200) {
                            stCourseAdapter.removeLoadingFooter();
                            isLoading = false;
                            courseList = courseListResponse.getData().getCourses();

                            stCourseAdapter.addAllData(courseList);
                            if (currentPage < (TOTAL_PAGES - 1)) stCourseAdapter.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        // uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //uiHelper.dismissLoadingDialog();
                    }
                });


    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void retryPageLoad() {

    }
}
