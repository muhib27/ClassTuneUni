package com.classtune.classtuneuni.fragment;


import android.content.Context;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ExamListAdapter;
import com.classtune.classtuneuni.adapter.SubjectResultAdapter;
import com.classtune.classtuneuni.assignment.TeacherAssignmentResponse;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.SubjectResultModel;
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

import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamListFragment extends Fragment implements ExamListAdapter.ItemListener {

    TabHost mTabHost;
    RecyclerView recyclerView;
    private List<ExamInfoModel> examList;
    LinearLayoutManager linearLayoutManager;
    ExamListAdapter examListAdapter;
    UIHelper uiHelper;
    public ExamListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);
        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);

        examList = new ArrayList<>();


        examListAdapter = new ExamListAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(examListAdapter);

        if(AppSharedPreference.getUserType().equals("3")) {
            callStExamListApi(GlobalOfferedCourseSectionId);
        }
        else {

        }
    }


    @Override
    public void onItemClick(ExamInfoModel item, int pos) {
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

    private void callStExamListApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStExamList(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ExamResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ExamResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        ExamResponse examResponse = value.body();
                        if (examResponse.getStatus().getCode() == 200) {
//
                            examList = examResponse.getData().getExams();
//
//
//                            List<String> dateList = new ArrayList<>();
//                            for (int r = 0; r < noticeList.size(); r++) {
//                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
//                                if (!dateList.contains(sub))
//                                    dateList.add(sub);
//                            }

//                            itemList = buildItemList(noticeList, dateList);
                            examListAdapter.addAllData(examList);
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
