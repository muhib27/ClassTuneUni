package com.classtune.classtuneuni.model;

public class ClassScheduleModel {
    private String day;
    private String date;
    private String monthYear;
    private String time;

    public ClassScheduleModel() {
    }

    public ClassScheduleModel(String day, String date, String monthYear, String time) {
        this.day = day;
        this.date = date;
        this.monthYear = monthYear;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
