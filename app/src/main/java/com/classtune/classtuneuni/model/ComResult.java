package com.classtune.classtuneuni.model;

public class ComResult {
    private String subject;
    private String gradePoint;
    private String grade;

    public ComResult() {
    }

    public ComResult(String subject, String gradePoint, String grade) {
        this.subject = subject;
        this.gradePoint = gradePoint;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(String gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
