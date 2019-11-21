package com.classtune.classtuneuni.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.fragment.MorePageFragment;
import com.classtune.classtuneuni.fragment.QuizExplainationFragment;
import com.classtune.classtuneuni.fragment.QuizFragment;
import com.classtune.classtuneuni.fragment.QuizOverviewFragment;
import com.classtune.classtuneuni.fragment.QuizScoreFragment;
import com.classtune.classtuneuni.quiz.Question;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    String quizData = "";
    QuizQuestions model;
    QuizQuestions quizQuestions;
    String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.toolbar_icon);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String type = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("time") != null) {
            time = getIntent().getStringExtra("time");
        }
        if (getIntent().getStringExtra("data") != null) {
            quizData = getIntent().getStringExtra("data");
            quizQuestions = parseData(quizData);
        }

        model = ViewModelProviders.of(this).get(QuizQuestions.class);
        model.setData(quizQuestions);
        if (type.equals("quiz")) {
            Fragment fragment = new QuizFragment();
            Bundle bundle = new Bundle();
            bundle.putString("data", quizData);
            bundle.putString("time", time);
            loadFragment(fragment, "quizFragment", bundle);
        } else if(type.equals("view")) {
            Fragment fragment = new QuizOverviewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", quizQuestions.getQuizId());
            loadFragment(fragment, "quizOverviewFragment", bundle);
        }
    }

    @Override
    public void onBackPressed() {
        QuizExplainationFragment quizExplainationFragment = (QuizExplainationFragment) getSupportFragmentManager().findFragmentByTag("quizExplainationFragment");
        if (quizExplainationFragment != null && quizExplainationFragment.isVisible())
            getSupportFragmentManager().popBackStack();
        else
            finish();
        //super.onBackPressed();
    }

    private void loadFragment(Fragment fragment, String tag, Bundle bundle) {
        // load fragment
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quizContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    private QuizQuestions parseData(String object) {

        QuizQuestions tags = new QuizQuestions();
        Type listType = new TypeToken<QuizQuestions>() {
        }.getType();
        tags = new Gson().fromJson(object, listType);
        return tags;
    }
}
