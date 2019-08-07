package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classtune.classtuneuni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSelectionFragment extends Fragment implements View.OnClickListener {


    LinearLayout student, teacher;
    public UserSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_selection_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        student = view.findViewById(R.id.student);
        teacher = view.findViewById(R.id.teacher);

        student.setOnClickListener(this);
        teacher.setOnClickListener(this);

    }

    Fragment fragment;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.student:
                fragment = new RegistrationFragment();
                gotoFragment(fragment, "registrationFragment", "3");
            break;
            case R.id.teacher:
                fragment = new RegistrationFragment();
                gotoFragment(fragment, "registrationFragment", "2");
                break;
        }
    }

    private void gotoFragment(Fragment fragment, String tag, String userType) {
        // load fragment
        Bundle bundle = new Bundle();
        bundle.putString("userType",userType);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
