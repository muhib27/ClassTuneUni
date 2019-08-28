package com.classtune.classtuneuni.home;

import com.classtune.classtuneuni.message.StSendMsgData;
import com.classtune.classtuneuni.model.CommonStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StHomeRespons {
    @SerializedName("status")
    @Expose
    private CommonStatus status;
    @SerializedName("data")
    @Expose
    private StHomeData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StHomeData getData() {
        return data;
    }

    public void setData(StHomeData data) {
        this.data = data;
    }
}
