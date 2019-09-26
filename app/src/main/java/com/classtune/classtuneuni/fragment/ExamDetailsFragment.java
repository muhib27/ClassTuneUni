package com.classtune.classtuneuni.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.attendance.StudentAttendanceResponse;
import com.classtune.classtuneuni.exam.ExamDetailsResponse;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamDetailsFragment extends Fragment {

    private TextView examDay, examDate, examMonthYear, examName, examTime, marks, examSubject, obtainedMarks;
    private View examDotView;
    private BarChart chart;
    UIHelper uiHelper;
    private String id = "";
    public ExamDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(((MainActivity)getActivity()).tabRl.getVisibility() == View.VISIBLE)
        ((MainActivity)getActivity()).tabRl.setVisibility(View.GONE);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getArguments().getString("id") != null)
            id = getArguments().getString("id");
        uiHelper = new UIHelper(getActivity());
        examDay = view.findViewById(R.id.upcomingExamDay);
        examDate = view.findViewById(R.id.upcomingExamDate);
        examMonthYear = view.findViewById(R.id.upcomingExamMY);
        examName = view.findViewById(R.id.examName);
        marks = view.findViewById(R.id.marks);
        examTime = view.findViewById(R.id.examTime);
        examDotView = view.findViewById(R.id.examDotView);
        examSubject = view.findViewById(R.id.examSubject);
        obtainedMarks = view.findViewById(R.id.obtainedMarks);

        chart = view.findViewById(R.id.chart1);

        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        chart.animateXY(2000, 2000);
        chart.invalidate();

        if(!id.isEmpty())
            callExamDetails(id);
    }

    private void callExamDetails(String examId) {

        if (!NetworkConnection.getInstance().isNetworkAvailable()) {
            Toast.makeText(getActivity(), "No Connectivity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!uiHelper.isDialogActive())
            uiHelper.showLoadingDialog("Please wait...");

        // RetrofitApiClient.getApiInterface().getTaskAssign(requestBody)
        RetrofitApiClient.getApiInterfaceWithId().getStudentExamDetails(AppSharedPreference.getApiKey(), examId)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ExamDetailsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ExamDetailsResponse> value) {
                        uiHelper.dismissLoadingDialog();

                        ExamDetailsResponse examDetailsResponse = value.body();
                        if (examDetailsResponse.getStatus().getCode() == 200) {
                           // populateData(studentAttendanceResponse.getData());


                            //Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                           // Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
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
}
