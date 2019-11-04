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
import android.widget.Button;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AnsAdapter;
import com.classtune.classtuneuni.adapter.TakeAttendanceAdapter;
import com.classtune.classtuneuni.model.OptionModel;
import com.classtune.classtuneuni.model.Student;
import com.classtune.classtuneuni.utils.CountDownTimerPausable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment implements View.OnClickListener {
    private TextView myTimer;
    private CountDownTimerPausable timer = null;
    AnsAdapter ansAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private List<OptionModel> optionModelList;
    private Button next;

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

        optionModelList = new ArrayList<>();
        getList();
        recyclerView = view.findViewById(R.id.recyclerView);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);

        ansAdapter = new AnsAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ansAdapter);
        myTimer = view.findViewById(R.id.timer);

        ansAdapter.addAllData(optionModelList);
        initTimer(120*1000);
    }

    private void getList() {
        OptionModel optionModel = new OptionModel("A", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("B", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("C", "option a", "0");
        optionModelList.add(optionModel);
        optionModel = new OptionModel("D", "option a","0");
        optionModelList.add(optionModel);
    }

    private void initTimer(final long time){

        if(timer == null){
            timer = new CountDownTimerPausable(time, 1) {
                @Override
                public void onTick(long millisUntilFinished) {

                    myTimer.setText(convertSecondsToHMS(String.valueOf(millisUntilFinished / 1000)));
                }

                @Override
                public void onFinish() {
                    //showDoalogTimeUp();
                    //timer = null;
                   // refreshSnippetForTimeOut();

                }
            };

            this.timer.start();
        }

    }

    private String convertSecondsToHMS(String seconds){
        String result = "";

        int second = Integer.parseInt(seconds);

        int hr = (int)(second/3600);
        int rem = (int)(second%3600);
        int mn = rem/60;
        int sec = rem%60;
        //String hrStr = (hr<10 ? "0" : "")+hr;
        String mnStr = (mn<10 ? "0" : "")+mn;
        String secStr = (sec<10 ? "0" : "")+sec;

        //result = hrStr+":"+mnStr+":"+secStr;
        result = mnStr+":"+secStr;

        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                Fragment fragment = new QuizOverviewFragment();
                gotoFragment(fragment, "quizOverviewFragment");
                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
