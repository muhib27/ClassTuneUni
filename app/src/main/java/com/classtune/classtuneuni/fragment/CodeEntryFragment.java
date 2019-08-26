package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.enroll.StEnrollData;
import com.classtune.classtuneuni.enroll.StEnrollResponse;
import com.classtune.classtuneuni.notice.NoticeDetailsResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CodeEntryFragment extends Fragment implements View.OnClickListener {

    Fragment fragment;
    ImageButton nextBtn;
    UIHelper uiHelper;
    private EditText et1, et2, et3, et4, et5, et6;

    public CodeEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code_entry, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
            ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);



        uiHelper = new UIHelper(getActivity());

        et1 = view.findViewById(R.id.editText1);
        et2 = view.findViewById(R.id.editText2);
        et3 = view.findViewById(R.id.editText3);
        et4 = view.findViewById(R.id.editText4);
        et5 = view.findViewById(R.id.editText5);
        et6 = view.findViewById(R.id.editText6);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et1.getText().toString().trim().length() >0){
                    et1.clearFocus();
                    et2.requestFocus();
                }
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et2.getText().toString().trim().length() >0){
                    et2.clearFocus();
                    et3.requestFocus();
                }
            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et3.getText().toString().trim().length() >0){
                    et3.clearFocus();
                    et4.requestFocus();
                }
            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et4.getText().toString().trim().length() >0){
                    et4.clearFocus();
                    et5.requestFocus();
                }
            }
        });
        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et5.getText().toString().trim().length() >0){
                    et5.clearFocus();
                    et6.requestFocus();
                }
            }
        });

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // add a condition to check length here - you can give here length according to your requirement to go to next EditTexts.
                if(et6.getText().toString().trim().length() >0){
                    et6.clearFocus();
                    //et6.requestFocus();
                }
            }
        });


        nextBtn = view.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nextBtn:
                String stCode = "";
                if(!et1.getText().toString().trim().isEmpty())
                    stCode  = stCode + et1.getText().toString().trim();
                if(!et2.getText().toString().trim().isEmpty())
                    stCode  = stCode + et2.getText().toString().trim();
                if(!et3.getText().toString().trim().isEmpty())
                    stCode  = stCode + et3.getText().toString().trim();
                if(!et4.getText().toString().trim().isEmpty())
                    stCode  = stCode + et4.getText().toString().trim();
                if(!et5.getText().toString().trim().isEmpty())
                    stCode  = stCode + et5.getText().toString().trim();
                if(!et6.getText().toString().trim().isEmpty())
                    stCode  = stCode + et6.getText().toString().trim();
                if(stCode.length() >=6)
                    callStNEnroll(stCode);

//                fragment = new EnrollSuccessFragment();
                //fragment = new CourseOfferFragment();
                //gotoFragment(fragment, "enrollSuccessFragment");
                break;
        }
    }


//    private void gotoFragment(Fragment fragment, String tag, StEnrollData data) {
//        // load fragment
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.mainContainer, fragment, tag);
//        //transaction.addToBackStack(null);
//        transaction.commit();
//    }

    private void callStNEnroll(String code) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStEnroll(AppSharedPreference.getApiKey(), code)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StEnrollResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StEnrollResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StEnrollResponse stEnrollResponse = value.body();
                        if (stEnrollResponse.getStatus().getCode() == 200) {
                            fragment = new EnrollSuccessFragment();
                            gotoNextFragment(fragment, "enrollSuccessFragment", stEnrollResponse.getData());
//                            populateData(noticeDetailsResponse.getData());

                          //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        uiHelper.dismissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
//                        progressDialog.dismiss();
                        uiHelper.dismissLoadingDialog();
                    }
                });
    }


    private void gotoNextFragment(Fragment fragment, String tag, StEnrollData data) {
        // load fragment
        Bundle bundle = new Bundle();
        if(data.getCourseName()!=null)
        bundle.putString("courseName", data.getCourseName());
        if(data.getCourseCode()!=null)
        bundle.putString("courseCode", data.getCourseCode());
        if(data.getInstructor()!=null)
        bundle.putString("teacherName", data.getInstructor());
        if(data.getSectionName()!=null)
        bundle.putString("section", data.getSectionName());
        bundle.putString("duration", getTimeLine(data.getStartDate(), data.getEndDate()));
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String getTimeLine(String startDate, String endDate) {
        String timeline = "";
        if(startDate != null && startDate.contains("-"))
            timeline = timeline + AppUtility.getDateString(startDate, AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER);
        if(endDate !=null && endDate.contains("-"))
            timeline = timeline +  " - " +  AppUtility.getDateString(endDate, AppUtility.DATE_FORMAT_APP, AppUtility.DATE_FORMAT_SERVER);
        return timeline;
    }
}
