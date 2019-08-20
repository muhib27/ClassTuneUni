package com.classtune.classtuneuni.course_resonse;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedCourseResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private OffereCourseData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public OffereCourseData getData() {
        return data;
    }

    public void setData(OffereCourseData data) {
        this.data = data;
    }
}
