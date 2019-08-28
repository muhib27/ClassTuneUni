package com.classtune.classtuneuni.message;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StSendMsgData {
    @SerializedName("course_offer_section_id")
    @Expose
    private String courseOfferSectionId;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("msg")
    @Expose
    private String msg;

    public String getCourseOfferSectionId() {
        return courseOfferSectionId;
    }

    public void setCourseOfferSectionId(String courseOfferSectionId) {
        this.courseOfferSectionId = courseOfferSectionId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
