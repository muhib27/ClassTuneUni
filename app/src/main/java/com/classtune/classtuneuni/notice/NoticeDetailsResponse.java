package com.classtune.classtuneuni.notice;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeDetailsResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NoticeDetails data;

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

    public NoticeDetails getData() {
        return data;
    }

    public void setData(NoticeDetails data) {
        this.data = data;
    }
}
