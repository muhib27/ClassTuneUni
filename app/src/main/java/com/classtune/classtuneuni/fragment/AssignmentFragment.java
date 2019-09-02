package com.classtune.classtuneuni.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.course_resonse.CourseListResponse;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.PaginationScrollListener;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;


/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentFragment extends Fragment implements AssignmentAdapter.ItemListener, View.OnClickListener, PaginationAdapterCallback {
    TabLayout tabLayout;
    TabHost mTabHost;
    RecyclerView recyclerView;
    private List<Assignment> assignmentList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    AssignmentAdapter assignmentAdapter;
    FloatingActionButton fabAdd;

    private static final int PAGE_START = 0;
    boolean f = false;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    public AssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());
        String tabText = ((MainActivity)getActivity()).mTabHost.getCurrentTabTag();
      //  AssignmentSection assignmentSectionTab = AppSharedPreference.getUserTab(tabText, 0);

        //Toast.makeText(getActivity(), assignmentSection.getCourseCode() + " " + assignmentSection.getOfferedSectionId() , Toast.LENGTH_LONG).show();

        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerView);

        assignmentList = new ArrayList<>();

//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));
//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));
//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));

        assignmentAdapter = new AssignmentAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


//        if(AppSharedPreference.getUserType().equals("3"))
//        {
//            fabAdd.hide();
//            callStAssignmentListApi(GlobalOfferedCourseSectionId);
//
//        }
//        else {
//
//            callOfferedCoursesApi();
//        }


        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    callStAssignmentListApi(GlobalOfferedCourseSectionId);

                }
                else {
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                    callOfferedCoursesApi();
                }
            }
        });


        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                callStAssignmentListNextApi(GlobalOfferedCourseSectionId);
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

        recyclerView.setAdapter(assignmentAdapter);
        // ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        if(AppSharedPreference.getUserType().equals("3")) {
            fabAdd.hide();
            callStAssignmentListApi(GlobalOfferedCourseSectionId);
        }
        else {
            fabAdd.show();
            callOfferedCoursesApi();

        }
    }

    private void setupTab(final View view, final String tag, String tag1) {
        View tabview = createTabView(mTabHost.getContext(), tag, tag1);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text, final String text1) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        return view;
    }

    @Override
    public void onItemClick(AssignmentModel item, int pos) {
       // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        AssignmentDetailsFragment assignmentDetailsFragment = new AssignmentDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, assignmentDetailsFragment, "assignmentDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callAssignmentListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAssignmentList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TeacherAssignmentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TeacherAssignmentResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        TeacherAssignmentResponse assignmentResponse = value.body();
                        if (assignmentResponse.getStatus().getCode() == 200) {
//
                            assignmentList = assignmentResponse.getData().getAssignments();
//
//
//                            List<String> dateList = new ArrayList<>();
//                            for (int r = 0; r < noticeList.size(); r++) {
//                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
//                                if (!dateList.contains(sub))
//                                    dateList.add(sub);
//                            }

//                            itemList = buildItemList(noticeList, dateList);
                            assignmentAdapter.addAllData(assignmentList);
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

    private void callStAssignmentListApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStAssignmentList(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TeacherAssignmentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TeacherAssignmentResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        TeacherAssignmentResponse assignmentResponse = value.body();
                        if (assignmentResponse != null && assignmentResponse.getStatus().getCode() == 200) {

                            assignmentList = assignmentResponse.getData().getAssignments();

                            assignmentAdapter.addAllData(assignmentList);


                            TOTAL_PAGES = assignmentResponse.getData().getTotalPage();

                            if (currentPage <  (TOTAL_PAGES - 1)) assignmentAdapter.addLoadingFooter();
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

    private void callStAssignmentListNextApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        //uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStAssignmentList(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TeacherAssignmentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TeacherAssignmentResponse> value) {
                       // uiHelper.dismissLoadingDialog();

                        TeacherAssignmentResponse assignmentResponse = value.body();
                        if (assignmentResponse != null && assignmentResponse.getStatus().getCode() == 200) {

                            assignmentAdapter.removeLoadingFooter();
                            isLoading = false;
                            assignmentList = assignmentResponse.getData().getAssignments();

                            assignmentAdapter.addAllData(assignmentList);

//                            TOTAL_PAGES = assignmentResponse.getData().getTotalPage();

                            if (currentPage < (TOTAL_PAGES - 1)) assignmentAdapter.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        //uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void callOfferedCoursesApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getAssignmentCourses(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TeacherAssignmentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<TeacherAssignmentResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        TeacherAssignmentResponse assignmentResponse = value.body();
                        if (assignmentResponse.getStatus().getCode() == 200) {
                            callAssignmentListApi();
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
                Fragment fragment = new CreateAssignmentFragment();
                gotoFragment(fragment, "createAssignmentFragment");
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

    @Override
    public void retryPageLoad() {

    }
}
