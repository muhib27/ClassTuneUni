package com.classtune.classtuneuni.notice;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeDetails {

    @SerializedName("notices")
    @Expose
    private Notices notices;
    @SerializedName("course_section")
    @Expose
    private List<NoticcCourseSection> courseSection = null;

    public Notices getNotices() {
        return notices;
    }

    public void setNotices(Notices notices) {
        this.notices = notices;
    }

    public List<NoticcCourseSection> getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(List<NoticcCourseSection> courseSection) {
        this.courseSection = courseSection;
    }
}