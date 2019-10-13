package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.response.UserLoginData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonStatus {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("data")
    @Expose
    private UserLoginData data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }
}
