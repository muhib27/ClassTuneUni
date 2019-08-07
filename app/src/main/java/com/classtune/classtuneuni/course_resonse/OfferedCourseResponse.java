package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedCourseResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private OffereCourseData data;

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

    public OffereCourseData getData() {
        return data;
    }

    public void setData(OffereCourseData data) {
        this.data = data;
    }
}
