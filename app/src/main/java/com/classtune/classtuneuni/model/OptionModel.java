package com.classtune.classtuneuni.model;

public class OptionModel {
    private String si;
    private String opt;
    private String status;

    public OptionModel() {
    }

    public OptionModel(String si, String opt) {
        this.si = si;
        this.opt = opt;
    }

    public OptionModel(String si, String opt, String status) {
        this.si = si;
        this.opt = opt;
        this.status = status;
    }

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
