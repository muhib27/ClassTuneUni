package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollSuccessFragment extends Fragment {
    private TextView courseName, courseCode, teacherName, section, duration;


    public EnrollSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enroll_success, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseName = view.findViewById(R.id.courseName);
        courseCode = view.findViewById(R.id.courseCode);
        teacherName = view.findViewById(R.id.teacherName);
        section = view.findViewById(R.id.section);
        duration = view.findViewById(R.id.duration);


        ((MainActivity)getActivity()).callStudentSectionListApi();


        if(getArguments().getString("courseName") != null)
            courseName.setText(getArguments().getString("courseName"));
        if(getArguments().getString("courseCode") != null)
            courseCode.setText(getArguments().getString("courseCode"));
        if(getArguments().getString("teacherName") != null)
            teacherName.setText(getArguments().getString("teacherName"));
        if(getArguments().getString("section") != null)
            section.setText(getArguments().getString("section"));
        if(getArguments().getString("duration") != null)
            duration.setText(getArguments().getString("duration"));
    }
}
