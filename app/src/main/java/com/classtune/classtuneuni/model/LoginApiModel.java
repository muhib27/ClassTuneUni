package com.classtune.classtuneuni.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginApiModel {

    @SerializedName("status")
    @Expose
    private CommonStatus status;

    @SerializedName("data")
    @Expose
    private ApiKeyModel data;
    @SerializedName("course_count")
    @Expose
    private String courseCount;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public ApiKeyModel getData() {
        return data;
    }

    public void setData(ApiKeyModel data) {
        this.data = data;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }
}