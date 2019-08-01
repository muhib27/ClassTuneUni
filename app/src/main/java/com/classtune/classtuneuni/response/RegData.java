package com.classtune.classtuneuni.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegData {

    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("user_data")
    @Expose
    private UserData userData;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

}