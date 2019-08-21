package com.classtune.classtuneuni.attendance;

import com.classtune.classtuneuni.model.Student;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentListData {
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
}
