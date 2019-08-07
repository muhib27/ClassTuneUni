package com.classtune.classtuneuni.course_resonse;

import com.classtune.classtuneuni.response.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OffereCourseData {
    @SerializedName("course_offer")
    @Expose
    private OfferedCourseDetails courseOffer;
    @SerializedName("sections")
    @Expose
    private List<OfferedCourseSection> sections = null;

    public OfferedCourseDetails getCourseOffer() {
        return courseOffer;
    }

    public void setCourseOffer(OfferedCourseDetails courseOffer) {
        this.courseOffer = courseOffer;
    }

    public List<OfferedCourseSection> getSections() {
        return sections;
    }

    public void setSections(List<OfferedCourseSection> sections) {
        this.sections = sections;
    }
}
