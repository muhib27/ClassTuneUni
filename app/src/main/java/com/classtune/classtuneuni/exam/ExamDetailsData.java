package com.classtune.classtuneuni.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamDetailsData {
    @SerializedName("exam")
    @Expose
    private Exam exam;
    @SerializedName("exam_min_avg_max")
    @Expose
    private ExamMinAvgMax examMinAvgMax;
    @SerializedName("mark")
    @Expose
    private Mark mark;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public ExamMinAvgMax getExamMinAvgMax() {
        return examMinAvgMax;
    }

    public void setExamMinAvgMax(ExamMinAvgMax examMinAvgMax) {
        this.examMinAvgMax = examMinAvgMax;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
