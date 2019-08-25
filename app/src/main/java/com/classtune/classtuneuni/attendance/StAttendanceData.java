package com.classtune.classtuneuni.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StAttendanceData {
    @SerializedName("total_class")
    @Expose
    private Integer totalClass;
    @SerializedName("absent")
    @Expose
    private Integer absent;
    @SerializedName("present")
    @Expose
    private Integer present;
    @SerializedName("percentage")
    @Expose
    private Integer percentage;

    public Integer getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(Integer totalClass) {
        this.totalClass = totalClass;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getPresent() {
        return present;
    }

    public void setPresent(Integer present) {
        this.present = present;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
