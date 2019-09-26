package com.classtune.classtuneuni.exam;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamDetailsResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private ExamDetailsData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public ExamDetailsData getData() {
        return data;
    }

    public void setData(ExamDetailsData data) {
        this.data = data;
    }
}
