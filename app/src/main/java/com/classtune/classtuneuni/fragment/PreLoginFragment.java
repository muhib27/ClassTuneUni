package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
public class PreLoginFragment extends Fragment implements View.OnClickListener {

    Button login, register;
    public PreLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pre_login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // AppSharedPreference.setUsingFirstTime(false);

        login = view.findViewById(R.id.login);
        register = view.findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    Fragment fragment;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                fragment = new LoginFragment();
                gotoFragment(fragment, "loginFragment");
                break;
            case R.id.register:
                fragment = new UserSelectionFragment();
                gotoFragment(fragment, "userSelectionFragment");
                break;
        }
    }


        private void gotoFragment(Fragment fragment, String tag) {
            // load fragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.loginContainer, fragment, tag);
            //transaction.addToBackStack(null);
            transaction.commit();
        }

//    private void gotoRegister(){
//
//    }
}
