package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ResourceAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.resource.ResourceResponse;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.result.StCourseResultResponse;
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

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourseFragment extends Fragment {

    private RecyclerView recyclerView;
    ResourceAdapter resourceAdapter;
    GridLayoutManager manager;

    UIHelper uiHelper;
    private List<Resource> resourceList;
    public ResourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resourse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);

        resourceList = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        resourceAdapter = new ResourceAdapter(getActivity());
        recyclerView.setAdapter(resourceAdapter);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        recyclerView.setLayoutManager(linearLayoutManager);

        manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        uiHelper = new UIHelper(getActivity());


        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    callResourceListApi(GlobalOfferedCourseSectionId);

                }
                else {
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                }
            }
        });

//        if(GlobalOfferedCourseSectionId.isEmpty())
//        {
//            int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
//            StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
//            GlobalCourseId = ss.getCourseCode();
//            GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
//        }
        callResourceListApi(GlobalOfferedCourseSectionId);

    }

    private void callResourceListApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getSubjectResource(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId, "0")

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResourceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResourceResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        ResourceResponse resourceResponse = value.body();
                        if (resourceResponse.getStatus().getCode() == 200) {
                            resourceAdapter.clear();
//
                            resourceList = resourceResponse.getData().getCourseMaterials();
//

                            resourceAdapter.addAllData(resourceList);
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
}
