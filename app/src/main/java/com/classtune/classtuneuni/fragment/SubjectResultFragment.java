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
import com.classtune.classtuneuni.adapter.ComResultAdapter;
import com.classtune.classtuneuni.adapter.SubjectResultAdapter;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.SubjectResultModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubjectResultFragment extends Fragment implements SubjectResultAdapter.ItemListener {
    TabHost mTabHost;
    RecyclerView recyclerView;
    private ArrayList<SubjectResultModel> resultList;
    LinearLayoutManager linearLayoutManager;

    public SubjectResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup();
        setupTab(new TextView(getContext()), "All", "Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2", "Summer 2018");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 1","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");

        recyclerView = view.findViewById(R.id.recyclerView);

        resultList = new ArrayList<>();

        resultList.add(new SubjectResultModel( "Attendance", "10%", "9"));
        resultList.add(new SubjectResultModel( "Class Test", "10%", "8"));
        resultList.add(new SubjectResultModel("Assignment", "10%", "10"));
        resultList.add(new SubjectResultModel( "Attendance", "10%", "9"));
        resultList.add(new SubjectResultModel( "Class Test", "10%", "8"));
        resultList.add(new SubjectResultModel("Assignment", "10%", "10"));
        resultList.add(new SubjectResultModel( "Attendance", "10%", "9"));
        resultList.add(new SubjectResultModel( "Class Test", "10%", "8"));
        resultList.add(new SubjectResultModel("Assignment", "10%", "10"));

        SubjectResultAdapter subjectResultAdapter = new SubjectResultAdapter(getActivity(), resultList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subjectResultAdapter);
    }

    private void setupTab(final View view, final String tag, String tag1) {
        View tabview = createTabView(mTabHost.getContext(), tag, tag1);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text, final String text1) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        return view;
    }

    @Override
    public void onItemClick(SubjectResultModel item, int pos) {
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
