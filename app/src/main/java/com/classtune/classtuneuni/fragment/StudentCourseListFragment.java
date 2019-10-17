package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.StCourseAdapter;
import com.classtune.classtuneuni.course_resonse.Course;
import com.classtune.classtuneuni.course_resonse.CourseListResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.PaginationScrollListener;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
import com.mancj.materialsearchbar.MaterialSearchBar;

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
public class StudentCourseListFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback, MaterialSearchBar.OnSearchActionListener {
    RecyclerView recyclerView;
    private List<Course> courseList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    StCourseAdapter stCourseAdapter;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;
    private static String searchKey = "";

    MaterialSearchBar searchBar;

    public StudentCourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (((MainActivity) getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity) getActivity()).tabRl.setVisibility(View.GONE);
        uiHelper = new UIHelper(getActivity());

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        recyclerView = view.findViewById(R.id.recyclerView);

        courseList = new ArrayList<>();
        searchKey = "";
        currentPage = 0;

//        courseList.add(new Course("Title"));
//        courseList.add(new Course("Title"));
//        courseList.add(new Course("Title"));

        stCourseAdapter = new StCourseAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if (searchKey.length() > 0)
                    callSearchCourseListNextApi(searchKey);
                else
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

        // stCourseAdapter.addAllData(courseList);
        callAllCourseListApi();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void retryPageLoad() {

    }


    private void callAllCourseListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAllCourseList(AppSharedPreference.getApiKey(), 0, "")

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

    private void callAllCourseListNextApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        // uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAllCourseList(AppSharedPreference.getApiKey(), currentPage, "")

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


    private void callSearchCourseListApi(String key) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAllCourseList(AppSharedPreference.getApiKey(), 0, key)

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

    private void callSearchCourseListNextApi(String key) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        // uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAllCourseList(AppSharedPreference.getApiKey(), currentPage, key)

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
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        if (s.equals("disabled")) {
            searchKey = "";
            currentPage = 0;
            isLastPage = false;
            callAllCourseListApi();
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        searchKey = text.toString().trim();
        callSearchCourseListApi(searchKey);
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    public MenuItem item;

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Do something that differs the Activity's menu here
//        super.onCreateOptionsMenu(menu, inflater);
//        item = menu.findItem(R.id.chat);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.chat:
//                if(AppSharedPreference.getStTabString().isEmpty())
//                    item.setEnabled(false);
//                else
//                    item.setEnabled(true);
//                return false;
//
//
//            default:
//                break;
//        }
//
//        return false;
//    }
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        if (AppSharedPreference.getStTabString().isEmpty())
//            menu.getItem(R.id.chat).setEnabled(false);
//        else
//            menu.getItem(R.id.chat).setEnabled(true);
//
//        //return true;
//    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (AppSharedPreference.getStTabString().isEmpty())
            menu.findItem(R.id.chat).setEnabled(false);
        else
            menu.findItem(R.id.chat).setEnabled(true);
        super.onPrepareOptionsMenu(menu);
    }

}
