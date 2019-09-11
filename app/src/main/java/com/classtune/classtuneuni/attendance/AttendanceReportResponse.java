package com.classtune.classtuneuni.attendance;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceReportResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private ReportData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public ReportData getData() {
        return data;
    }

    public void setData(ReportData data) {
        this.data = data;
    }
}
