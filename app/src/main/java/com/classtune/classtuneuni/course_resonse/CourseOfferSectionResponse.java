package com.classtune.classtuneuni.course_resonse;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseOfferSectionResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private SectionData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public SectionData getData() {
        return data;
    }

    public void setData(SectionData data) {
        this.data = data;
    }
}
