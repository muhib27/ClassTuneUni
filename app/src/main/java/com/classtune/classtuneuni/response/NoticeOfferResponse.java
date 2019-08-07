package com.classtune.classtuneuni.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeOfferResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NoticeOfferData noticeOfferData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NoticeOfferData getNoticeOfferData() {
        return noticeOfferData;
    }

    public void setNoticeOfferData(NoticeOfferData noticeOfferData) {
        this.noticeOfferData = noticeOfferData;
    }
}
