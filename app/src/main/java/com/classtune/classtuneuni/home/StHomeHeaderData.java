package com.classtune.classtuneuni.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StHomeHeaderData {
    @SerializedName("next_class")
    @Expose
    private StNextClass nextClass;
    @SerializedName("due_submission")
    @Expose
    private StDueSubmission dueSubmission;
    @SerializedName("attendance")
    @Expose
    private List<StHomeAttendance> attendance = null;

    public StNextClass getNextClass() {
        return nextClass;
    }

    public void setNextClass(StNextClass nextClass) {
        this.nextClass = nextClass;
    }

    public StDueSubmission getDueSubmission() {
        return dueSubmission;
    }

    public void setDueSubmission(StDueSubmission dueSubmission) {
        this.dueSubmission = dueSubmission;
    }

    public List<StHomeAttendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<StHomeAttendance> attendance) {
        this.attendance = attendance;
    }
}
