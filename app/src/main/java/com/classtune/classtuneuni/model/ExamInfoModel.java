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
}
