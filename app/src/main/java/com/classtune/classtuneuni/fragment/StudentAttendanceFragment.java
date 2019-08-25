package com.classtune.classtuneuni.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.PagerAdapter;
import com.classtune.classtuneuni.attendance.StAttendanceData;
import com.classtune.classtuneuni.attendance.StudentAttendanceResponse;
import com.classtune.classtuneuni.course_resonse.CorsSecStudentResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
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
public class StudentAttendanceFragment extends Fragment {

    UIHelper uiHelper;
    private TextView total, present, parcentage;
    public StudentAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_attendance, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).tabRl.setVisibility(View.VISIBLE);
        uiHelper = new UIHelper(getActivity());
        total = view.findViewById(R.id.total);
        present = view.findViewById(R.id.present);
        parcentage = view.findViewById(R.id.parcentage);
        callStAttendance("19");

    }


    private void callStAttendance(String courseId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStudentAttendance(AppSharedPreference.getApiKey(), courseId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<StudentAttendanceResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<StudentAttendanceResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        StudentAttendanceResponse studentAttendanceResponse = value.body();
                        if (studentAttendanceResponse.getStatus().getCode() == 200) {
                            populateData(studentAttendanceResponse.getData());

                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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

    private void populateData(StAttendanceData data) {
        if(data.getTotalClass()!=null)
            total.setText("" + data.getTotalClass());
        if(data.getPresent()!=null)
            present.setText("" + data.getPresent());
        if(data.getPercentage()!=null)
            parcentage.setText("" + data.getPercentage() + "%" );
    }

}
