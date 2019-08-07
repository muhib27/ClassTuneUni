package com.classtune.classtuneuni.model;

import com.classtune.classtuneuni.response.UserLoginData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiKeyModel {

    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("user_data")
    @Expose
    private UserLoginData userData;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public UserLoginData getUserData() {
        return userData;
    }

    public void setUserData(UserLoginData userData) {
        this.userData = userData;
    }
}