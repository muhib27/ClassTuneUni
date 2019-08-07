package com.classtune.classtuneuni.notice;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticcCourseSection {

    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
