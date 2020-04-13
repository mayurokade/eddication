package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewModel implements Serializable {

    @SerializedName("BookReviewList")
    @Expose
    private BookReviewList bookReviewList;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BookReviewList getBookReviewList() {
        return bookReviewList;
    }

    public void setBookReviewList(BookReviewList bookReviewList) {
        this.bookReviewList = bookReviewList;
    }


    public class BookReviewList {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("UserProfilePath")
        @Expose
        private String userProfilePath;
        @SerializedName("BookReviewData")
        @Expose
        private List<BookReviewDatum> bookReviewData = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getUserProfilePath() {
            return userProfilePath;
        }

        public void setUserProfilePath(String userProfilePath) {
            this.userProfilePath = userProfilePath;
        }

        public List<BookReviewDatum> getBookReviewData() {
            return bookReviewData;
        }

        public void setBookReviewData(List<BookReviewDatum> bookReviewData) {
            this.bookReviewData = bookReviewData;
        }

    }

    public class BookReviewDatum {

        @SerializedName("comment_id")
        @Expose
        private String commentId;
        @SerializedName("comment_user_name")
        @Expose
        private String commentUserName;
        @SerializedName("comment_review")
        @Expose
        private String commentReview;
        @SerializedName("comment_desc")
        @Expose
        private String commentDesc;
        @SerializedName("comment_user_profile")
        @Expose
        private Object commentUserProfile;
        @SerializedName("dt_addedon")
        @Expose
        private String dtAddedon;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public String getCommentReview() {
            return commentReview;
        }

        public void setCommentReview(String commentReview) {
            this.commentReview = commentReview;
        }

        public String getCommentDesc() {
            return commentDesc;
        }

        public void setCommentDesc(String commentDesc) {
            this.commentDesc = commentDesc;
        }

        public Object getCommentUserProfile() {
            return commentUserProfile;
        }

        public void setCommentUserProfile(Object commentUserProfile) {
            this.commentUserProfile = commentUserProfile;
        }

        public String getDtAddedon() {
            return dtAddedon;
        }

        public void setDtAddedon(String dtAddedon) {
            this.dtAddedon = dtAddedon;
        }

    }

}
