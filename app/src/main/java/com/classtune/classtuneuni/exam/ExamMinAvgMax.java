package com.classtune.classtuneuni.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamMinAvgMax {

    @SerializedName("avg_mark")
    @Expose
    private String avgMark;
    @SerializedName("max_mark")
    @Expose
    private String maxMark;
    @SerializedName("min_mark")
    @Expose
    private String minMark;

    public String getAvgMark() {
        return avgMark;
    }

    public void setAvgMark(String avgMark) {
        this.avgMark = avgMark;
    }

    public String getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(String maxMark) {
        this.maxMark = maxMark;
    }

    public String getMinMark() {
        return minMark;
    }

    public void setMinMark(String minMark) {
        this.minMark = minMark;
    }

}