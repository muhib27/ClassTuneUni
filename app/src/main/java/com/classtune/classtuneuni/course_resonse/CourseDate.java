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
    @SerializedName("total_courses")
    @Expose
    private Integer totalCourses;
    @SerializedName("related_courses")
    @Expose
    private List<RelatedCourse> relatedCourses = null;

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
}
