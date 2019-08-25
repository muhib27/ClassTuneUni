package com.classtune.classtuneuni.class_schedule;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StClsScheduleResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StClScheduleData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StClScheduleData getData() {
        return data;
    }

    public void setData(StClScheduleData data) {
        this.data = data;
    }
}
