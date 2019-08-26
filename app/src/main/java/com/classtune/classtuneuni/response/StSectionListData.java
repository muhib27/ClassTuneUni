package com.classtune.classtuneuni.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StSectionListData {
    @SerializedName("course_section")
    @Expose
    private List<StCourseSection> courseSection = null;

    public List<StCourseSection> getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(List<StCourseSection> courseSection) {
        this.courseSection = courseSection;
    }
}
