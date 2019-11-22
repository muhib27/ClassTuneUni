package com.classtune.classtuneuni.fragment;


import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ItemAdapter;
import com.classtune.classtuneuni.adapter.MultiSelectionSpinner;
import com.classtune.classtuneuni.adapter.NoticeAdapterNew;
import com.classtune.classtuneuni.model.Item;
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

import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeListFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {

    UIHelper uiHelper;
    ItemAdapter itemAdapter;
    FloatingActionButton fabAdd;
    List<Section> noticeOfferResponseList;
    List<Notice> noticeList;
    LinearLayoutManager layoutManager;

    List<NoticeInfo> noticeInfoList;

    NoticeAdapterNew noticeAdapterNew;

    private static final int PAGE_START = 0;
    boolean f = false;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES;
    private int currentPage = PAGE_START;


    List<Item> itemList;

    public NoticeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notice_list, container, false);
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
        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);


        RecyclerView rvItem = view.findViewById(R.id.rv_item);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        itemAdapter = new ItemAdapter(getActivity());
        noticeAdapterNew = new NoticeAdapterNew(getActivity(),this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(layoutManager);
        rvItem.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        rvItem.setItemAnimator(new DefaultItemAnimator());
        rvItem.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                callStudentNoticeListNextApi();
               // callStAssignmentListNextApi(GlobalOfferedCourseSectionId);
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
        rvItem.setAdapter(noticeAdapterNew);

        if (AppSharedPreference.getUserType().equals("3")) {
            fabAdd.hide();
            callStudentNoticeListApi();
        } else {
            fabAdd.show();
            //callNoticeListApi();
        }
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


//    private void callNoticeListApi() {
//
//
//        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
//            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        uiHelper.showLoadingDialog("Please wait...");
//
//        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
//        RetrofitApiClient.getApiInterfaceWithId().getTeacherNitceList(AppSharedPreference.getApiKey())
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
//                        if (noticeResonse.getStatus().getCode() == 200) {
//
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
////                            String ss = jsonObject
////                            Log.v("noticeResponseModel", value.message());
////                            List<Notice> noticeList = noticeResonse.getData().getNotice();
////                            Collections.reverse(noticeList);
//                            itemAdapter.addAllData(itemList);
////                            Log.v("tt", noticeList.toString());
//                           // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
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

    private void callStudentNoticeListApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStudentNitceList(AppSharedPreference.getApiKey(), 0)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NoticeResonse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NoticeResonse> value) {
                        uiHelper.dismissLoadingDialog();

                        NoticeResonse noticeResonse = value.body();
                        noticeAdapterNew.clear();
                        if (noticeResonse.getStatus().getCode() == 200) {

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
                            noticeAdapterNew.addAllData(noticeResonse.getData().getNotices(), noticeResonse.getData().getCurrentTime());
                            TOTAL_PAGES = noticeResonse.getData().getTotalPages();

                            if (currentPage <  (TOTAL_PAGES - 1)) noticeAdapterNew.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            noticeAdapterNew.clear();
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        noticeAdapterNew.clear();
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

    private void callStudentNoticeListNextApi() {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
       // uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStudentNitceList(AppSharedPreference.getApiKey(), currentPage)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NoticeResonse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NoticeResonse> value) {
                        //uiHelper.dismissLoadingDialog();

                        NoticeResonse noticeResonse = value.body();

                        if (noticeResonse.getStatus().getCode() == 200) {

                            noticeAdapterNew.removeLoadingFooter();
                            isLoading = false;
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
                            noticeAdapterNew.addAllData(noticeResonse.getData().getNotices(),noticeResonse.getData().getCurrentTime() );
                            if (currentPage < (TOTAL_PAGES - 1)) noticeAdapterNew.addLoadingFooter();
                            else isLastPage = true;
//                            Log.v("tt", noticeList.toString());
                            // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {

                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        //uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        //uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private List<Item> buildItemList(List<Notice> noticeList, List<String> dateList) {
        List<Notice> subItemList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            Item item = new Item(dateList.get(i), buildSubItemList(dateList.get(i), noticeList));
            itemList.add(item);
//            for(int j =0; j<=noticeList.size(); j++){
//                if(noticeList.get(j).getCreatedAt().contains(jsonArray.get(i).getAsString()))
//                {
//                    Item item = new Item(noticeList.get(i).getCreatedAt(), buildSubItemList(jsonArray.get(i).getAsString(), noticeList));
//                    itemList.add(item);
//                }
//                   // subItemList.add(noticeList.get(j));
//            }


        }
        return itemList;
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
//                            Log.v("noticeResponseModel", value.message());
//                            List<Notice> noticeList = noticeResonse.getData().getNotice();
//                            Collections.reverse(noticeList);
//                            itemAdapter.addAllData(noticeList);
//                            Log.v("tt", noticeList.toString());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add:
                callNoticeSection();
                break;
        }
    }

    MultiSelectionSpinner spinner;
    EditText title;
    EditText description;
    View view1;
    LinearLayout ll;

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
            checkBox.setText(noticeOfferResponseList.get(i).getName());
            checkBox.setId(Integer.parseInt(noticeOfferResponseList.get(i).getId()));
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
                    if (!titleSt.isEmpty() && !noticeId.isEmpty())
                        callAddNoticeApi(titleSt, descriptionSt, noticeId);
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

    CheckBox checkBox;
    ViewGroup main;

//    private void addSection(List<Section> noticeOfferResponseList, Dialog dialog){
//        LayoutInflater inflater = getLayoutInflater();
//        View view1 = inflater.inflate(R.layout.checkbox_section_view, null);
//
//        for(int i = 0; i<noticeOfferResponseList.size(); i++) {
//            checkBox = view1.findViewById(R.id.i_am_id);
//            checkBox.setText(noticeOfferResponseList.get(i).getName());
//            checkBox.setId(Integer.parseInt(noticeOfferResponseList.get(i).getId()));
//            main.addView(checkBox);
//        }
//
//
//       // count++;
//
//        //  Toast.makeText(getActivity(), ""+ main.getChildCount(), Toast.LENGTH_LONG).show();
//    }

    private void callAddNoticeApi(String title, String description, String noticeId) {
        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().addNotice(title, description, noticeId, AppSharedPreference.getApiKey())

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

    String noticeId = "";

    @Override
    public void retryPageLoad() {

    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            noticeId = noticeOfferResponseList.get(pos).getId();

//            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
//                // ToDo when first item is selected
//            } else {
//                Toast.makeText(parent.getContext(),
//                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
//                        Toast.LENGTH_LONG).show();
//            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }
}
