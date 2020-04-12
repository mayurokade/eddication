package com.ssb.apps.bookapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategotryResModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("CategoryData")
    @Expose
    private List<CategoryDatum> categoryData = null;

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

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }

    public class CategoryDatum implements Serializable {

        @SerializedName("ICATID")
        @Expose
        private String iCATID;
        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("avail_book_count")
        @Expose
        private String availBookCount;

        public String getICATID() {
            return iCATID;
        }

        public void setICATID(String iCATID) {
            this.iCATID = iCATID;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public String getAvailBookCount() {
            return availBookCount;
        }

        public void setAvailBookCount(String availBookCount) {
            this.availBookCount = availBookCount;
        }

    }

}
