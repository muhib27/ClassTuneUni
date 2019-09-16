package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.adapter.StCourseAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.utils.PaginationAdapterCallback;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentCourseListFragment extends Fragment implements View.OnClickListener, PaginationAdapterCallback {
    RecyclerView recyclerView;
    private List<Assignment> assignmentList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    StCourseAdapter stCourseAdapter;

    public StudentCourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uiHelper = new UIHelper(getActivity());

        recyclerView = view.findViewById(R.id.recyclerView);

        assignmentList = new ArrayList<>();

//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));
//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));
//        assignmentList.add(new AssignmentModel("Title", "Md. Rohim", "CSE 101", "2.5", "22 July, 2019", "25 July, 2019"));

        stCourseAdapter = new StCourseAdapter(getActivity(), this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void retryPageLoad() {

    }
}
