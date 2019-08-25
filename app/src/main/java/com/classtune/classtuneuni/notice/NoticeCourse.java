package com.classtune.classtuneuni.notice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeCourse {
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
}
