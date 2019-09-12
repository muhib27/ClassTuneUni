package com.classtune.classtuneuni.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StMsgData {
    @SerializedName("course_discussion")
    @Expose
    private List<StCourseDiscussion> courseDiscussion = null;
    @SerializedName("total_page")
    @Expose
    private Integer total_page;

    public List<StCourseDiscussion> getCourseDiscussion() {
        return courseDiscussion;
    }

    public void setCourseDiscussion(List<StCourseDiscussion> courseDiscussion) {
        this.courseDiscussion = courseDiscussion;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }
}
