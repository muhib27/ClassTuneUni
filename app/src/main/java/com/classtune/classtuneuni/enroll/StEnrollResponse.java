package com.classtune.classtuneuni.enroll;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StEnrollResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StEnrollData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StEnrollData getData() {
        return data;
    }

    public void setData(StEnrollData data) {
        this.data = data;
    }
}
