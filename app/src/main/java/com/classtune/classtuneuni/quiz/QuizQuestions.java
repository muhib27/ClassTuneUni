package com.classtune.classtuneuni.quiz;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizQuestions extends ViewModel {

    private final MutableLiveData<QuizQuestions> questionMutableLiveData = new MutableLiveData<QuizQuestions>();

    @SerializedName("quiz_id")
    @Expose
    private String quizId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("quiz_title")
    @Expose
    private String quizTitle;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("allow_retake")
    @Expose
    private String allowRetake;
    @SerializedName("retake_count")
    @Expose
    private String retakeCount;
    @SerializedName("review_previous")
    @Expose
    private String reviewPrevious;
    @SerializedName("total_marks")
    @Expose
    private Integer totalMarks;
    @SerializedName("obtained_marks")
    @Expose
    private String obtainedMarks;
    @SerializedName("retake_available")
    @Expose
    private String retakeAvailable;
    @SerializedName("attend_status")
    @Expose
    private String attendStatus;
    @SerializedName("participated_before")
    @Expose
    private String participatedBefore;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("taken_time")
    @Expose
    private String takenTime;

    public MutableLiveData<QuizQuestions> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    //    public void setQuestionMutableLiveData(MutableLiveData<Question> questions){
//        questionMutableLiveData.setValue(questions);
//    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getReviewPrevious() {
        return reviewPrevious;
    }

    public void setReviewPrevious(String reviewPrevious) {
        this.reviewPrevious = reviewPrevious;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setData(QuizQuestions quizQuestions) {
        questionMutableLiveData.setValue(quizQuestions);
    }

    public String getAllowRetake() {
        return allowRetake;
    }

    public void setAllowRetake(String allowRetake) {
        this.allowRetake = allowRetake;
    }

    public String getRetakeCount() {
        return retakeCount;
    }

    public void setRetakeCount(String retakeCount) {
        this.retakeCount = retakeCount;
    }

    public String getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(String obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public String getRetakeAvailable() {
        return retakeAvailable;
    }

    public void setRetakeAvailable(String retakeAvailable) {
        this.retakeAvailable = retakeAvailable;
    }

    public String getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(String attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getParticipatedBefore() {
        return participatedBefore;
    }

    public void setParticipatedBefore(String participatedBefore) {
        this.participatedBefore = participatedBefore;
    }

    public String getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(String takenTime) {
        this.takenTime = takenTime;
    }
}
