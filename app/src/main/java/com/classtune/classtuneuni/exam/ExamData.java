package com.classtune.classtuneuni.exam;

import android.arch.lifecycle.ViewModel;

import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.model.Quiz;
import com.classtune.classtuneuni.quiz.QuizQuestions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExamData {
    @SerializedName("exams")
    @Expose
    private List<ExamInfoModel> exams = null;

    @SerializedName("quiz")
    @Expose
    private List<Quiz> quiz = null;

    @SerializedName("quiz_questions")
    @Expose
    private QuizQuestions quizQuestions;

    public List<ExamInfoModel> getExams() {
        return exams;
    }

    public void setExams(List<ExamInfoModel> exams) {
        this.exams = exams;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public QuizQuestions getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(QuizQuestions quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
}
