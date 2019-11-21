package com.classtune.classtuneuni.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizObtained {
    @SerializedName("marks")
    @Expose
    private Integer marks;
    @SerializedName("quiz_title")
    @Expose
    private String quizTitle;
    @SerializedName("course_name")
    @Expose
    private String courseName;

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
