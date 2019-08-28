package com.classtune.classtuneuni.message;

import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StSendMsgResponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StSendMsgData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StSendMsgData getData() {
        return data;
    }

    public void setData(StSendMsgData data) {
        this.data = data;
    }
}
