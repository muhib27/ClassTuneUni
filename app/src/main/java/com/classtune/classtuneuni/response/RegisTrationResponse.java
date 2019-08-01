package com.classtune.classtuneuni.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisTrationResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RegData data;

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

    public RegData getData() {
        return data;
    }

    public void setData(RegData data) {
        this.data = data;
    }
}