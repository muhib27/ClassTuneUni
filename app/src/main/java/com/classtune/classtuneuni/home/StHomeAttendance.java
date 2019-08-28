package com.classtune.classtuneuni.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StHomeAttendance {
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("instructor")
    @Expose
    private String instructor;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

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
