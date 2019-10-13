package com.classtune.classtuneuni.profile;

import com.classtune.classtuneuni.model.CommonStatus;
import com.classtune.classtuneuni.response.UserLoginData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileResponse {
    @SerializedName("data")
    @Expose
    private UserLoginData data;
    @SerializedName("status")
    @Expose
    private CommonStatus status;

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }

    public CommonStatus getStatus() {
        return status;
    }

    public void setStatus(CommonStatus status) {
        this.status = status;
    }
}
