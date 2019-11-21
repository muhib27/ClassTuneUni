package com.classtune.classtuneuni.quiz;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question extends ViewModel {

    public Question() {
    }

    private final MutableLiveData<List<Question>> quizQuestions = new MutableLiveData<List<Question>>();

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("question_explanation")
    @Expose
    private String questionExplanation;
    @SerializedName("mark_count")
    @Expose
    private String markCount;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;
    @SerializedName("correct")
    @Expose
    private Integer correct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMarkCount() {
        return markCount;
    }

    public void setMarkCount(String markCount) {
        this.markCount = markCount;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public MutableLiveData<List<Question>> getQuestionMutableLiveData() {
        return quizQuestions;
    }
    public void setListData(List<Question> questionList) {
        quizQuestions.setValue(questionList);
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public String getQuestionExplanation() {
        return questionExplanation;
    }

    public void setQuestionExplanation(String questionExplanation) {
        this.questionExplanation = questionExplanation;
    }
}