package com.classtune.classtuneuni.profile;

import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.resource.ResourceData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StProfileRsponse {
    @SerializedName("status")
    @Expose
    private CommonStatus status;

    @SerializedName("data")
    @Expose
    private StProfileData data;

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }

    public StProfileData getData() {
        return data;
    }

    public void setData(StProfileData data) {
        this.data = data;
    }
}
