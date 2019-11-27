package com.classtune.classtuneuni.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


public class TermsConditionModel extends ViewModel {
    private final MutableLiveData<String> stringMutableLiveData = new MutableLiveData<String>();

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public void setData(String agree) {
        stringMutableLiveData.setValue(agree);
    }
}
