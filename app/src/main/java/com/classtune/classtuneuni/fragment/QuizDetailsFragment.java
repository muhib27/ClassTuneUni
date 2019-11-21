package com.classtune.classtuneuni.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.activity.QuizActivity;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.classtune.classtuneuni.quiz.QuizStartResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.Gson;

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
public class QuizDetailsFragment extends Fragment implements View.OnClickListener {

    private String id = "";
    UIHelper uiHelper;
    private TextView subject, quizName, totalQuestion, totalMarks, timer;
    private Button start, viewDetails;
    private List<Question> quizQuestionsList;
    QuizQuestions model;

    public QuizDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (((MainActivity) getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity) getActivity()).tabRl.setVisibility(View.GONE);

        if (getArguments() != null && getArguments().getString("id") != null)
            id = getArguments().getString("id");
        uiHelper = new UIHelper(getActivity());
        quizQuestionsList = new ArrayList<>();


        initView(view);

//        if (id != null && !id.isEmpty())
//            callStQuestionListApi(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (id != null && !id.isEmpty())
            callStQuestionListApi(id);
    }

    private void initView(View view) {
        subject = view.findViewById(R.id.subject);
        quizName = view.findViewById(R.id.quizName);
        totalQuestion = view.findViewById(R.id.totalQuestion);
        totalMarks = view.findViewById(R.id.totalMarks);
        timer = view.findViewById(R.id.timer);

        start = view.findViewById(R.id.start);
        start.setOnClickListener(this);

        viewDetails = view.findViewById(R.id.viewDetails);
        viewDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                callStQuizStartApi(id);
                break;

            case R.id.viewDetails:
                String str = new Gson().toJson(model.getQuestionMutableLiveData().getValue());
                gotoQuizActivity(str, "view");
                break;
        }
    }

    private void callStQuestionListApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getQuizQuestionList(AppSharedPreference.getApiKey(), id)

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
                        quizQuestionsList.clear();
                        if (examResponse.getStatus().getCode() == 200) {
                            populateData(examResponse.getData().getQuizQuestions());
                            model = ViewModelProviders.of(getActivity()).get(QuizQuestions.class);
                            model.setData(examResponse.getData().getQuizQuestions());
                            // quizQuestionsList quizQuestions = model.getQuestions()

                            quizQuestionsList = examResponse.getData().getQuizQuestions().getQuestions();
//
//                            if(quizList.size()>0)
//                                quizListAdapter.addAllData(quizList);

                        } else {
                            //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        //Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });


    }


    private void callStQuizStartApi(String id) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getQuizStart(AppSharedPreference.getApiKey(), id)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<QuizStartResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<QuizStartResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        QuizStartResponse quizStartResponse = value.body();

                        if (quizStartResponse != null && quizStartResponse.getStatus().getCode() == 200) {
//                            populateData(examResponse.getData().getQuizQuestions());
//                            QuizQuestions model = ViewModelProviders.of(getActivity()).get(QuizQuestions.class);
//                            model.setData(examResponse.getData().getQuizQuestions());
//                            // quizQuestionsList quizQuestions = model.getQuestions()
//
//                            quizQuestionsList = examResponse.getData().getQuizQuestions().getQuestions();
//
//                            if(quizList.size()>0)
//                                quizListAdapter.addAllData(quizList);
//                            Fragment fragment = new QuizFragment();
//                            gotoFragment(fragment, "quizFragment");
                            String str = new Gson().toJson(model.getQuestionMutableLiveData().getValue());
                            gotoQuizActivity(str, quizStartResponse.getQuizStart().getStartTime());
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

    private void populateData(QuizQuestions quizQuestions) {
        if (quizQuestions.getCourseName() != null)
            subject.setText(quizQuestions.getCourseName());
        if (quizQuestions.getQuizTitle() != null)
            quizName.setText(quizQuestions.getQuizTitle());
        if (quizQuestions.getQuestions().size() > 0)
            totalQuestion.setText(String.valueOf(quizQuestions.getQuestions().size()));
        if (quizQuestions.getTotalMarks() != null)
            totalMarks.setText(String.valueOf(quizQuestions.getTotalMarks()));
        if (quizQuestions.getDuration() != null) {
            timer.setText(AppUtility.convertSecondsToHMS(Integer.parseInt(quizQuestions.getDuration()) * 60));
        }

        if(quizQuestions.getAttendStatus()!=null && quizQuestions.getAttendStatus().equals("yes"))
        {
            if(quizQuestions.getParticipatedBefore()!=null && quizQuestions.getParticipatedBefore().equals("yes"))
                start.setText("Retake");
            else
                start.setText("Start");
            start.setVisibility(View.VISIBLE);

        }
        else {
            start.setVisibility(View.INVISIBLE);
        }


        if(quizQuestions.getParticipatedBefore()!=null && quizQuestions.getParticipatedBefore().equals("yes"))
        {
            viewDetails.setVisibility(View.VISIBLE);
            if(quizQuestions.getObtainedMarks()!=null && quizQuestions.getTotalMarks()!=null) {
                String text = "You obtained " + quizQuestions.getObtainedMarks() + " out of " + quizQuestions.getTotalMarks();
                totalMarks.setText(text);
            }
        }
        else
            viewDetails.setVisibility(View.GONE);

    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void gotoQuizActivity(String str, String startTime) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        if (startTime.equals("view")) {
            intent.putExtra("type", "view");
            intent.putExtra("time", "");
        }
        else {
            intent.putExtra("type", "quiz");
            intent.putExtra("time", startTime);
        }

        intent.putExtra("data", str);

        getActivity().startActivity(intent);
    }
}
