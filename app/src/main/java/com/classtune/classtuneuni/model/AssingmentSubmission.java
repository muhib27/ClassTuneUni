package com.classtune.classtuneuni.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssingmentSubmission {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private SubmissionData submissionData;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public SubmissionData getSubmissionData() {
        return submissionData;
    }

    public void setSubmissionData(SubmissionData submissionData) {
        this.submissionData = submissionData;
    }
}
