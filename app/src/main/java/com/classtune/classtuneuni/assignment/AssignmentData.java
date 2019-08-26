package com.classtune.classtuneuni.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignmentData {
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("assignment")
    @Expose
    private Assignment assignment;

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
}
