package com.classtune.classtuneuni.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Policy {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("percentage")
    @Expose
    private String percentage;
    @SerializedName("best_count")
    @Expose
    private String bestCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("for_exam")
    @Expose
    private String forExam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getBestCount() {
        return bestCount;
    }

    public void setBestCount(String bestCount) {
        this.bestCount = bestCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForExam() {
        return forExam;
    }

    public void setForExam(String forExam) {
        this.forExam = forExam;
    }


    public Policy(String percentage, String name) {
        this.percentage = percentage;
        this.name = name;
    }

    public Policy() {
    }
}
