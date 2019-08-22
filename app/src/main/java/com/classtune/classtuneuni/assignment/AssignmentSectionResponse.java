package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.course_resonse.SectionData;
import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentSectionResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private AssignSectionData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public AssignSectionData getData() {
        return data;
    }

    public void setData(AssignSectionData data) {
        this.data = data;
    }
}
