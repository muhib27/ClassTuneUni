package com.classtune.classtuneuni.model;

public class AssignmentModel {
    public String title;
    public String name;
    public String subject;
    public String status;
    public String assignDate;
    public String dueDate;

    public AssignmentModel() {
    }

    public AssignmentModel(String title, String name, String subject, String status, String assignDate, String dueDate) {
        this.title = title;
        this.name = name;
        this.subject = subject;
        this.status = status;
        this.assignDate = assignDate;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
