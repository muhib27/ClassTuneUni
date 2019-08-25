package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.ClassScheduleAdapter;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.class_schedule.StClsScheduleResponse;
import com.classtune.classtuneuni.model.ClassScheduleModel;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

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
public class ClassScheduleFragment extends Fragment implements ClassScheduleAdapter.ItemListener {

    UIHelper uiHelper;
    RecyclerView recyclerView;
    private List<Routine> scheduleList;
    LinearLayoutManager linearLayoutManager;
    ClassScheduleAdapter classScheduleAdapter;

    public ClassScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);


        scheduleList = new ArrayList<>();


        classScheduleAdapter = new ClassScheduleAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(classScheduleAdapter);

        callStClassSchedule("19");
    }


    @Override
    public void onItemClick(ClassScheduleModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        ExamDetailsFragment examDetailsFragment = new ExamDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, examDetailsFragment, "examDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private void callStClassSchedule(String courseId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStClassSchedule(AppSharedPreference.getApiKey(), courseId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StClsScheduleResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StClsScheduleResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StClsScheduleResponse stClsScheduleResponse = value.body();
                        if (stClsScheduleResponse.getStatus().getCode() == 200) {
                            //populateData(studentAttendanceResponse.getData());
                            classScheduleAdapter.addAllData(stClsScheduleResponse.getData().getRoutine());

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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
