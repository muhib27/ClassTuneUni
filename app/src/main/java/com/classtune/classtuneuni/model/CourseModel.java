package com.classtune.classtuneuni.model;

public class CourseModel {
    private String examName;
    private String studentName;
    private String subject;
    private String examDate;
    private String obtainedMark;
    private String totalMarks;

    public CourseModel() {
    }

    public CourseModel(String examName, String studentName, String subject, String examDate, String obtainedMark, String totalMarks) {
        this.examName = examName;
        this.studentName = studentName;
        this.subject = subject;
        this.examDate = examDate;
        this.obtainedMark = obtainedMark;
        this.totalMarks = totalMarks;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public String getObtainedMark() {
        return obtainedMark;
    }

    public void setObtainedMark(String obtainedMark) {
        this.obtainedMark = obtainedMark;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }
}
