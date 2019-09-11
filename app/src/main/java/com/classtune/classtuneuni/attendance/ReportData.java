package com.classtune.classtuneuni.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportData {
    @SerializedName("total_class")
    @Expose
    private Integer totalClass;
    @SerializedName("attendance")
    @Expose
    private List<StAttendanceData> attendance = null;

    public Integer getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Integer totalClass) {
        this.totalClass = totalClass;
    }

    public List<StAttendanceData> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<StAttendanceData> attendance) {
        this.attendance = attendance;
    }
}
