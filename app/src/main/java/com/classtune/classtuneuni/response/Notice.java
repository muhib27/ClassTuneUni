package com.classtune.classtuneuni.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notice {
    @SerializedName("notice")
    @Expose
    private NoticeInfo notice;
    @SerializedName("course")
    @Expose
    private List<List<CourseSection>> course = null;

    public NoticeInfo getNotice() {
        return notice;
    }

    public List<List<CourseSection>> getCourse() {
        return course;
    }

    public void setCourse(List<List<CourseSection>> course) {
        this.course = course;
    }

    public void setNotice(NoticeInfo notice) {
        this.notice = notice;
    }



}