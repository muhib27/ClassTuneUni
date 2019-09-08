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

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.ComResultAdapter;
import com.classtune.classtuneuni.adapter.ResultEntryAdapter;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.ResultEntryModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherResultEntryFragment extends Fragment implements ResultEntryAdapter.ItemListener {
    RecyclerView recyclerView;
    private ArrayList<ResultEntryModel> resultList;
    LinearLayoutManager linearLayoutManager;
    String examName = "", assessmentName = "", courseCode = "", examDate = "", participant = "", total = "";
    private TextView examNameTv, assessmentNameTv, courseCodeTv, examDateTv, participantTv, totalTv;

    ResultEntryAdapter resultEntryAdapter;
    public TeacherResultEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);

        if (getArguments().getString("examName") != null)
            examName = getArguments().getString("examName");
        if (getArguments().getString("assessmentName") != null)
            assessmentName = getArguments().getString("assessmentName");
        if (getArguments().getString("courseCode") != null)
            courseCode = getArguments().getString("courseCode");
        if (getArguments().getString("examDate") != null)
            examDate = getArguments().getString("examDate");
        if (getArguments().getString("participant") != null)
            participant = getArguments().getString("participant");
        if (getArguments().getString("total") != null)
            total = getArguments().getString("total");

        examNameTv = view.findViewById(R.id.title);
        assessmentNameTv = view.findViewById(R.id.name);
        courseCodeTv = view.findViewById(R.id.courseCode);
        examDateTv = view.findViewById(R.id.examDate);
        participantTv = view.findViewById(R.id.participant);
        totalTv = view.findViewById(R.id.total);

        totalTv.setText(total);
        participantTv.setText(participant);
        examDateTv.setText(examDate);
        courseCodeTv.setText(courseCode);
        assessmentNameTv.setText(assessmentName);
        examDateTv.setText(examDate);

        recyclerView = view.findViewById(R.id.recyclerView);

        resultList = new ArrayList<>();


        resultEntryAdapter = new ResultEntryAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(resultEntryAdapter);
    }


    @Override
    public void onItemClick(ResultEntryModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        SubjectResultFragment subjectResultFragment = new SubjectResultFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, subjectResultFragment, "subjectResultFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
