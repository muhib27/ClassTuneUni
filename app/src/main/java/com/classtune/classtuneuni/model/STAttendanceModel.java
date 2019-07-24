package com.classtune.classtuneuni.model;

public class STAttendanceModel {
    private String roll;
    private String name;
    private String status;

    public STAttendanceModel() {
    }

    public STAttendanceModel(String roll, String name, String status) {
        this.roll = roll;
        this.name = name;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
