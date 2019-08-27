package com.classtune.classtuneuni.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StMsgData {
    @SerializedName("course_discussion")
    @Expose
    private List<StCourseDiscussion> courseDiscussion = null;
    @SerializedName("page")
    @Expose
    private String page;

    public List<StCourseDiscussion> getCourseDiscussion() {
        return courseDiscussion;
    }

    public void setCourseDiscussion(List<StCourseDiscussion> courseDiscussion) {
        this.courseDiscussion = courseDiscussion;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
