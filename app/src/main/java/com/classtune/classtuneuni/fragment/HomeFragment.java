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

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.StHomeAdapter;
import com.classtune.classtuneuni.home.StHomeAttendance;
import com.classtune.classtuneuni.home.StHomeFeed;
import com.classtune.classtuneuni.home.StHomeHeaderData;
import com.classtune.classtuneuni.home.StHomeHeaderRespons;
import com.classtune.classtuneuni.home.StHomeRespons;
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


public class HomeFragment extends Fragment implements PaginationAdapterCallback {
    RecyclerView recyclerView;
    private List<StHomeFeed> stHomeFeedList;
    private List<StHomeAttendance> stHomeAttendanceList;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 0;
    boolean f = false;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;

    StHomeAdapter stHomeAdapter;

    private TextView nextClassTime;
    UIHelper uiHelper;
//((TextView)findViewById(R.id.text)).setText(Html.fromHtml("X<sup>2</sup>"));
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());

        stHomeFeedList = new ArrayList<>();
        stHomeAttendanceList = new ArrayList<>();

        nextClassTime = view.findViewById(R.id.nextClassTime);
        //nextClassTime.setText("10:30" + Html.fromHtml("<sub>am</sub>"));

        recyclerView = view.findViewById(R.id.recyclerView);


        stHomeAdapter = new StHomeAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


//        recyclerView.setFocusable(true);







        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

//                boolean a = isLastItemDisplaying(recyclerView);
//                if(firstVisibleItemPosition>0)
                        callStudentHomeNextApi();
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

        recyclerView.setAdapter(stHomeAdapter);
       // ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        if(AppSharedPreference.getUserType().equals("3")) {
           // callStudentHomeApi();
           callStudentHomeHeaderApi();
        }
        else {

        }

    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }

    StHomeFeed stHomeFeed;
    private void callStudentHomeApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStHome(AppSharedPreference.getApiKey(), currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StHomeRespons>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StHomeRespons> value) {
                        uiHelper.dismissLoadingDialog();

                        StHomeRespons stHomeRespons = value.body();
                        if ( stHomeRespons!= null && stHomeRespons.getStatus().getCode() == 200) {


                            stHomeFeedList = stHomeRespons.getData().getNewsFeed();
                            //stHomeFeedList = getList();
                            stHomeAdapter.addAllData(stHomeFeedList);
                            TOTAL_PAGES = stHomeRespons.getData().getTotal_page();

                            if (currentPage < TOTAL_PAGES) stHomeAdapter.addLoadingFooter();
                            else isLastPage = true;
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

    private void callStudentHomeHeaderApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStHomeHeader(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StHomeHeaderRespons>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StHomeHeaderRespons> value) {
                        // uiHelper.dismissLoadingDialog();

                        StHomeHeaderRespons stHomeHeaderRespons = value.body();
                        if (stHomeHeaderRespons != null && stHomeHeaderRespons.getStatus().getCode() == 200) {

                            StHomeFeed stHomeFeed = new StHomeFeed(100);
                            stHomeFeedList.add(stHomeFeed);
                            stHomeAdapter.addAllData(stHomeFeedList);

                            stHomeAttendanceList = stHomeHeaderRespons.getData().getAttendance();

                            //stHomeAdapter.addAllData(stHomeFeedList);

                           // populateData(stHomeHeaderRespons.getData());

                            callStudentHomeApi();
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

    private void populateData(StHomeHeaderData data) {
        if(data.getAttendance().get(0).getCourseCode()!=null)
        stHomeAdapter.attendanceSubCode.setText(data.getAttendance().get(0).getCourseCode());
        if(data.getAttendance().get(0).getPresent()!=null)
            stHomeAdapter.attendancePresent.setText(data.getAttendance().get(0).getPresent() + "/" + data.getAttendance().get(0).getTotalClass());
        if(data.getAttendance().get(0).getPercentage()!=null)
            stHomeAdapter.attendanceParcent.setText(data.getAttendance().get(0).getPercentage() + "%");
        if(data.getNextClass().getCourseCode()!=null)
        stHomeAdapter.nextSubCode.setText(data.getNextClass().getCourseCode());
        if(data.getNextClass().getInstructor()!=null)
            stHomeAdapter.nextTeacher.setText(data.getNextClass().getInstructor());
        if(data.getNextClass().getStartTime()!=null)
            stHomeAdapter.nextTime.setText(data.getNextClass().getStartTime());
        if(data.getDueSubmission().getCourseName()!=null)
        stHomeAdapter.dueSubCode.setText(data.getDueSubmission().getCourseCode());
        if(data.getDueSubmission().getCourseCode()!=null)
            stHomeAdapter.dueAubject.setText(data.getDueSubmission().getCourseName());
        if(data.getDueSubmission().getCourseCode()!=null)
            stHomeAdapter.dueDate.setText(data.getDueSubmission().getDueDate());
    }

    private void callStudentHomeNextApi() {

//        stHomeAdapter.headerSubCode.setText("test");

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
      //  uiHelper.showLoadingDialog("Please wait...");

        RetrofitApiClient.getApiInterfaceWithId().getStHome(AppSharedPreference.getApiKey(), currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StHomeRespons>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StHomeRespons> value) {
                       // uiHelper.dismissLoadingDialog();

                        StHomeRespons stHomeRespons = value.body();
                        if (stHomeRespons!= null && stHomeRespons.getStatus().getCode() == 200) {
                            stHomeAdapter.removeLoadingFooter();
                            isLoading = false;
                            stHomeFeedList = stHomeRespons.getData().getNewsFeed();
                            stHomeAdapter.addAllData(stHomeFeedList);
                            //TOTAL_PAGES = stHomeRespons.getData().getTotal_page();

                            if (currentPage < TOTAL_PAGES) stHomeAdapter.addLoadingFooter();
                            else isLastPage = true;
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
                        //uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private List<StHomeFeed> getList(){
        List<StHomeFeed> list = new ArrayList<>();
        StHomeFeed stHomeFeed = new StHomeFeed(1);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(2);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(3);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(5);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(7);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(8);
        list.add(stHomeFeed);
        stHomeFeed = new StHomeFeed(1);
        list.add(stHomeFeed);



        return list;
    }


    @Override
    public void retryPageLoad() {
        callStudentHomeNextApi();
    }
}
