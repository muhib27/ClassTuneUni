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
import com.classtune.classtuneuni.exam.Exam;
import com.classtune.classtuneuni.exam.ExamDetailsResponse;
import com.classtune.classtuneuni.exam.ExamMinAvgMax;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
import com.classtune.classtuneuni.utils.AppUtility;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

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

        if (getArguments() !=null && getArguments().getString("id") != null)
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
                            populateData(examDetailsResponse.getData().getExam());
                            if(examDetailsResponse.getData().getMark()!=null)
                                obtainedMarks.setText(examDetailsResponse.getData().getMark().getScore());
                            if((examDetailsResponse.getData().getExamMinAvgMax()!=null) && (examDetailsResponse.getData().getExamMinAvgMax().getAvgMark()!=null) && (examDetailsResponse.getData().getExamMinAvgMax().getMinMark()!=null) && (examDetailsResponse.getData().getExamMinAvgMax().getMaxMark()!=null))
                            showChart(examDetailsResponse.getData().getExamMinAvgMax().getAvgMark(), examDetailsResponse.getData().getExamMinAvgMax().getMinMark(), examDetailsResponse.getData().getExamMinAvgMax().getMaxMark());


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


    private void populateData(Exam exam) {
        if(exam.getExamName()!=null)
            examName.setText(exam.getExamName());
        if(exam.getCourseName()!=null)
            examSubject.setText(exam.getCourseName());
        if(exam.getExamMark()!=null) {
            String mark = "";
            if(exam.getExamMark().contains("."))
            {
                String[] marks = exam.getExamMark().split("\\.");
                if(marks.length>0)
                    mark = marks[0];
            }
            else
                mark = exam.getExamMark();
            marks.setText("" + mark);
        }
        if(exam.getDayName()!=null)
            examDay.setText(exam.getDayName());

        if(exam.getExamDate() !=null && exam.getExamDate().contains("-"))
        {
            String[] parts = exam.getExamDate().split("-");
            if(parts.length>=2)
                examDate.setText(parts[2]);
        }
        if(exam.getExamDate() !=null && exam.getExamDate().contains("-"))
            examMonthYear.setText(AppUtility.getDateString(exam.getExamDate(), AppUtility.DATE_FORMAT_APP_M_Y, AppUtility.DATE_FORMAT_SERVER));

    }

    private void showChart(String avgMark, String minMark, String maxMark)
    {
        BarData data = new BarData(getXAxisValues(), getDataSet( Float.parseFloat(avgMark),  Float.parseFloat(minMark),  Float.parseFloat(maxMark)));
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.setDescription("");



        chart.invalidate();
    }

    private ArrayList<IBarDataSet> getDataSet(float avg, float min, float max) {
        ArrayList<IBarDataSet> dataSets = null;
        //  Log.e("DDD_DATA", ""+data.getMark());
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(max, 0); // totalClass
        valueSet1.add(v1e1);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(avg, 1); //present
        valueSet2.add(v2e1);

        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
        BarEntry v3e1 = new BarEntry(min, 2); // highest mark
        valueSet3.add(v3e1);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, getString(R.string.highest));
        barDataSet1.setColor(getResources().getColor(R.color.total_class));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, getString(R.string.average));
        barDataSet2.setColor(getResources().getColor(R.color.ash_b5));

        BarDataSet barDataSet3 = new BarDataSet(valueSet3, getString(R.string.lowest));
        barDataSet3.setColor(getResources().getColor(R.color.appColor));

        barDataSet1.setBarSpacePercent(-30f);
        barDataSet2.setBarSpacePercent(-30f);
        barDataSet3.setBarSpacePercent(-30f);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);


        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("1");
        xAxis.add("2");
        xAxis.add("3");
        return xAxis;
    }
}
