package com.classtune.classtuneuni.exam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PolicyData {
    @SerializedName("policies")
    @Expose
    private List<Policy> policies = null;

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
