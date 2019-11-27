package com.classtune.classtuneuni.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Submission {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("submission_count")
    @Expose
    private String submissionCount;
    @SerializedName("is_editable")
    @Expose
    private String isEditable;
    @SerializedName("is_deletable")
    @Expose
    private String isDeletable;
    @SerializedName("permission_status")
    @Expose
    private String permissionStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(String submissionCount) {
        this.submissionCount = submissionCount;
    }

    public String getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(String isEditable) {
        this.isEditable = isEditable;
    }

    public String getIsDeletable() {
        return isDeletable;
    }

    public void setIsDeletable(String isDeletable) {
        this.isDeletable = isDeletable;
    }

    public String getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(String permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
