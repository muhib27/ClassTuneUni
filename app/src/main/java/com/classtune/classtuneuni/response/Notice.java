package com.classtune.classtuneuni.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notice {
    @SerializedName("notice")
    @Expose
    private NoticeInfo notice;
    @SerializedName("courses")
    @Expose
    private List<String> courses = null;

    public NoticeInfo getNotice() {
        return notice;
    }


    public void setNotice(NoticeInfo notice) {
        this.notice = notice;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}