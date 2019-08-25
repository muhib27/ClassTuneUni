package com.classtune.classtuneuni.notice;

import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StNoticeData {

    @SerializedName("notices")
    @Expose
    private List<NoticeInfo> notices = null;
    @SerializedName("next_page")
    @Expose
    private Integer nextPage;

    public List<NoticeInfo> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeInfo> notices) {
        this.notices = notices;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }
}