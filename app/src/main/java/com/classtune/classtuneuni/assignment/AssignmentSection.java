package com.classtune.classtuneuni.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentSection {
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("offered_section_id")
    @Expose
    private String offeredSectionId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getOfferedSectionId() {
        return offeredSectionId;
    }

    public void setOfferedSectionId(String offeredSectionId) {
        this.offeredSectionId = offeredSectionId;
    }

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
}
