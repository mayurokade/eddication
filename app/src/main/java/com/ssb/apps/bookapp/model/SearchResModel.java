package com.ssb.apps.bookapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("BookImagePath")
    @Expose
    private String bookImagePath;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("SearchData")
    @Expose
    private List<DashboardResModel.BookData> searchData = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBookImagePath() {
        return bookImagePath;
    }

    public void setBookImagePath(String bookImagePath) {
        this.bookImagePath = bookImagePath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DashboardResModel.BookData> getSearchData() {
        return searchData;
    }

    public void setSearchData(List<DashboardResModel.BookData> searchData) {
        this.searchData = searchData;
    }

}
