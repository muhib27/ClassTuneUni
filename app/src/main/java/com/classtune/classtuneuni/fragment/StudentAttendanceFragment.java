package com.classtune.classtuneuni.fragment;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.activity.MainActivity;
import com.classtune.classtuneuni.adapter.PagerAdapter;
import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.attendance.StAttendanceData;
import com.classtune.classtuneuni.attendance.StudentAttendanceResponse;
import com.classtune.classtuneuni.course_resonse.CorsSecStudentResponse;
import com.classtune.classtuneuni.response.StCourseSection;
import com.classtune.classtuneuni.retrofit.RetrofitApiClient;
import com.classtune.classtuneuni.utils.AppSharedPreference;
//import com.classtune.classtuneuni.utils.MyValueFormatter;
import com.classtune.classtuneuni.utils.NetworkConnection;
import com.classtune.classtuneuni.utils.UIHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.classtune.classtuneuni.activity.MainActivity.GlobalCourseId;
import static com.classtune.classtuneuni.activity.MainActivity.GlobalOfferedCourseSectionId;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentAttendanceFragment extends Fragment {

    UIHelper uiHelper;
    int pos = 0;
    private TextView total, present, parcentage;
    String courseOfferSectionId = "";
    public StudentAttendanceFragment() {
        // Required empty public constructor
    }
    private BarChart chart;



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

        chart = view.findViewById(R.id.chart1);
       // chart.getDescription().setEnabled(false);

//        BarData data = new BarData(getXAxisValues(), getDataSet());
//        chart.setData(data);
//        chart.setDrawValueAboveBar(false);
//        chart.getXAxis().setTextSize(15f);
//       // chart.setDescription("Percent(%)");
//        //chart.getLegend().setEnabled(false);
//
//        // Hide the desc value of each bar on top
       // chart.getXAxis().setEnabled(false);

        YAxis rightYAxis = chart.getAxisRight();
        rightYAxis.setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setEnabled(false);

        chart.animateXY(2000, 2000);
        chart.invalidate();
        ((MainActivity)getActivity()).mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int pos = ((MainActivity)getActivity()).mTabHost.getCurrentTab();
                if(AppSharedPreference.getUserType().equals("3"))
                {
                    StCourseSection ss = AppSharedPreference.getStUserTab(s, pos);
                    GlobalCourseId = ss.getCourseCode();
                    GlobalOfferedCourseSectionId = ss.getCourseOfferSectionId();
                    callStAttendance(GlobalOfferedCourseSectionId);

                }
                else {
                    AssignmentSection ss = AppSharedPreference.getUserTab(s, pos);
                    GlobalCourseId = ss.getCourseId();
                    GlobalOfferedCourseSectionId = ss.getOfferedSectionId();
                   // callResourceListApi(GlobalOfferedCourseSectionId);
                }
            }
        });


        if (getArguments() !=null && getArguments().getString("id") != null)
            courseOfferSectionId = getArguments().getString("id");
        if(courseOfferSectionId.isEmpty()) {
            callStAttendance(GlobalOfferedCourseSectionId);
        }
        else {
            for(int i=0; i<((MainActivity)getActivity()).mTabHost.getTabWidget().getTabCount(); i++)
            {
                String s = ((MainActivity)getActivity()).mTabHost.getTabWidget().getChildTabViewAt(i).getTag().toString();
                if(courseOfferSectionId.equals(((MainActivity)getActivity()).mTabHost.getTabWidget().getChildTabViewAt(i).getTag())) {
                    pos = i;
                    break;
                }
            }
            ((MainActivity)getActivity()).mTabHost.setCurrentTab(pos);
            callStAttendance(courseOfferSectionId);
        }

    }


//    private void setData(int present, int absent, int totalClass) {
//        ArrayList<BarEntry> yVal = new ArrayList<>();
////        for(int i = 0; i< count; i++){
////            float value = (float)(Math.random()*100);
////            yVal.add(new BarEntry(i, (int)value));
////        }
//
//        BarEntry v1e1 = new BarEntry(2, present);
//        yVal.add(v1e1);
//
//        BarEntry v1e2 = new BarEntry(3, absent);
//        yVal.add(v1e2);
//
//
//
//        BarDataSet set = new BarDataSet(yVal, "Data set");
//        set.setColors(ColorTemplate.MATERIAL_COLORS);
//        set.setDrawValues(true);
//
//        BarData data = new BarData(set);
//
//
////        chart.getXAxis().setEnabled(false);
//      chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//
//        YAxis rightYAxis = chart.getAxisRight();
//        rightYAxis.setEnabled(false);
//        rightYAxis.setDrawGridLines(false);
//
//        chart.setData(data);
//        chart.invalidate();
//        chart.animateY(500);
//    }


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

        //setData(data.getPresent(), data.getAbsent(), data.getTotalClass());
        //chart.setFitBars(true);
        showChart(data.getPresent(), data.getAbsent(), data.getTotalClass());
    }



    private void showChart(int present, int absent, int totalClass)
    {
        BarData data = new BarData(getXAxisValues(), getDataSet(present, absent, totalClass));
        chart.setData(data);
        chart.animateXY(2000, 2000);
        chart.setDescription("");



        chart.invalidate();
    }

    private ArrayList<IBarDataSet> getDataSet(int present, int absent, int totalClass) {
        ArrayList<IBarDataSet> dataSets = null;
      //  Log.e("DDD_DATA", ""+data.getMark());
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(totalClass, 0); // totalClass
        valueSet1.add(v1e1);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(present, 1); //present
        valueSet2.add(v2e1);

//        ArrayList<BarEntry> valueSet3 = new ArrayList<>();
//        BarEntry v3e1 = new BarEntry(Float.parseFloat(getDecimalFormatNumber(data.getMaxMark())), 2); // highest mark
//        valueSet3.add(v3e1);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, getString(R.string.total_class));
        barDataSet1.setColor(getResources().getColor(R.color.total_class));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, getString(R.string.present));
        barDataSet2.setColor(getResources().getColor(R.color.appColor));

//        BarDataSet barDataSet3 = new BarDataSet(valueSet3, getString(R.string.title));
//        barDataSet3.setColor(Color.rgb(0, 0, 155));

        barDataSet1.setBarSpacePercent(-50f);
        barDataSet2.setBarSpacePercent(-50f);
//        barDataSet3.setBarSpacePercent(-50f);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
//        dataSets.add(barDataSet3);


        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("1");
        xAxis.add("2");
//        xAxis.add("3");
        return xAxis;
    }

//    private void setChart(){
//        chart.setOnChartValueSelectedListener(this);
//
//        chart.setDrawBarShadow(false);
//        chart.setDrawValueAboveBar(true);
//
//        chart.getDescription().setEnabled(false);
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        chart.setMaxVisibleValueCount(60);
//
//        // scaling can now only be done on x- and y-axis separately
//        chart.setPinchZoom(false);
//
//        chart.setDrawGridBackground(false);
//        // chart.setDrawYLabels(false);
//
//        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(tfLight);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f); // only intervals of 1 day
//        xAxis.setLabelCount(7);
//        xAxis.setValueFormatter(xAxisFormatter);
//
//        ValueFormatter custom = new MyValueFormatter("$");
//
//        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        YAxis rightAxis = chart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setTypeface(tfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setForm(Legend.LegendForm.SQUARE);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
//
//    }


//    private ArrayList<BarDataSet> getDataSet() {
//        ArrayList<BarDataSet> dataSets = null;
//
//        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//        BarEntry v1e1 = new BarEntry(96.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(82.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(88.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(52.000f, 3); // Apr
//        valueSet1.add(v1e4);
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Availability of Drugs/Supplies");
//        //Chnaging the color
//        barDataSet1.setColor(Color.rgb(0, 169, 157));
//        //barDataSet1.setBarSpacePercent(30f);
//
//
//        // Hide the value on each bar
//        /*barDataSet1.setDrawValues(false);*/
//
//        dataSets = new ArrayList<>();
//        dataSets.add(barDataSet1);
//        return dataSets;
//    }
//
//    private ArrayList<String> getXAxisValues() {
//        ArrayList<String> yAxis = new ArrayList<>();
//        yAxis.add("Well behaved staff (Total)");
//        yAxis.add("Utilization of untied fund adequate (Total)");
//        yAxis.add("Awareness generation (use ofIEC/BCC) (Total)");
//        yAxis.add("Grievance redressal mechanismin place (Total)");
//
//        return yAxis;
//    }


}
