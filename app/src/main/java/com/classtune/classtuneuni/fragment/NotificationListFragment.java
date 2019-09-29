package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ItemAdapter;
import com.classtune.classtuneuni.adapter.MultiSelectionSpinner;
import com.classtune.classtuneuni.adapter.NoticeAdapterNew;
import com.classtune.classtuneuni.adapter.NotificationAdapter;
import com.classtune.classtuneuni.model.Item;
import com.classtune.classtuneuni.notification.NotificationResponse;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;
import com.classtune.classtuneuni.response.NoticeOfferResponse;
import com.classtune.classtuneuni.response.NoticeResonse;
import com.classtune.classtuneuni.response.Section;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.PaginationScrollListener;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
import com.google.gson.JsonElement;

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
public class NotificationListFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {

    UIHelper uiHelper;
    List<Notice> noticeList;
    LinearLayoutManager layoutManager;

    List<NoticeInfo> noticeInfoList;

    NotificationAdapter notificationAdapter;

    private static final int PAGE_START = 0;
    boolean f = false;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    List<Item> itemList;

    public NotificationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);

        noticeList = new ArrayList<>();
        noticeInfoList = new ArrayList<>();
        itemList = new ArrayList<>();



        RecyclerView rvItem = view.findViewById(R.id.rv_item);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        itemAdapter = new ItemAdapter(getActivity());
        notificationAdapter = new NotificationAdapter(getActivity(),this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        rvItem.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rvItem.setItemAnimator(new DefaultItemAnimator());
//        rvItem.addOnScrollListener(new PaginationScrollListener(layoutManager) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                callStudentNoticeListNextApi();
//               // callStAssignmentListNextApi(GlobalOfferedCourseSectionId);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
        rvItem.setAdapter(notificationAdapter);

        callNotificationListApi();
    }

//    private List<Item> buildItemList() {
//        List<Item> itemList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Item item = new Item("Item " + i, buildSubItemList());
//            itemList.add(item);
//        }
//        return itemList;
//    }

    private List<Notice> buildSubItemList(String asString, List<Notice> noticeList) {
        List<Notice> subItemList = new ArrayList<>();
        for (int i = 0; i < noticeList.size(); i++) {
            if (noticeList.get(i).getNotice().getCreatedAt().contains(asString)) {
                subItemList.add(noticeList.get(i));
            }
//            Notice subItem = new Notice();
//            subItemList.add(subItem);
        }
        return subItemList;
    }



    private void callNotificationListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getNotificationList(AppSharedPreference.getApiKey())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NotificationResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NotificationResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        NotificationResponse notificationResponse = value.body();
                        notificationAdapter.clear();
                        if (notificationResponse.getStatus().getCode() == 200) {

//                            noticeList = noticeResonse.getData().getNotices();
//
//
//                            List<String> dateList = new ArrayList<>();
//                            for (int r = 0; r < noticeList.size(); r++) {
//                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
//                                if (!dateList.contains(sub))
//                                    dateList.add(sub);
//                            }
//
//                            itemList = buildItemList(noticeList, dateList);
//                            String ss = jsonObject
//                            Log.v("noticeResponseModel", value.message());
//                            List<Notice> noticeList = noticeResonse.getData().getNotice();
//                            Collections.reverse(noticeList);
                            notificationAdapter.addAllData(notificationResponse.getData().getNotifications());
//                            TOTAL_PAGES = noticeResonse.getData().getTotalPages();
//
//                            if (currentPage <  (TOTAL_PAGES - 1)) notificationAdapter.addLoadingFooter();
//                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            notificationAdapter.clear();
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        notificationAdapter.clear();
                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

//    private void callStudentNoticeListNextApi() {
//
//
//        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
//            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        uiHelper.showLoadingDialog("Please wait...");
//
//        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
//        RetrofitApiClient.getApiInterfaceWithId().getStudentNitceList(AppSharedPreference.getApiKey(), currentPage)
//
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<NoticeResonse>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response<NoticeResonse> value) {
//                        uiHelper.dismissLoadingDialog();
//
//                        NoticeResonse noticeResonse = value.body();
//                        notificationAdapter.clear();
//                        if (noticeResonse.getStatus().getCode() == 200) {
//
//                            notificationAdapter.removeLoadingFooter();
//                            isLoading = false;
////                            noticeList = noticeResonse.getData().getNotices();
////
////
////                            List<String> dateList = new ArrayList<>();
////                            for (int r = 0; r < noticeList.size(); r++) {
////                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
////                                if (!dateList.contains(sub))
////                                    dateList.add(sub);
////                            }
////
////                            itemList = buildItemList(noticeList, dateList);
////                            String ss = jsonObject
////                            Log.v("noticeResponseModel", value.message());
////                            List<Notice> noticeList = noticeResonse.getData().getNotice();
////                            Collections.reverse(noticeList);
//                            notificationAdapter.addAllData(noticeResonse.getData().getNotices(),noticeResonse.getData().getCurrentTime() );
//                            if (currentPage < (TOTAL_PAGES - 1)) notificationAdapter.addLoadingFooter();
//                            else isLastPage = true;
////                            Log.v("tt", noticeList.toString());
//                            // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                        } else {
//                            notificationAdapter.clear();
//                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        notificationAdapter.clear();
//                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
//                        uiHelper.dismissLoadingDialog();
//                    }
//
//                    @Override
//                    public void onComplete() {
////                        progressDialog.dismiss();
//                        uiHelper.dismissLoadingDialog();
//                    }
//                });
//
//
//    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }





    String noticeId = "";

    @Override
    public void retryPageLoad() {

    }

}
