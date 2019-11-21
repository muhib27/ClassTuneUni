package com.classtune.classtuneuni.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizData {
    @SerializedName("data")
    @Expose
    private QuizObtained data;

    public QuizObtained getData() {
        return data;
    }

    public void setData(QuizObtained data) {
        this.data = data;
    }
}
