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
    @SerializedName("total_page")
    @Expose
    private int totalPage;

    @SerializedName("course_material")
    @Expose
    private Resource courseMaterial;


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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Resource getCourseMaterial() {
        return courseMaterial;
    }

    public void setCourseMaterial(Resource courseMaterial) {
        this.courseMaterial = courseMaterial;
    }
}
