package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("user_token")
    @Expose
    private String userToken;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("is_author")
    @Expose
    private boolean isAuthor = false;

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

}
