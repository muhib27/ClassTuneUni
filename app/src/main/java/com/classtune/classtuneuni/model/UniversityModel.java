package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.response.UniData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UniversityModel {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private List<UniData> data = null;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public List<UniData> getData() {
        return data;
    }

    public void setData(List<UniData> data) {
        this.data = data;
    }
}
