package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProfileModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("UserProfilePath")
    @Expose
    private String userProfilePath;
    @SerializedName("profileData")
    @Expose
    private List<ProfileDatum> profileData = null;
    @SerializedName("authcatdata")
    @Expose
    private List<Authcatdatum> authcatdata = null;
    @SerializedName("PanelDetails")
    @Expose
    private PanelDetails panelDetails;

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

    public String getUserProfilePath() {
        return userProfilePath;
    }

    public void setUserProfilePath(String userProfilePath) {
        this.userProfilePath = userProfilePath;
    }

    public List<ProfileDatum> getProfileData() {
        return profileData;
    }

    public void setProfileData(List<ProfileDatum> profileData) {
        this.profileData = profileData;
    }

    public List<Authcatdatum> getAuthcatdata() {
        return authcatdata;
    }

    public void setAuthcatdata(List<Authcatdatum> authcatdata) {
        this.authcatdata = authcatdata;
    }

    public PanelDetails getPanelDetails() {
        return panelDetails;
    }

    public void setPanelDetails(PanelDetails panelDetails) {
        this.panelDetails = panelDetails;
    }


    public class ProfileDatum implements Serializable {

        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_mobile")
        @Expose
        private String userMobile;
        @SerializedName("user_email")
        @Expose
        private String userEmail;
        @SerializedName("user_profile")
        @Expose
        private String userProfile;
        @SerializedName("bucket_count")
        @Expose
        private String bucketCount;
        @SerializedName("book_shelf_count")
        @Expose
        private String bookShelfCount;
        @SerializedName("total_book_count")
        @Expose
        private String totalBookCount;
        @SerializedName("author_bio")
        @Expose
        private String authorBio;
        @SerializedName("total_ratings")
        @Expose
        private String totalRatings;
        @SerializedName("avg_rating")
        @Expose
        private String avgRating;
        @SerializedName("my_collection")
        @Expose
        private String myCollection;

        public boolean isAuther() {
            return isAuther;
        }

        public void setAuther(boolean auther) {
            isAuther = auther;
        }

        private boolean isAuther = false;

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

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserProfile() {
            return userProfile;
        }

        public void setUserProfile(String userProfile) {
            this.userProfile = userProfile;
        }

        public String getBucketCount() {
            return bucketCount;
        }

        public void setBucketCount(String bucketCount) {
            this.bucketCount = bucketCount;
        }

        public String getBookShelfCount() {
            return bookShelfCount;
        }

        public void setBookShelfCount(String bookShelfCount) {
            this.bookShelfCount = bookShelfCount;
        }

        public String getTotalBookCount() {
            return totalBookCount;
        }

        public void setTotalBookCount(String totalBookCount) {
            this.totalBookCount = totalBookCount;
        }

        public String getAuthorBio() {
            return authorBio;
        }

        public void setAuthorBio(String authorBio) {
            this.authorBio = authorBio;
        }

        public String getTotalRatings() {
            return totalRatings;
        }

        public void setTotalRatings(String totalRatings) {
            this.totalRatings = totalRatings;
        }

        public String getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(String avgRating) {
            this.avgRating = avgRating;
        }

        public Object getMyCollection() {
            return myCollection;
        }

        public void setMyCollection(String myCollection) {
            this.myCollection = myCollection;
        }

    }

    public class PanelDetails implements Serializable{

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("email")
        @Expose
        private String email;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public class Authcatdatum implements Serializable{

        @SerializedName("cat_name")
        @Expose
        private String catName;

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

    }

}
