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
import com.classtune.classtuneuni.adapter.StudentSummaryAdapter;
import com.classtune.classtuneuni.adapter.SubjectResultAdapter;
import com.classtune.classtuneuni.model.StudentSummaryModel;
import com.classtune.classtuneuni.model.SubjectResultModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentsSummaryFragment extends Fragment implements StudentSummaryAdapter.ItemListener {
    RecyclerView recyclerView;
    private ArrayList<StudentSummaryModel> resultList;
    LinearLayoutManager linearLayoutManager;

    public StudentsSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_summary_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        resultList = new ArrayList<>();

        resultList.add(new StudentSummaryModel( "Attendance", "10%", "9"));
        resultList.add(new StudentSummaryModel( "Class Test", "10%", "8"));
        resultList.add(new StudentSummaryModel("Assignment", "10%", "10"));
        resultList.add(new StudentSummaryModel( "Attendance", "10%", "9"));
        resultList.add(new StudentSummaryModel( "Class Test", "10%", "8"));
        resultList.add(new StudentSummaryModel("Assignment", "10%", "10"));
        resultList.add(new StudentSummaryModel( "Attendance", "10%", "9"));
        resultList.add(new StudentSummaryModel( "Class Test", "10%", "8"));
        resultList.add(new StudentSummaryModel("Assignment", "10%", "10"));

        StudentSummaryAdapter subjectResultAdapter = new StudentSummaryAdapter(getActivity(), resultList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subjectResultAdapter);
    }


    @Override
    public void onItemClick(StudentSummaryModel item, int pos) {
        // Toast.makeText(getActivity(), " " + pos, Toast.LENGTH_LONG).show();
        loadFragment();
    }

    private void loadFragment() {
        // load fragment
        AssignmentDetailsFragment assignmentDetailsFragment = new AssignmentDetailsFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, assignmentDetailsFragment, "assignmentDetailsFragment");
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
