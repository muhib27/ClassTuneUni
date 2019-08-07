package com.classtune.classtuneuni.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeResonse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NoticeData data;

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

    public NoticeData getData() {
        return data;
    }

    public void setData(NoticeData data) {
        this.data = data;
    }
}
