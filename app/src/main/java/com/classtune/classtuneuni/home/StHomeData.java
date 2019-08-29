package com.classtune.classtuneuni.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StHomeData {

    @SerializedName("news_feed")
    @Expose
    private List<StHomeFeed> newsFeed = null;
    @SerializedName("total_page")
    @Expose
    private Integer total_page;




    public List<StHomeFeed> getNewsFeed() {
        return newsFeed;
    }

    public void setNewsFeed(List<StHomeFeed> newsFeed) {
        this.newsFeed = newsFeed;
    }

    public Integer getTotal_page() {
        return total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }


}
