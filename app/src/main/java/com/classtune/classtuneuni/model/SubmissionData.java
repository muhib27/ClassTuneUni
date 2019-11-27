package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubmissionData {
    @SerializedName("submission")
    @Expose
    private Submission submission;

    @SerializedName("attachments")
    @Expose
    private List<AssinmentAttachment> attachments = null;

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public List<AssinmentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AssinmentAttachment> attachments) {
        this.attachments = attachments;
    }
}
