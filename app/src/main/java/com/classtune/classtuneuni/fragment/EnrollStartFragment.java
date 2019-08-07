package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.utils.AppSharedPreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnrollStartFragment extends Fragment implements View.OnClickListener {

    Button enrollNow;
    Fragment fragment;

    public EnrollStartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enroll_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enrollNow = view.findViewById(R.id.enrollNow);
        enrollNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.enrollNow:
                if(AppSharedPreference.getUserType()== "3") {
                    fragment = new CodeEntryFragment();
                    gotoFragment(fragment, "codeEntryFragment");
                }
                else {
                    fragment = new TeacherAddCourseFragment();
                    gotoFragment(fragment, "teacherAddCourseFragment");
                }
                break;
        }
    }


    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
