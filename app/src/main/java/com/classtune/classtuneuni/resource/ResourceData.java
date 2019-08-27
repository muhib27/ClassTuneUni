package com.classtune.classtuneuni.resource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourceData {
    @SerializedName("course_materials")
    @Expose
    private List<Resource> courseMaterials = null;
    @SerializedName("page")
    @Expose
    private String page;


    public List<Resource> getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(List<Resource> courseMaterials) {
        this.courseMaterials = courseMaterials;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
