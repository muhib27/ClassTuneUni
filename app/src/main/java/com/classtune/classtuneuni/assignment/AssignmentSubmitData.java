package com.classtune.classtuneuni.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentSubmitData {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("assign_date")
    @Expose
    private String assignDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("total_students")
    @Expose
    private Integer totalStudents;
    @SerializedName("total_submission")
    @Expose
    private Integer totalSubmission;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getTotalSubmission() {
        return totalSubmission;
    }

    public void setTotalSubmission(Integer totalSubmission) {
        this.totalSubmission = totalSubmission;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
