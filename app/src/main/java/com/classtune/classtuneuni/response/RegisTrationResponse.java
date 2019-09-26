package com.classtune.classtuneuni.response;


import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisTrationResponse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;

    @SerializedName("data")
    @Expose
    private RegData data;
    @SerializedName("course_count")
    @Expose
    private Integer courseCount;

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

    public Integer getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(Integer courseCount) {
        this.courseCount = courseCount;
    }
}