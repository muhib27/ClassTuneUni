package com.classtune.classtuneuni.response;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class NoticeData {

//    @SerializedName("notices")
//    @Expose
//    private List<Notice> notices = null;
////    @SerializedName("dates")
////    @Expose
////    private JsonObject jsonObject;
////    @SerializedName("dates")
////    @Expose
////    private JsonArray jsonArray;
//
//
//
//    public List<Notice> getNotices() {
//        return notices;
//    }
//
//    public void setNotices(List<Notice> notices) {
//        this.notices = notices;
//    }
////
////    public JsonArray getJsonArray() {
////        return jsonArray;
////    }
////
////    public void setJsonArray(JsonArray jsonArray) {
////        this.jsonArray = jsonArray;
////    }
//
////
////    public JsonObject getJsonObject() {
////        return jsonObject;
////    }
////
////    public void setJsonObject(JsonObject jsonObject) {
////        this.jsonObject = jsonObject;
////    }

    @SerializedName("notices")
    @Expose
    private List<Notice> notices = null;
    @SerializedName("total_page")
    @Expose
    private Integer totalPages;
    @SerializedName("next_page")
    @Expose
    private Integer nextPage;
    @SerializedName("date_time")
    @Expose
    private String currentTime;


    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
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

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}