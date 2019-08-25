package com.classtune.classtuneuni.notice;

import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.response.NoticeData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StNoticeResonse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StNoticeData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StNoticeData getData() {
        return data;
    }

    public void setData(StNoticeData data) {
        this.data = data;
    }
}
