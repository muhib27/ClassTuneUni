package com.classtune.classtuneuni.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("is_correct")
    @Expose
    private String isCorrect;
    @SerializedName("student_click")
    @Expose
    private Integer studentClick;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStudentClick() {
        return studentClick;
    }

    public void setStudentClick(Integer studentClick) {
        this.studentClick = studentClick;
    }
}