package com.classtune.classtuneuni.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.classtune.classtuneuni.R;
import com.classtune.classtuneuni.utils.MyValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceSummaryFragment extends Fragment {
    private BarChart barChart;
    TabHost mTabHost;
    public AttendanceSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attendance_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);

        mTabHost.setup();
        setupTab(new TextView(getContext()), "All", "Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2", "Summer 2018");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 1","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 2","Summer 2019");
        setupTab(new TextView(getContext()), "Tab 3","Summer 2019");

        barChart = (BarChart)view.findViewById(R.id.chart1);
        barChart.getDescription().setEnabled(false);

        setData(5);
        barChart.setFitBars(true);

    }

    private void setupTab(final View view, final String tag, String tag1) {
        View tabview = createTabView(mTabHost.getContext(), tag, tag1);
        TabHost.TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return view;
            }
        });
        mTabHost.addTab(setContent);
    }

    private static View createTabView(final Context context, final String text, final String text1) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        TextView tvsmall = (TextView) view.findViewById(R.id.tabsTextSmall);
        tvsmall.setText(text1);
        return view;
    }

    private void setData(int count) {
        ArrayList<BarEntry> mvalues = new ArrayList<>();
//        for(int i=0; i<count; i++){
//            float value = (float)(Math.random()*100);
//            mvalues.add(new BarEntry(i, (int)value));
//        }


//        mvalues.add(new BarEntry(1, 0, 0));
//        mvalues.add(new BarEntry(2, 5, 0));
        mvalues.add(new BarEntry(3, 14, 0));
        mvalues.add(new BarEntry(4, 15, 0));
//        mvalues.add(new BarEntry(5, 20, 0));
//        mvalues.add(new BarEntry(6, 25, 0));



        BarDataSet set = new BarDataSet(mvalues, "Data set");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);
        barChart.setData(data);
        barChart.invalidate();
        barChart.animateY(500);
    }

//    private void setData2(int count) {
//
//        ArrayList<BarEntry> mvalues1 = new ArrayList<>();
//        for(int i=0; i<count; i++){
//            float val1 = (float)(Math.random()*count) + 20;
//            float val2 = (float)(Math.random()*count) + 20;
//            // float val3 = (float)(Math.random()*count) + 20;
//            mvalues1.add(new BarEntry(i, new float[]{val1, val2, 0}));
//        }
//
//        BarDataSet set1;
//        set1 = new BarDataSet(mvalues1, "Statistics");
//        set1.setDrawValues(false);
//        set1.setStackLabels(new String[]{"sss", "ddd", "qq"});
//        set1.setColors(ColorTemplate.JOYFUL_COLORS);
//
//        BarData barData1 = new BarData(set1);
//        barData1.setValueFormatter(new MyValueFormatter());
//
//        barChart.setData(barData1);
//        barChart.setFitBars(true);
//        barChart.invalidate();
//        barChart.getDescription().setEnabled(false);
//
//    }

}
