package com.classtune.classtuneuni.response;


import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisTrationResponse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;

    @SerializedName("error_message")
    @Expose
    private String errorMessage;

    @SerializedName("data")
    @Expose
    private RegData data;
    @SerializedName("course_count")
    @Expose
    private String courseCount;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public RegData getData() {
        return data;
    }

    public void setData(RegData data) {
        this.data = data;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}