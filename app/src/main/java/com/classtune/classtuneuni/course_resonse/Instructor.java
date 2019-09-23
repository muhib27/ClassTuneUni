package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instructor {
    @SerializedName("instructor_id")
    @Expose
    private String instructorId;
    @SerializedName("instructor")
    @Expose
    private String instructor;
    @SerializedName("instructor_image")
    @Expose
    private String instructorImage;

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getInstructorImage() {
        return instructorImage;
    }

    public void setInstructorImage(String instructorImage) {
        this.instructorImage = instructorImage;
    }
}
