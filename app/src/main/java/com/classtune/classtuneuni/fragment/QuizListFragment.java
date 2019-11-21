package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ExamListAdapter;
import com.classtune.classtuneuni.adapter.QuizListAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.Quiz;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class QuizListFragment extends Fragment implements ExamListAdapter.ItemListener {

    RecyclerView recyclerView;
    private List<Quiz> quizList;
    LinearLayoutManager linearLayoutManager;
    QuizListAdapter quizListAdapter;
    UIHelper uiHelper;
    String subCode = "";
    String courseOfferSectionId = "";
    int pos = 0;


    public QuizListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) Objects.requireNonNull(getActivity())).tabRl.setVisibility(View.VISIBLE);
        uiHelper = new UIHelper(getActivity());

        if (getArguments() !=null && getArguments().getString("id") != null)
            courseOfferSectionId = getArguments().getString("id");

        if(courseOfferSectionId.isEmpty()) {
//            callStAttendance(GlobalOfferedCourseSectionId);
            if(AppSharedPreference.getUserType().equals("3")) {

                callStQuizListApi(GlobalOfferedCourseSectionId);
            }
            else {
               // subCode = ((MainActivity)getActivity()).mTabHost.getCurrentTabTag();
            }
        }
        else {
            for(int i=0; i<((MainActivity)getActivity()).mTabHost.getTabWidget().getTabCount(); i++)
            {
                String s = ((MainActivity)getActivity()).mTabHost.getTabWidget().getChildTabViewAt(i).getTag().toString();
                if(courseOfferSectionId.equals(((MainActivity)getActivity()).mTabHost.getTabWidget().getChildTabViewAt(i).getTag())) {
                    pos = i;
                    break;
                }
            }
            ((MainActivity)getActivity()).mTabHost.setCurrentTab(pos);
            if(AppSharedPreference.getUserType().equals("3")) {
                callStQuizListApi(courseOfferSectionId);
            }
            else {

            }
        }

        recyclerView = view.findViewById(R.id.recyclerView);

        quizList = new ArrayList<>();


        quizListAdapter = new QuizListAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(quizListAdapter);

        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity) Objects.requireNonNull(getActivity())).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    callStQuizListApi(GlobalOfferedCourseSectionId);

                }
                else {
                    subCode = ((MainActivity)getActivity()).mTabHost.getCurrentTabTag();
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();

                }
            }
        });


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

    private void callStQuizListApi(String globalOfferedCourseSectionId) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStQuizList(AppSharedPreference.getApiKey(), globalOfferedCourseSectionId)

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
                        quizListAdapter.clear();
                        if (examResponse!=null && examResponse.getStatus().getCode() == 200) {
//
                            quizList = examResponse.getData().getQuiz();
//
//
//                            List<String> dateList = new ArrayList<>();
//                            for (int r = 0; r < noticeList.size(); r++) {
//                                String sub = noticeList.get(r).getNotice().getCreatedAt().substring(0, 10);
//                                if (!dateList.contains(sub))
//                                    dateList.add(sub);
//                            }

//                            itemList = buildItemList(noticeList, dateList);
                            if(quizList.size()>0)
                            quizListAdapter.addAllData(quizList);
//                            Log.v("tt", noticeList.toString());
                            //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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




    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
