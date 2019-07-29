package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classtune.classtuneuni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollStartFragment extends Fragment {


    public EnrollStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enroll_start, container, false);
    }

}
