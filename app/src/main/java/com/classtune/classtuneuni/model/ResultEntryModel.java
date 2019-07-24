package com.classtune.classtuneuni.model;

public class ResultEntryModel {
    private String roll;
    private String name;
    private String marks;

    public ResultEntryModel() {
    }

    public ResultEntryModel(String roll, String name, String marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
