package com.classtune.classtuneuni.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.LoginActivity;
import com.classtune.classtuneuni.model.User;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MorePageFragment extends Fragment implements View.OnClickListener {

    Fragment fragment;
    UIHelper uiHelper;
    RelativeLayout rl_8, rl_1, rl_2, rl_3, rl_4, rl_5, rl_6, rl_7;

    public MorePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uiHelper = new UIHelper(getActivity());

        rl_1 = view.findViewById(R.id.rl_1);
        rl_1.setOnClickListener(this);

        rl_2 = view.findViewById(R.id.rl_2);
        rl_2.setOnClickListener(this);

        rl_3 = view.findViewById(R.id.rl_3);
        rl_3.setOnClickListener(this);

        rl_4 = view.findViewById(R.id.rl_4);
        rl_4.setOnClickListener(this);

        rl_5 = view.findViewById(R.id.rl_5);
        rl_5.setOnClickListener(this);

        rl_6 = view.findViewById(R.id.rl_6);
        rl_6.setOnClickListener(this);

        rl_7 = view.findViewById(R.id.rl_7);
        rl_7.setOnClickListener(this);

        rl_8 = view.findViewById(R.id.rl_8);
        rl_8.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_8:
                callLogOutApi();
                break;
            case R.id.rl_2:
                fragment = new EnrollStartFragment();
                gotoFragment(fragment, "enrollStartFragment");
                break;
        }
    }


    private void callLogOutApi() {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            //Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        uiHelper.showLoadingDialog("Please wait...");


        RetrofitApiClient.getApiInterface().getLogout(AppSharedPreference.getApiKey(), AppSharedPreference.getFcm())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<JsonElement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<JsonElement> value) {
                        uiHelper.dismissLoadingDialog();

                        if (value.code() == 200)
                            userLogout();

                    }


                    @Override
                    public void onError(Throwable e) {

//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });
    }

    private void userLogout() {
        User user = new User("", "", "", "");
        AppSharedPreference.setUserBasicInfo(user);
        if (AppSharedPreference.getRememberMe()) {
            AppSharedPreference.setUserNameAndPassword(AppSharedPreference.getUserName(), AppSharedPreference.getUserPassword(), "", true);

        } else {
            AppSharedPreference.setUserNameAndPassword("", "", "", false);
        }
        AppSharedPreference.setUsingFirstTime(true);
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void gotoFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
