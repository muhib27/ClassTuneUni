package com.classtune.classtuneuni.home;

import com.classtune.classtuneuni.assignment.AssinmentAttachment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StHomeFeed {
    @SerializedName("content_type")
    @Expose
    private Integer contentType;
    @SerializedName("content_name")
    @Expose
    private String contentName;
    @SerializedName("is_read")
    @Expose
    private String isRead;
    @SerializedName("obtained_mark")
    @Expose
    private String obtainedMark;
    @SerializedName("obtained_grade")
    @Expose
    private String obtainedGrade;
    @SerializedName("obtained_point")
    @Expose
    private String obtainedPoint;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("exam_id")
    @Expose
    private String examId;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("exam_date")
    @Expose
    private String examDate;
    @SerializedName("exam_mark")
    @Expose
    private String examMark;
    @SerializedName("notice_id")
    @Expose
    private String noticeId;
    @SerializedName("notice_title")
    @Expose
    private String noticeTitle;
    @SerializedName("notice_descriptions")
    @Expose
    private String noticeDescriptions;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attachments")
    @Expose
    private List<AssinmentAttachment> attachments = null;

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getObtainedMark() {
        return obtainedMark;
    }

    public void setObtainedMark(String obtainedMark) {
        this.obtainedMark = obtainedMark;
    }

    public String getObtainedGrade() {
        return obtainedGrade;
    }

    public void setObtainedGrade(String obtainedGrade) {
        this.obtainedGrade = obtainedGrade;
    }

    public String getObtainedPoint() {
        return obtainedPoint;
    }

    public void setObtainedPoint(String obtainedPoint) {
        this.obtainedPoint = obtainedPoint;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getExamMark() {
        return examMark;
    }

    public void setExamMark(String examMark) {
        this.examMark = examMark;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDescriptions() {
        return noticeDescriptions;
    }

    public void setNoticeDescriptions(String noticeDescriptions) {
        this.noticeDescriptions = noticeDescriptions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AssinmentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AssinmentAttachment> attachments) {
        this.attachments = attachments;
    }

    public StHomeFeed(int contentType) {
        this.contentType = contentType;
    }

    public StHomeFeed() {
    }
}
