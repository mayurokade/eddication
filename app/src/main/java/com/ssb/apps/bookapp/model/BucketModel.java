package com.ssb.apps.bookapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BucketModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("BookImagePath")
    @Expose
    private String bookImagePath;
    @SerializedName("cartData")
    @Expose
    private List<DashboardResModel.BookData> cartData = null;

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

    public String getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(String bookImagePath) {
        this.bookImagePath = bookImagePath;
    }

    public List<DashboardResModel.BookData> getCartData() {
        return cartData;
    }

    public void setCartData(List<DashboardResModel.BookData> cartData) {
        this.cartData = cartData;
    }

}
