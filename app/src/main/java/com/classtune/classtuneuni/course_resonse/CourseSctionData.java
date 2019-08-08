package com.classtune.classtuneuni.course_resonse;

import com.classtune.classtuneuni.model.Student;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseSctionData {
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("next_page")
    @Expose
    private Integer nextPage;
    @SerializedName("course")
    @Expose
    private SectionCourse sectionCourse;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public SectionCourse getSectionCourse() {
        return sectionCourse;
    }

    public void setSectionCourse(SectionCourse sectionCourse) {
        this.sectionCourse = sectionCourse;
    }
}
