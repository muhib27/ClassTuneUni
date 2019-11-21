package com.classtune.classtuneuni.quiz;

import com.classtune.classtuneuni.exam.ExamData;
import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizStartResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private QuizStart quizStart;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public QuizStart getQuizStart() {
        return quizStart;
    }

    public void setQuizStart(QuizStart quizStart) {
        this.quizStart = quizStart;
    }
}
