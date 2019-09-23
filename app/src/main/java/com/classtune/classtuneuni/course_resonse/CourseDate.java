package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseDate {
    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_page")
    @Expose
    private Integer totalPage;
    @SerializedName("course")
    @Expose
    private Course course;
    @SerializedName("enrolled_students")
    @Expose
    private String enrolledStudents;
    @SerializedName("new_course")
    @Expose
    private String newCourse;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("interested")
    @Expose
    private Integer interested;
    @SerializedName("share_url")
    @Expose
    private String shareUrl;
    @SerializedName("interested_message")
    @Expose
    private String interestedMessage;
    @SerializedName("allready_enrolled")
    @Expose
    private String allreadyEnrolled;
    @SerializedName("total_resources")
    @Expose
    private Integer totalResources;
    @SerializedName("total_courses")
    @Expose
    private Integer totalCourses;
    @SerializedName("related_courses")
    @Expose
    private List<RelatedCourse> relatedCourses = null;

    @SerializedName("instructor")
    @Expose
    private Instructor instructor;

    @SerializedName("current_date")
    @Expose
    private String currentDate;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(String enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public String getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(String newCourse) {
        this.newCourse = newCourse;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getInterested() {
        return interested;
    }

    public void setInterested(Integer interested) {
        this.interested = interested;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Integer getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }

    public List<RelatedCourse> getRelatedCourses() {
        return relatedCourses;
    }

    public void setRelatedCourses(List<RelatedCourse> relatedCourses) {
        this.relatedCourses = relatedCourses;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getInterestedMessage() {
        return interestedMessage;
    }

    public void setInterestedMessage(String interestedMessage) {
        this.interestedMessage = interestedMessage;
    }

    public String getAllreadyEnrolled() {
        return allreadyEnrolled;
    }

    public void setAllreadyEnrolled(String allreadyEnrolled) {
        this.allreadyEnrolled = allreadyEnrolled;
    }

    public Integer getTotalResources() {
        return totalResources;
    }

    public void setTotalResources(Integer totalResources) {
        this.totalResources = totalResources;
    }
}
