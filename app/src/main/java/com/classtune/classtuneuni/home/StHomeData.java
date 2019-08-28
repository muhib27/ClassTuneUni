package com.classtune.classtuneuni.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StHomeData {
    @SerializedName("attendance")
    @Expose
    private List<StHomeAttendance> attendance = null;
    @SerializedName("news_feed")
    @Expose
    private List<StHomeFeed> newsFeed = null;
    @SerializedName("page")
    @Expose
    private Object page;

    public List<StHomeAttendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<StHomeAttendance> attendance) {
        this.attendance = attendance;
    }

    public List<StHomeFeed> getNewsFeed() {
        return newsFeed;
    }

    public void setNewsFeed(List<StHomeFeed> newsFeed) {
        this.newsFeed = newsFeed;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
