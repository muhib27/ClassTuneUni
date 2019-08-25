package com.classtune.classtuneuni.attendance;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAttendanceResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StAttendanceData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StAttendanceData getData() {
        return data;
    }

    public void setData(StAttendanceData data) {
        this.data = data;
    }
}
