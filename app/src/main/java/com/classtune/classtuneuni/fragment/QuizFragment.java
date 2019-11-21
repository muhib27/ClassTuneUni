package com.classtune.classtuneuni.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.AnsAdapter;
import com.classtune.classtuneuni.exam.ExamResponse;
import com.classtune.classtuneuni.model.OptionModel;
import com.classtune.classtuneuni.quiz.Option;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.classtune.classtuneuni.quiz.QuizSubmitResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.CountDownTimerPausable;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {
    private TextView myTimer;
    private TextView si, question, marks, questionNo, total;
    private CountDownTimerPausable timer = null;
    AnsAdapter ansAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private List<OptionModel> optionModelList;
    private Button next, previous;
    public static List<Question> quizQuestionsList;
    private QuizQuestions quizQuestions;
    Fragment fragment;
    int questionCount = 0;
    UIHelper uiHelper;
    QuizQuestions model;
    ProgressBar progressBar;
    private int progressStatus = 0;
    private long millisInFuture = 20000;
    private long countDownInterval = 1000;
    private String time = "";


    CountDownTimer mCountDownTimer;
    int progress = 0;

    boolean submitted = false;
    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
//            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        uiHelper = new UIHelper(getActivity());
        initView(view);
        optionModelList = new ArrayList<>();
        quizQuestionsList = new ArrayList<>();
        questionCount = 0;
        if (getArguments() != null && getArguments().containsKey("time")) {
            time = getArguments().getString("time");
//            if (str != null)
//                quizQuestionsList = parseNewsList(str);

        }

       // getList();


        model = ViewModelProviders.of(getActivity()).get(QuizQuestions.class);
        quizQuestions = model.getQuestionMutableLiveData().getValue();
        quizQuestionsList = quizQuestions.getQuestions();


        ansAdapter = new AnsAdapter(getActivity(), QuizFragment.this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ansAdapter);
//        int progressBarMaximumValue = (int)(millisInFuture/countDownInterval);
//        progressBar.setMax(progressBarMaximumValue);




        progressBar.setProgress(progress);
//        mCountDownTimer=new CountDownTimer(Integer.parseInt(quizQuestions.getDuration()) * 60 * 1000,1000) {
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.v("Log_tag", "Tick of Progress"+ progress+ millisUntilFinished);
//                progress++;
//                progressBar.setProgress((int)progress*100/(Integer.parseInt(quizQuestions.getDuration()) * 60 * 1000/1000));
//                myTimer.setText(convertSecondsToHMS(String.valueOf(millisUntilFinished / 1000)));
//            }
//
//            @Override
//            public void onFinish() {
//                //Do what you want
//                progress++;
//                progressBar.setProgress(100);
//                if(!submitted)
//                    resultProcess();
//            }
//        };
//        mCountDownTimer.start();






        initTimer(Integer.parseInt(quizQuestions.getDuration()) * 60 * 1000);
       // progressBar.setProgress(0);

        setQuestion(questionCount);
        total.setText("/" + String.valueOf(quizQuestionsList.size()));

        quizQuestions.getQuestionMutableLiveData().observe(getActivity(), new Observer<QuizQuestions>() {
            @Override
            public void onChanged(@Nullable QuizQuestions quizQuestions) {
                initTimer(Integer.parseInt(quizQuestions.getDuration()) * 60 * 1000);
            }
        });


//        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
//        int numberOfSeconds = (Integer.parseInt(quizQuestions.getDuration()) * 60*1000)/60; // Ex : 20000/1000 = 20
//        int factor = 100/numberOfSeconds;
//        animation.setDuration((60 * 1000));
////        animation.setDuration(Integer.parseInt(quizQuestions.getDuration()) * 60 * 1000);
//
//        //animation.setDuration(factor);
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) { }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                //do something when the countdown is complete
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) { }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) { }
//        });
//        animation.start();

    }

    private void setQuestion(int questionCount) {
        ansAdapter.clear();
        if(quizQuestionsList.get(questionCount).getMarkCount()!=null)
        marks.setText(quizQuestionsList.get(questionCount).getMarkCount());
        questionNo.setText(String.valueOf(questionCount + 1));
        si.setText(String.valueOf(questionCount + 1) + ". ");
        question.setText(quizQuestionsList.get(questionCount).getTitle());

        ansAdapter.addAllData(quizQuestionsList.get(questionCount).getOptions(), questionCount, quizQuestionsList.get(questionCount).getType());
        if (questionCount == (quizQuestionsList.size() - 1)) {
            next.setText("Submit");
        } else if (questionCount > (quizQuestionsList.size() - 1))
            next.setVisibility(View.INVISIBLE);
        else {
            next.setText("Next");
            next.setVisibility(View.VISIBLE);
        }


        if (questionCount > 0)
            previous.setVisibility(View.VISIBLE);
        else
            previous.setVisibility(View.INVISIBLE);
    }

    private void initView(View view) {
        myTimer = view.findViewById(R.id.timer);
        marks = view.findViewById(R.id.marks);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);

        previous = view.findViewById(R.id.previous);
        previous.setOnClickListener(this);
        previous.setVisibility(View.INVISIBLE);

        recyclerView = view.findViewById(R.id.recyclerView);
        si = view.findViewById(R.id.si);
        question = view.findViewById(R.id.question);
        questionNo = view.findViewById(R.id.questionNo);
        total = view.findViewById(R.id.total);

        progressBar = view.findViewById(R.id.progressBar);
    }


    private void getList() {
        OptionModel optionModel = new OptionModel("A", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("B", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("C", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("D", "option a", "0");
        optionModelList.add(optionModel);
    }

    private void initTimer(final int time) {

        mCountDownTimer=new CountDownTimer(time,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ progress+ millisUntilFinished);
                progress++;
                progressBar.setProgress((int)progress*100/(time/1000));
                myTimer.setText(convertSecondsToHMS(String.valueOf(millisUntilFinished / 1000)));
            }

            @Override
            public void onFinish() {
                //Do what you want
                progress++;
                progressBar.setProgress(100);
                if(!submitted)
                    resultProcess();
            }
        };
        mCountDownTimer.start();

//        if (timer == null) {
//            timer = new CountDownTimerPausable(time, 1) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//
//                    myTimer.setText(convertSecondsToHMS(String.valueOf(millisUntilFinished / 1000)));
////                    progress = (int)((time - millisUntilFinished) / (time/1000)) * 100;
////                    progressBar.setProgress(progress);
//
//                }
//
//                @Override
//                public void onFinish() {
//                    //showDoalogTimeUp();
//                    //timer = null;
//                    // refreshSnippetForTimeOut();
//                    if(!submitted)
//                    resultProcess();
//
//                }
//            };
//
//            this.timer.start();
//        }

    }

    private String convertSecondsToHMS(String seconds) {
        String result = "";

        int second = Integer.parseInt(seconds);

        int hr = (int) (second / 3600);
        int rem = (int) (second % 3600);
        int mn = rem / 60;
        int sec = rem % 60;
        //String hrStr = (hr<10 ? "0" : "")+hr;
        String mnStr = (mn < 10 ? "0" : "") + mn;
        String secStr = (sec < 10 ? "0" : "") + sec;

        //result = hrStr+":"+mnStr+":"+secStr;
        result = mnStr + ":" + secStr;

        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next:

                String st = next.getText().toString();
                if(st.equals("Next")) {
                    questionCount++;
                    if (questionCount < quizQuestionsList.size())
                        setQuestion(questionCount);
                }
                else {
                    previous.setVisibility(View.INVISIBLE);
                    resultProcess();
//                    fragment = new QuizOverviewFragment();
//                    gotoFragment(fragment, "quizOverviewFragment");
                }
                break;
            case R.id.previous:
                questionCount--;
                if (questionCount >= 0)
                    setQuestion(questionCount);
//                fragment = new QuizOverviewFragment();
//                gotoFragment(fragment, "quizOverviewFragment");
                break;
        }
    }

    private void resultProcess() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< quizQuestionsList.size(); i++){
            String resultId = "";
            int count = 0;
            String questionId = quizQuestionsList.get(i).getId() + "-" ;
            for(int  j=0; j<quizQuestionsList.get(i).getOptions().size(); j++)
            {
                if(quizQuestionsList.get(i).getOptions().get(j).getStatus()!= null && quizQuestionsList.get(i).getOptions().get(j).getStatus().equals("1")) {
                    count++;
                    if(count>1)
                    resultId = resultId + "|";
                    resultId = resultId + quizQuestionsList.get(i).getOptions().get(j).getId();


                }
            }
            //result.add(questionId + resultId);
            sb.append(questionId + resultId);
            if(i < (quizQuestionsList.size()-1))
            sb.append(",");

        }

        if(!submitted) {
            submitted = true;
            callStQuizSubmit(quizQuestions.getQuizId(), sb.toString());
        }
    }

    private void gotoFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        try {
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.quizContainer, fragment, tag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        catch (Exception e){

        }

    }

    public void quizListUpdate(String status, int question, int position, String questionType) {

        if(questionType.equals("2"))
        {
            if(position == 0)
            {
                if(status.equals("0")) {
                    quizQuestionsList.get(question).getOptions().get(0).setStatus("0");
                    quizQuestionsList.get(question).getOptions().get(1).setStatus("1");
                }
                else{
                    quizQuestionsList.get(question).getOptions().get(0).setStatus("1");
                    quizQuestionsList.get(question).getOptions().get(1).setStatus("0");
                }
            }
            else {
                if(status.equals("0")) {
                    quizQuestionsList.get(question).getOptions().get(0).setStatus("1");
                    quizQuestionsList.get(question).getOptions().get(1).setStatus("0");

                }
                else{
                    quizQuestionsList.get(question).getOptions().get(0).setStatus("0");
                    quizQuestionsList.get(question).getOptions().get(1).setStatus("1");
                }
            }

            ansAdapter.clear();
            ansAdapter.addAllData(quizQuestionsList.get(question).getOptions(), questionCount, questionType);
        }
        else {
            quizQuestionsList.get(question).getOptions().get(position).setStatus(status);
        }
    }


//    private void callStQuizSubmit(String quizId, ArrayList<String> result) {
        private void callStQuizSubmit(final String quizId, String result) {


        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getQuizSubmit(AppSharedPreference.getApiKey(), quizId, result, time)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<Response<QuizSubmitResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<QuizSubmitResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        QuizSubmitResponse quizSubmitResponse = value.body();
//                        quizQuestionsList.clear();
                        if (quizSubmitResponse.getStatus().getCode() == 200) {

                            Bundle bundle = new Bundle();
                            bundle.putInt("marks", quizSubmitResponse.getQuizData().getData().getMarks());
                            bundle.putString("quizName", quizSubmitResponse.getQuizData().getData().getQuizTitle());
                            bundle.putString("subject", quizSubmitResponse.getQuizData().getData().getCourseName());
                            bundle.putString("id", quizId);
                            Fragment fragment = new QuizScoreFragment();
                            gotoFragment(fragment, "quizScoreFragment", bundle);

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


    private List<Question> parseNewsList(String object) {

        List<Question> tags = new ArrayList<Question>();
        Type listType = new TypeToken<List<Question>>() {
        }.getType();
        tags = new Gson().fromJson(object, listType);
        return tags;
    }

}
