package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseOfferSectionResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private SectionData data;

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

    public SectionData getData() {
        return data;
    }

    public void setData(SectionData data) {
        this.data = data;
    }
}
