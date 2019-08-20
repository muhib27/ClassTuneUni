package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentSubmitedListResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private AssignmentSubmitData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public AssignmentSubmitData getData() {
        return data;
    }

    public void setData(AssignmentSubmitData data) {
        this.data = data;
    }
}
