package com.classtune.classtuneuni.result;

import com.classtune.classtuneuni.model.SubjectResultModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseResultData {
    @SerializedName("assessments")
    @Expose
    private List<SubjectResultModel> assessments = null;
    @SerializedName("total_marks")
    @Expose
    private Double totalMarks;
    @SerializedName("grade")
    @Expose
    private Grade grade;

    public List<SubjectResultModel> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<SubjectResultModel> assessments) {
        this.assessments = assessments;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
