package com.classtune.classtuneuni.response;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeOfferData {

    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}