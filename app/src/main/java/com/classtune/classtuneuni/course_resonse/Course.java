package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("credit_point")
    @Expose
    private String creditPoint;
    @SerializedName("course_offered")
    @Expose
    private Boolean courseOffered;

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("short_details")
    @Expose
    private String shortDetails;
    @SerializedName("details")
    @Expose
    private Object details;
    @SerializedName("prereq")
    @Expose
    private String prereq;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("interested")
    @Expose
    private String interested;
    @SerializedName("enrolled_students")
    @Expose
    private String enrolledStudents;
    @SerializedName("new_course")
    @Expose
    private String newCourse;


    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("enroll_date")
    @Expose
    private String enrollDate;


    @SerializedName("instructor_id")
    @Expose
    private String instructorId;

    @SerializedName("instructor_image")
    @Expose
    private String instructorImage;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(String creditPoint) {
        this.creditPoint = creditPoint;
    }

    public Boolean getCourseOffered() {
        return courseOffered;
    }

    public void setCourseOffered(Boolean courseOffered) {
        this.courseOffered = courseOffered;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(String shortDetails) {
        this.shortDetails = shortDetails;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getPrereq() {
        return prereq;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
