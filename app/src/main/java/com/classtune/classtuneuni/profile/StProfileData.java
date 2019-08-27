package com.classtune.classtuneuni.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StProfileData {
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("enrolled_course")
    @Expose
    private Integer enrolledCourse;
    @SerializedName("completed_course")
    @Expose
    private Integer completedCourse;
    @SerializedName("course_assessment")
    @Expose
    private List<StCourseAssessment> courseAssessment = null;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setEnrolledCourse(Integer enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public Integer getCompletedCourse() {
        return completedCourse;
    }

    public void setCompletedCourse(Integer completedCourse) {
        this.completedCourse = completedCourse;
    }


    public List<StCourseAssessment> getCourseAssessment() {
        return courseAssessment;
    }

    public void setCourseAssessment(List<StCourseAssessment> courseAssessment) {
        this.courseAssessment = courseAssessment;
    }
}
