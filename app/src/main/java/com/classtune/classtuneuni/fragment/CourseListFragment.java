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
import com.classtune.classtuneuni.adapter.CourseListAdapter;
import com.classtune.classtuneuni.adapter.TeacherExamListAdapter;
import com.classtune.classtuneuni.model.CourseModel;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment implements CourseListAdapter.ItemListener{

    RecyclerView recyclerView;
    private ArrayList<CourseModel> examList;
    LinearLayoutManager linearLayoutManager;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        examList = new ArrayList<>();

        examList = new ArrayList<>();

        examList.add(new CourseModel( "Class Test 2","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new CourseModel( "Class Test 3","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new CourseModel( "Class Test 4","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));
        examList.add(new CourseModel( "Class Test 5","Md. Rahim", "CSE 101", "10 July, 2019", "8", "10"));


        CourseListAdapter courseListAdapter = new CourseListAdapter(getActivity(), examList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(courseListAdapter);
    }

    @Override
    public void onItemClick(CourseModel item, int pos) {

    }
}
