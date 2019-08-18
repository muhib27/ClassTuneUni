package com.classtune.classtuneuni.course_resonse;

import com.classtune.classtuneuni.response.Section;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionData {
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
