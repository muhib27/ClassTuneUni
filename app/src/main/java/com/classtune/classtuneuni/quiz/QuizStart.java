package com.classtune.classtuneuni.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizStart {
    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("start_time")
    @Expose
    private String startTime;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
