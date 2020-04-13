package com.ssb.apps.bookapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BookInfoModel implements Serializable {

    @SerializedName("BookInfo")
    @Expose
    private BookInfo bookInfo;
    @SerializedName("ChapterInfo")
    @Expose
    private ChapterInfo chapterInfo;
    @SerializedName("msg")
    @Expose
    private String msg = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public ChapterInfo getChapterInfo() {
        return chapterInfo;
    }

    public void setChapterInfo(ChapterInfo chapterInfo) {
        this.chapterInfo = chapterInfo;
    }

    public class ChapterInfo implements Serializable {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("ChapterAudioPath")
        @Expose
        private String chapterAudioPath;
        @SerializedName("ChapterData")
        @Expose
        private List<ChapterDatum> chapterData = null;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public String getChapterAudioPath() {
            return chapterAudioPath;
        }

        public void setChapterAudioPath(String chapterAudioPath) {
            this.chapterAudioPath = chapterAudioPath;
        }

        public List<ChapterDatum> getChapterData() {
            return chapterData;
        }

        public void setChapterData(List<ChapterDatum> chapterData) {
            this.chapterData = chapterData;
        }

    }

    public class ChapterDatum implements Serializable {

        @SerializedName("chapter_id")
        @Expose
        private String chapterId;
        @SerializedName("chapter_pay_status")
        @Expose
        private String chapterPayStatus;
        @SerializedName("chapter_name")
        @Expose
        private String chapterName;
        @SerializedName("chapter_hindi_name")
        @Expose
        private String chapterHindiName;
        @SerializedName("chapter_file")
        @Expose
        private String chapterFile;
        @SerializedName("dt_addedon")
        @Expose
        private String dtAddedon;

        public String getChapterId() {
            return chapterId;
        }

        public void setChapterId(String chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterPayStatus() {
            return chapterPayStatus;
        }

        public void setChapterPayStatus(String chapterPayStatus) {
            this.chapterPayStatus = chapterPayStatus;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getChapterHindiName() {
            return chapterHindiName;
        }

        public void setChapterHindiName(String chapterHindiName) {
            this.chapterHindiName = chapterHindiName;
        }

        public String getChapterFile() {
            return chapterFile;
        }

        public void setChapterFile(String chapterFile) {
            this.chapterFile = chapterFile;
        }

        public String getDtAddedon() {
            return dtAddedon;
        }

        public void setDtAddedon(String dtAddedon) {
            this.dtAddedon = dtAddedon;
        }

    }

    public class BookInfoData implements Serializable {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_price")
        @Expose
        private String bookPrice;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cat_name")
        @Expose
        private String bookCatName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;
        @SerializedName("book_summary_file")
        @Expose
        private String bookSummaryFile;
        @SerializedName("total_ratings")
        @Expose
        private String totalRatings;
        @SerializedName("avg_rating")
        @Expose
        private String avgRating;
        @SerializedName("dt_addedon")
        @Expose
        private String dtAddedon;
        @SerializedName("book_desc")
        @Expose
        private String book_desc;
        @SerializedName("HasUserPaid")
        @Expose
        private Boolean hasUserPaid;

        public String getBook_desc() {
            return book_desc;
        }

        public void setBook_desc(String book_desc) {
            this.book_desc = book_desc;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getBookPrice() {
            return bookPrice;
        }

        public void setBookPrice(String bookPrice) {
            this.bookPrice = bookPrice;
        }

        public String getBookHindiName() {
            return bookHindiName;
        }

        public void setBookHindiName(String bookHindiName) {
            this.bookHindiName = bookHindiName;
        }

        public String getBookCatName() {
            return bookCatName;
        }

        public void setBookCatName(String bookCatName) {
            this.bookCatName = bookCatName;
        }

        public String getBookCoverImage() {
            return bookCoverImage;
        }

        public void setBookCoverImage(String bookCoverImage) {
            this.bookCoverImage = bookCoverImage;
        }

        public String getBookSummaryFile() {
            return bookSummaryFile;
        }

        public void setBookSummaryFile(String bookSummaryFile) {
            this.bookSummaryFile = bookSummaryFile;
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

        public String getDtAddedon() {
            return dtAddedon;
        }

        public void setDtAddedon(String dtAddedon) {
            this.dtAddedon = dtAddedon;
        }

        public Boolean getHasUserPaid() {
            return hasUserPaid;
        }

        public void setHasUserPaid(Boolean hasUserPaid) {
            this.hasUserPaid = hasUserPaid;
        }

    }

    public class BookInfo implements Serializable{

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("BookImagePath")
        @Expose
        private String bookImagePath;
        @SerializedName("ChapterAudioPath")
        @Expose
        private String chapterAudioPath;
        @SerializedName("BookInfoData")
        @Expose
        private BookInfoData bookInfoData;

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

        public String getChapterAudioPath() {
            return chapterAudioPath;
        }

        public void setChapterAudioPath(String chapterAudioPath) {
            this.chapterAudioPath = chapterAudioPath;
        }

        public BookInfoData getBookInfoData() {
            return bookInfoData;
        }

        public void setBookInfoData(BookInfoData bookInfoData) {
            this.bookInfoData = bookInfoData;
        }

    }

}
