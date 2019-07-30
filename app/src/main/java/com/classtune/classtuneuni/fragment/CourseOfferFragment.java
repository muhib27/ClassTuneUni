package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classtune.classtuneuni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseOfferFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fab;
    View v;
    ViewGroup main;

    public CourseOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_course_offer, container, false);

        main = (ViewGroup) v.findViewById(R.id.insert_point);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = view.findViewById(R.id.addBtn);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBtn:
                addSection();
                break;
        }
    }

    static int count = 0;


    private void addSection(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.section_view, null);

        TextView textView = view.findViewById(R.id.i_am_id);
        textView.setText("A");


        main.addView(textView);
        count++;
    }

}
