package com.classtune.classtuneuni.response;


import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeOfferResponse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;;
    @SerializedName("data")
    @Expose
    private NoticeOfferData noticeOfferData;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public NoticeOfferData getNoticeOfferData() {
        return noticeOfferData;
    }

    public void setNoticeOfferData(NoticeOfferData noticeOfferData) {
        this.noticeOfferData = noticeOfferData;
    }
}
