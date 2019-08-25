package com.classtune.classtuneuni.class_schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StClScheduleData {
    @SerializedName("routine")
    @Expose
    private List<Routine> routine = null;

    public List<Routine> getRoutine() {
        return routine;
    }

    public void setRoutine(List<Routine> routine) {
        this.routine = routine;
    }
}
