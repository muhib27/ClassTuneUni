package com.classtune.classtuneuni.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StCourseAssessment {


//    @SerializedName("obtained")
//    @Expose
//    private List<StObtained> obtained = null;

    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("attendance")
    @Expose
    private Integer attendance;
//    @SerializedName("obtained")
//    @Expose
//    private StObtained obtained;

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

//    public List<StObtained> getObtained() {
//        return obtained;
//    }
//
//    public void setObtained(List<StObtained> obtained) {
//        this.obtained = obtained;
//    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }
}
