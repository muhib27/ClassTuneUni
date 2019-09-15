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

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.AssignmentAdapter;
import com.classtune.classtuneuni.adapter.HomeResourceAdapter;
import com.classtune.classtuneuni.adapter.ResourceAdapter;
import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.utils.UIHelper;
import com.classtune.classtuneuni.utils.VerticalSpaceItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragmentNew.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragmentNew#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentNew extends Fragment {

    RecyclerView rvNotice, rvAssignmnet, rvResource;
    private List<Assignment> assignmentList;
    LinearLayoutManager linearLayoutManager;
    UIHelper uiHelper;
    AssignmentAdapter assignmentAdapter;

    HomeResourceAdapter homeResourceAdapter;

    GridLayoutManager manager;


    public HomeFragmentNew() {
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

        rvNotice = view.findViewById(R.id.rvNotice);
        rvAssignmnet = view.findViewById(R.id.rvAssignment);
        rvResource = view.findViewById(R.id.rvResources);

        homeResourceAdapter = new HomeResourceAdapter(getActivity());
        rvResource.setAdapter(homeResourceAdapter);

        rvResource.addItemDecoration(new VerticalSpaceItemDecoration(getResources()));
//        recyclerView.setLayoutManager(linearLayoutManager);

        manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        rvResource.setLayoutManager(manager);
        rvResource.setItemAnimator(new DefaultItemAnimator());

    }
}
