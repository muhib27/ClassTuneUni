package com.classtune.classtuneuni.model;

public class ProfileCourseModel {
    private String subject;
    private String attendance;
    private String result;

    public ProfileCourseModel() {
    }

    public ProfileCourseModel(String subject, String attendance, String result) {
        this.subject = subject;
        this.attendance = attendance;
        this.result = result;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
