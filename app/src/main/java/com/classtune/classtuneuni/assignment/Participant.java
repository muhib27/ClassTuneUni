package com.classtune.classtuneuni.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participant {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("submission_id")
    @Expose
    private String submissionId;
    @SerializedName("submission_date")
    @Expose
    private String submissionDate;
    @SerializedName("mark")
    @Expose
    private String mark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
