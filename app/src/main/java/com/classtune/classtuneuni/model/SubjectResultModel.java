package com.classtune.classtuneuni.model;

public class SubjectResultModel {
    private String assessmentName;
    private String weight;
    private String obtained;

    public SubjectResultModel() {
    }

    public SubjectResultModel(String assessmentName, String weight, String obtained) {
        this.assessmentName = assessmentName;
        this.weight = weight;
        this.obtained = obtained;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getObtained() {
        return obtained;
    }

    public void setObtained(String obtained) {
        this.obtained = obtained;
    }
}
