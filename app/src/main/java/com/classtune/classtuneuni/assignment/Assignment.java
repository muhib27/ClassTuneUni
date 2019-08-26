package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.model.AssignmentModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {
    @SerializedName("assignment")
    @Expose
    private AssignmentModel assignment;
    @SerializedName("students")
    @Expose
    private Integer students;
    @SerializedName("submissions")
    @Expose
    private Integer submissions;

    @SerializedName("submission")
    @Expose
    private Integer submission;
    @SerializedName("mark")
    @Expose
    private Integer mark;


    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public Integer getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Integer submissions) {
        this.submissions = submissions;
    }

    public AssignmentModel getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentModel assignment) {
        this.assignment = assignment;
    }

    public Integer getSubmission() {
        return submission;
    }

    public void setSubmission(Integer submission) {
        this.submission = submission;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
