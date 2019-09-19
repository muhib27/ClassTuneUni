package com.classtune.classtuneuni.home;

import com.classtune.classtuneuni.assignment.Assignment;
import com.classtune.classtuneuni.class_schedule.Routine;
import com.classtune.classtuneuni.message.StSendMsgData;
import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.model.ExamInfoModel;
import com.classtune.classtuneuni.notice.Notices;
import com.classtune.classtuneuni.resource.Resource;
import com.classtune.classtuneuni.response.Notice;
import com.classtune.classtuneuni.response.NoticeInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StHomeRespons {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("resource_single")
    @Expose
    private Resource resourceSingle;
    @SerializedName("notice")
    @Expose
    private List<Notices> notice = null;
    @SerializedName("next_class")
    @Expose
    private Routine nextClass;
    @SerializedName("weekday")
    @Expose
    private String weekday;
    @SerializedName("current_time")
    @Expose
    private String CurrentTime;

    @SerializedName("notices")
    @Expose
    private List<NoticeInfo> notices = null;
    @SerializedName("resources")
    @Expose
    private List<Resource> resources = null;
    @SerializedName("exam")
    @Expose
    private ExamInfoModel exam;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public Resource getResourceSingle() {
        return resourceSingle;
    }

    public void setResourceSingle(Resource resourceSingle) {
        this.resourceSingle = resourceSingle;
    }

    public List<Notices> getNotice() {
        return notice;
    }

    public void setNotice(List<Notices> notice) {
        this.notice = notice;
    }

    public Routine getNextClass() {
        return nextClass;
    }

    public void setNextClass(Routine nextClass) {
        this.nextClass = nextClass;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public List<NoticeInfo> getNotices() {
        return notices;
    }

    public void setNotices(List<NoticeInfo> notices) {
        this.notices = notices;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public ExamInfoModel getExam() {
        return exam;
    }

    public void setExam(ExamInfoModel exam) {
        this.exam = exam;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }
}
