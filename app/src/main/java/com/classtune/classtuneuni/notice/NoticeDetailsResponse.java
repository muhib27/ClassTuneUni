package com.classtune.classtuneuni.notice;


import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeDetailsResponse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private NoticeDetails data;


    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public NoticeDetails getData() {
        return data;
    }

    public void setData(NoticeDetails data) {
        this.data = data;
    }
}
