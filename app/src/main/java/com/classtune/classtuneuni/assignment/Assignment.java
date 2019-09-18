package com.classtune.classtuneuni.assignment;

import com.classtune.classtuneuni.model.AssignmentModel;
import com.classtune.classtuneuni.model.AttachmentModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assignment {
    @SerializedName("assignment")
    @Expose
    private AssignmentModel assignment;
    @SerializedName("students")
    @Expose
    private Integer students;
    @SerializedName("submissions")
    @Expose
    private Integer submissions;

    @SerializedName("submission")
    @Expose
    private Integer submission;
    @SerializedName("mark")
    @Expose
    private Integer mark;

    @SerializedName("attachments")
    @Expose
    private List<AssinmentAttachment> attachments = null;


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("course_offer_section_id")
    @Expose
    private String courseOfferSectionId;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("course_code")
    @Expose
    private String courseCode;
    @SerializedName("assign_date")
    @Expose
    private String assignDate;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("submission_student_id")
    @Expose
    private String submissionStudentId;
    @SerializedName("due")
    @Expose
    private Boolean due;

    public Assignment() {
    }



    public Integer getStudents() {
        return students;
    }

    public void setStudents(Integer students) {
        this.students = students;
    }

    public Integer getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Integer submissions) {
        this.submissions = submissions;
    }

    public AssignmentModel getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentModel assignment) {
        this.assignment = assignment;
    }

    public Integer getSubmission() {
        return submission;
    }

    public void setSubmission(Integer submission) {
        this.submission = submission;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public List<AssinmentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AssinmentAttachment> attachments) {
        this.attachments = attachments;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getCourseOfferSectionId() {
        return courseOfferSectionId;
    }

    public void setCourseOfferSectionId(String courseOfferSectionId) {
        this.courseOfferSectionId = courseOfferSectionId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubmissionStudentId() {
        return submissionStudentId;
    }

    public void setSubmissionStudentId(String submissionStudentId) {
        this.submissionStudentId = submissionStudentId;
    }

    public Boolean getDue() {
        return due;
    }

    public void setDue(Boolean due) {
        this.due = due;
    }
}
