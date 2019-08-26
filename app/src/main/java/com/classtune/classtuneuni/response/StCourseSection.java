package com.classtune.classtuneuni.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StCourseSection {
    @SerializedName("course_offer_section_id")
    @Expose
    private String courseOfferSectionId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("instructor")
    @Expose
    private String instructor;

    public String getCourseOfferSectionId() {
        return courseOfferSectionId;
    }

    public void setCourseOfferSectionId(String courseOfferSectionId) {
        this.courseOfferSectionId = courseOfferSectionId;
    }

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
}
