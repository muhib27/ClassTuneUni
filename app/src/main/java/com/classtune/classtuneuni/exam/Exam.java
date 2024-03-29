package com.classtune.classtuneuni.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exam {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("max_mark")
    @Expose
    private String maxMark;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("exam_id")
    @Expose
    private String examId;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("exam_date")
    @Expose
    private String examDate;
    @SerializedName("day_name")
    @Expose
    private String dayName;
    @SerializedName("start_time")
    @Expose
    private String startTime;
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

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(String maxMark) {
        this.maxMark = maxMark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
