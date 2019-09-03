package com.classtune.classtuneuni.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.fragment.LoginFragment;
import com.classtune.classtuneuni.fragment.PreLoginFragment;
import com.classtune.classtuneuni.utils.AppSharedPreference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_pre_login);

        if(AppSharedPreference.getFirstTimeLogin()) {
            fragment = new PreLoginFragment();
            loadFragment(fragment, "preLoginFragment");
        }
        else {
            fragment = new LoginFragment();
            loadFragment(fragment, "loginFragment");
        }
    }


    private void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.loginContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {

    }
}
