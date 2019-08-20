package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedCorsesResponse {
    @SerializedName("data")
    @Expose
    private OfferedCorseData data;
    @SerializedName("status")
    @Expose
    private CommonStatus status;

    public OfferedCorseData getData() {
        return data;
    }

    public void setData(OfferedCorseData data) {
        this.data = data;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }
}
