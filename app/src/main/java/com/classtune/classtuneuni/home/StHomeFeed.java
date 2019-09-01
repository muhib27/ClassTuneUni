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
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @SerializedName("assignment_mark")
    @Expose
    private Integer assignmentMark;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("obtained_mark")
    @Expose
    private Integer obtainedMark;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("instructor_image")
    @Expose
    private String instructorImage;
    @SerializedName("feed_time")
    @Expose
    private String feedTime;
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
    private Integer examMark;
    @SerializedName("notice_id")
    @Expose
    private String noticeId;
    @SerializedName("notice_title")
    @Expose
    private String noticeTitle;
    @SerializedName("notice_descriptions")
    @Expose
    private String noticeDescriptions;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("attachments")
    @Expose
    private List<AssinmentAttachment> attachments = null;
    @SerializedName("material_id")
    @Expose
    private String materialId;
    @SerializedName("chapter_id")
    @Expose
    private String chapterId;
    @SerializedName("material_title")
    @Expose
    private String materialTitle;
    @SerializedName("material_content")
    @Expose
    private String materialContent;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("chapter_title")
    @Expose
    private String chapterTitle;

    public StHomeFeed(Integer contentType) {
        this.contentType = contentType;
    }

    public StHomeFeed() {
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
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

    public String getInstructorImage() {
        return instructorImage;
    }

    public void setInstructorImage(String instructorImage) {
        this.instructorImage = instructorImage;
    }

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public String getMaterialContent() {
        return materialContent;
    }

    public void setMaterialContent(String materialContent) {
        this.materialContent = materialContent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public List<AssinmentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AssinmentAttachment> attachments) {
        this.attachments = attachments;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAssignmentMark() {
        return assignmentMark;
    }

    public void setAssignmentMark(Integer assignmentMark) {
        this.assignmentMark = assignmentMark;
    }

    public Integer getObtainedMark() {
        return obtainedMark;
    }

    public void setObtainedMark(Integer obtainedMark) {
        this.obtainedMark = obtainedMark;
    }

    public Integer getExamMark() {
        return examMark;
    }

    public void setExamMark(Integer examMark) {
        this.examMark = examMark;
    }
}
