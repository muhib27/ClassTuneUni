package com.classtune.classtuneuni.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignSectionData {
    @SerializedName("sections")
    @Expose
    private List<AssignmentSection> sections = null;

    public List<AssignmentSection> getSections() {
        return sections;
    }

    public void setSections(List<AssignmentSection> sections) {
        this.sections = sections;
    }
}
