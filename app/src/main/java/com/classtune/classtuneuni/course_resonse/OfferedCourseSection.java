package com.classtune.classtuneuni.course_resonse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferedCourseSection {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("en_code")
    @Expose
    private String enCode;
    @SerializedName("course_offer_sections_id")
    @Expose
    private String courseOfferSectionsId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String getCourseOfferSectionsId() {
        return courseOfferSectionsId;
    }

    public void setCourseOfferSectionsId(String courseOfferSectionsId) {
        this.courseOfferSectionsId = courseOfferSectionsId;
    }
}
