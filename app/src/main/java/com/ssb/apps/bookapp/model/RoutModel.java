package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoutModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("base_url")
    @Expose
    private String baseUrl;
    @SerializedName("otp")
    @Expose
    private String opt;
    @SerializedName("msg")
    @Expose
    private String msg;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
