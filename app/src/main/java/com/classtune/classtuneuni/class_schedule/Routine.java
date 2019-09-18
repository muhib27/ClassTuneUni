package com.classtune.classtuneuni.class_schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Routine {
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;


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
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("short_details")
    @Expose
    private Object shortDetails;
    @SerializedName("pre_req")
    @Expose
    private Object preReq;
    @SerializedName("thumbnail")
    @Expose
    private Object thumbnail;
    @SerializedName("details")
    @Expose
    private Object details;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("s_name")
    @Expose
    private String sName;




    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getShortDetails() {
        return shortDetails;
    }

    public void setShortDetails(Object shortDetails) {
        this.shortDetails = shortDetails;
    }

    public Object getPreReq() {
        return preReq;
    }

    public void setPreReq(Object preReq) {
        this.preReq = preReq;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
