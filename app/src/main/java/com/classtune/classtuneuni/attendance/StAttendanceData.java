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
    private Double percentage;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("name")
    @Expose
    private String name;

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

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
