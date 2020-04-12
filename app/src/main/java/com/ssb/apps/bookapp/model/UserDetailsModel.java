package com.ssb.apps.bookapp.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @Expose
    private ShopData shopData;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShopData getShopData() {
        return shopData;
    }

    public void setShopData(ShopData shopData) {
        this.shopData = shopData;
    }


}
