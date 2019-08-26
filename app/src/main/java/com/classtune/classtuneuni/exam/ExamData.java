package com.classtune.classtuneuni.exam;

import com.classtune.classtuneuni.model.ExamInfoModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamData {
    @SerializedName("exams")
    @Expose
    private List<ExamInfoModel> exams = null;

    public List<ExamInfoModel> getExams() {
        return exams;
    }

    public void setExams(List<ExamInfoModel> exams) {
        this.exams = exams;
    }
}
