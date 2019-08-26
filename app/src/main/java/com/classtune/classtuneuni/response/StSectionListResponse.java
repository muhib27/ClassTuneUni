package com.classtune.classtuneuni.response;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StSectionListResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StSectionListData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StSectionListData getData() {
        return data;
    }

    public void setData(StSectionListData data) {
        this.data = data;
    }
}
