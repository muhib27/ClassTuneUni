package com.classtune.classtuneuni.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeAssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeNoticeAdapter;
import com.classtune.classtuneuni.adapter.HomeResourceAdapter;
import com.classtune.classtuneuni.adapter.ItemAdapter;
import com.classtune.classtuneuni.adapter.ResourceAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private TextView topDate, topTitle, chapter, subCode, author;
    private TextView notice1Date, notice1Title, notice2Date, notice2Title;
    private TextView nextSubject, nextClassTime, classInstructor, dayText, room;
    private TextView examDay, examDate, examMonthYear, examName, examTime, marks, examSubject;

    RecyclerView rvNotice, rvAssignmnet, rvResource;
    private List<Assignment> assignmentList;
    LinearLayoutManager noticelinearLayoutManager, assignmentLayoutManager;
    UIHelper uiHelper;
    AssignmentAdapter assignmentAdapter;

    HomeNoticeAdapter homeNoticeAdapter;

    HomeResourceAdapter homeResourceAdapter;
    HomeAssignmentAdapter homeAssignmentAdapter;

    GridLayoutManager manager;
    private List<Resource> resourceList;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());

        topDate = view.findViewById(R.id.topDate);
        topTitle = view.findViewById(R.id.topTitle);
        chapter = view.findViewById(R.id.chapter);
        subCode = view.findViewById(R.id.subCode);
        author = view.findViewById(R.id.authorName);

        notice1Date = view.findViewById(R.id.notice1Date);
        notice2Date = view.findViewById(R.id.notice2Date);
        notice1Title = view.findViewById(R.id.notice1Title);
        notice2Title = view.findViewById(R.id.notice2Title);

        nextSubject = view.findViewById(R.id.next_subject);
        nextClassTime = view.findViewById(R.id.nextClassTime);
        classInstructor = view.findViewById(R.id.next_instructor);
        dayText = view.findViewById(R.id.dayText);
        room = view.findViewById(R.id.room);

        examDay = view.findViewById(R.id.upcomingExamDay);
        examDate = view.findViewById(R.id.upcomingExamDate);
        examMonthYear = view.findViewById(R.id.upcomingExamMY);
        examName = view.findViewById(R.id.examName);
        examTime = view.findViewById(R.id.examTime);
        examSubject = view.findViewById(R.id.examSubject);




        rvNotice = view.findViewById(R.id.rvNotice);
        rvAssignmnet = view.findViewById(R.id.rvAssignment);
        rvResource = view.findViewById(R.id.rvResources);

        resourceList = new ArrayList<>();
        List<Notice> noticeList;



        homeResourceAdapter = new HomeResourceAdapter(getActivity());
        rvResource.setAdapter(homeResourceAdapter);
        rvResource.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvResource.setLayoutManager(manager);
        rvResource.setItemAnimator(new DefaultItemAnimator());

        noticelinearLayoutManager = new LinearLayoutManager(getActivity());
        homeNoticeAdapter = new HomeNoticeAdapter(getActivity());
        rvNotice.setAdapter(homeNoticeAdapter);
        rvNotice.setLayoutManager(noticelinearLayoutManager);

        assignmentLayoutManager = new LinearLayoutManager(getActivity());
        homeAssignmentAdapter = new HomeAssignmentAdapter(getActivity());
        rvNotice.setAdapter(homeAssignmentAdapter);
        rvNotice.setLayoutManager(assignmentLayoutManager);
        

    }
}
