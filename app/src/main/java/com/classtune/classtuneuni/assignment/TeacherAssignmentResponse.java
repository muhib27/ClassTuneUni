package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeacherAssignmentResponse {

    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private AssignmentData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public AssignmentData getData() {
        return data;
    }

    public void setData(AssignmentData data) {
        this.data = data;
    }
}
