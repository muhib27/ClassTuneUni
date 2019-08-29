package com.classtune.classtuneuni.home;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StHomeHeaderRespons {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StHomeHeaderData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StHomeHeaderData getData() {
        return data;
    }

    public void setData(StHomeHeaderData data) {
        this.data = data;
    }
}
