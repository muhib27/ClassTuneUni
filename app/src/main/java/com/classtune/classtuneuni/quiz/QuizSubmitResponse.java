package com.classtune.classtuneuni.quiz;

import com.classtune.classtuneuni.exam.ExamData;
import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizSubmitResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private QuizData quizData;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public QuizData getQuizData() {
        return quizData;
    }

    public void setQuizData(QuizData quizData) {
        this.quizData = quizData;
    }
}
