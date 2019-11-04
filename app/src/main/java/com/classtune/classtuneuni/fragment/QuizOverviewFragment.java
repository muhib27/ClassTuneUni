package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.adapter.QuizGridAdapter;
import com.classtune.classtuneuni.model.QuizGridModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizOverviewFragment extends Fragment {
    private List<QuizGridModel> quizGridModelList;

    private RecyclerView recyclerView;
    QuizGridAdapter quizGridAdapter;
    GridLayoutManager manager;
    public QuizOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quizGridModelList = new ArrayList<>();
        getList();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        quizGridAdapter = new QuizGridAdapter(getActivity());
        manager = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(quizGridAdapter);

        quizGridAdapter.addAllData(quizGridModelList);

    }

    private void getList() {
        QuizGridModel quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);
        quizGridModel = new QuizGridModel("1");
        quizGridModelList.add(quizGridModel);

    }
}
