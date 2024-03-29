//package com.classtune.classtuneuni.utils;
//
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.formatter.ValueFormatter;
//
//import java.text.DecimalFormat;
//
//public class MyValueFormatter extends ValueFormatter
//{
//
//    private DecimalFormat mFormat;
//    private String suffix;
//
//    public MyValueFormatter(String suffix) {
//        mFormat = new DecimalFormat("###,###,###,##0.0");
//        this.suffix = suffix;
//    }
//
//    public MyValueFormatter() {
//    }
//
//    @Override
//    public String getFormattedValue(float value) {
//        return mFormat.format(value) + suffix;
//    }
//
//    @Override
//    public String getAxisLabel(float value, AxisBase axis) {
//        if (axis instanceof XAxis) {
//            return mFormat.format(value);
//        } else if (value > 0) {
//            return mFormat.format(value) + suffix;
//        } else {
//            return mFormat.format(value);
//        }
//    }
//}
