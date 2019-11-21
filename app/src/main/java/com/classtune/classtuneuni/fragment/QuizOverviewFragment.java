package com.classtune.classtuneuni.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.QuizGridAdapter;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.model.QuizGridModel;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
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
public class QuizOverviewFragment extends Fragment {
    private List<QuizGridModel> quizGridModelList;
    private List<Question> quizQuestionsList;
    QuizQuestions model;
    private String id = "";
    UIHelper uiHelper;
    private TextView quizDay, quizMY, quizDate, quizNo, marks, subject, timer;
    private RecyclerView recyclerView;
    QuizGridAdapter quizGridAdapter;
    GridLayoutManager manager;
    public QuizOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() !=null && getArguments().getString("id") != null)
            id = getArguments().getString("id");
        uiHelper = new UIHelper(getActivity());
        quizQuestionsList = new ArrayList<>();
        initView(view);
        quizGridModelList = new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        quizGridAdapter = new QuizGridAdapter(getActivity());
        manager = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(quizGridAdapter);

       // quizGridAdapter.addAllData(quizGridModelList);
        if(!id.isEmpty())
        callStQuizResult(id);

    }

    private void initView(View view) {
        quizDay = view.findViewById(R.id.quizDay);
        quizMY = view.findViewById(R.id.quizMY);
        quizDate = view.findViewById(R.id.quizDate);
        quizNo = view.findViewById(R.id.quizNo);
        marks = view.findViewById(R.id.marks);
        subject = view.findViewById(R.id.subject);
        timer = view.findViewById(R.id.timer);
    }




    private void callStQuizResult(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getQuizResults(AppSharedPreference.getApiKey(), id)

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
//                        quizQuestionsList.clear();
                        quizGridAdapter.clear();
                        if (examResponse != null && examResponse.getStatus().getCode() == 200) {
                            populateData(examResponse.getData().getQuizQuestions());
                            quizGridAdapter.addAllData(examResponse.getData().getQuizQuestions().getQuestions());
                            model = ViewModelProviders.of(getActivity()).get(QuizQuestions.class);
                            model.setData(examResponse.getData().getQuizQuestions());
//                            // quizQuestionsList quizQuestions = model.getQuestions()
//
//                            quizQuestionsList = examResponse.getData().getQuizQuestions().getQuestions();
//
//                            if(quizList.size()>0)
//                                quizListAdapter.addAllData(quizList);

                        } else {
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                            quizGridAdapter.clear();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        quizGridAdapter.clear();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }

    private void populateData(QuizQuestions quizQuestions) {
        if(quizQuestions.getQuizTitle()!=null)
        quizNo.setText(quizQuestions.getQuizTitle());
        if(quizQuestions.getCourseName()!=null)
            subject.setText(quizQuestions.getCourseName());
        if(quizQuestions.getObtainedMarks()!=null) {

            Spannable WordtoSpan = new SpannableString(String.valueOf(quizQuestions.getObtainedMarks()) + " Marks Obtained");
            WordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#D71921")), 0, quizQuestions.getObtainedMarks().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //textview.setText(WordtoSpan);

            marks.setText(WordtoSpan);
        }
        if(quizQuestions.getDuration()!=null)
            timer.setText(AppUtility.convertSecondsToHMS(Integer.parseInt(quizQuestions.getDuration()) * 60));
    }
}
