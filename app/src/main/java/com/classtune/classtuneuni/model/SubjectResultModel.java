package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.exam.Exam;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectResultModel {
    @SerializedName("assessment")
    @Expose
    private String assessment;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("obtained")
    @Expose
    private Double obtained;

    @SerializedName("exams")
    @Expose
    private List<Exam> exams = null;

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getObtained() {
        return obtained;
    }

    public void setObtained(Double obtained) {
        this.obtained = obtained;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
}
