package com.classtune.classtuneuni.fragment;


import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AnsAdapter;
import com.classtune.classtuneuni.adapter.QuizViewAdapter;
import com.classtune.classtuneuni.model.OptionModel;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.classtune.classtuneuni.utils.CountDownTimerPausable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizExplainationFragment extends Fragment implements View.OnClickListener {
    private TextView myTimer;
    private TextView si, questionTv, explainationTv, exp, status;
    private LinearLayout explain;
    private CountDownTimerPausable timer = null;
    QuizViewAdapter ansAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private List<OptionModel> optionModelList;
    private Button next;
    private int pos = -1;

    public static List<Question> quizQuestionsList;
    private QuizQuestions quizQuestions;
    QuizQuestions model;

    public QuizExplainationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_explaination, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() !=null)
            pos = getArguments().getInt("pos");

        initView(view);

        model = ViewModelProviders.of(getActivity()).get(QuizQuestions.class);
        quizQuestions = model.getQuestionMutableLiveData().getValue();
        quizQuestionsList = quizQuestions.getQuestions();
        optionModelList = new ArrayList<>();



        ansAdapter = new QuizViewAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ansAdapter);
        myTimer = view.findViewById(R.id.timer);

        populateData(quizQuestionsList.get(pos), pos);

        ansAdapter.addAllData(quizQuestionsList.get(pos).getOptions());
    }

    private void populateData(Question question, int pos) {
        si.setText(String.valueOf(pos + 1) + ". ");
        if(question.getTitle()!=null)
        questionTv.setText(question.getTitle());
        if(question.getQuestionExplanation()!=null && !question.getQuestionExplanation().trim().isEmpty())
            explainationTv.setText(question.getQuestionExplanation());
        else {
            explain.setVisibility(View.GONE);
            exp.setVisibility(View.GONE);
        }
        if(question.getCorrect()!=null && question.getCorrect()==1)
        {
            status.setText("Correct answer");
        }
        else if(question.getCorrect()!=null && question.getCorrect()==0)
        {
            status.setText("Wrong answer");

        }


    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        si = view.findViewById(R.id.si);
        questionTv = view.findViewById(R.id.question);
        explainationTv = view.findViewById(R.id.explaination);
        explain = view.findViewById(R.id.explain);
        exp = view.findViewById(R.id.exp);
        status = view.findViewById(R.id.status);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.next:
//                Fragment fragment = new QuizOverviewFragment();
//                gotoFragment(fragment, "quizOverviewFragment");
//                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
