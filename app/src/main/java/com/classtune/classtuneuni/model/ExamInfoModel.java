package com.classtune.classtuneuni.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamInfoModel {
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
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("obtained_mark")
    @Expose
    private String obtainedMark;

    @SerializedName("assessment_id")
    @Expose
    private String assessmentId;

    @SerializedName("assessment_name")
    @Expose
    private String assessmentName;

    @SerializedName("published")
    @Expose
    private String published;

    @SerializedName("total_students")
    @Expose
    private String totalStudents;
    @SerializedName("participants")
    @Expose
    private String participants;



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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
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

    public String getObtainedMark() {
        return obtainedMark;
    }

    public void setObtainedMark(String obtainedMark) {
        this.obtainedMark = obtainedMark;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }


}
