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
import com.classtune.classtuneuni.adapter.StProfileInfoAdapter;
import com.classtune.classtuneuni.model.ComResult;
import com.classtune.classtuneuni.model.ProfileCourseModel;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfileFragment extends Fragment implements StProfileInfoAdapter.ItemListener {
    RecyclerView recyclerView;
    private ArrayList<ProfileCourseModel> resultList;
    LinearLayoutManager linearLayoutManager;

    public StudentProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerView);

        resultList = new ArrayList<>();

        resultList.add(new ProfileCourseModel( "CSE 101", "94%", "A"));
        resultList.add(new ProfileCourseModel( "CSE 101", "87%", "B"));
        resultList.add(new ProfileCourseModel("CSE 101", "89%", "C"));

        StProfileInfoAdapter stProfileInfoAdapter = new StProfileInfoAdapter(getActivity(), resultList, this);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stProfileInfoAdapter);
    }


    @Override
    public void onItemClick(ProfileCourseModel item, int pos) {
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
