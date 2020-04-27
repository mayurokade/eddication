package com.ssb.apps.bookapp.model;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.databinding.BindingAdapter;

public class DashboardResModel implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("latest_book")
    @Expose
    private LatestBook latestBook =null;
    @SerializedName("listner_choice")
    @Expose
    private ListnerChoice listnerChoice = null;
    @SerializedName("middle_of_something")
    @Expose
    private MiddleOfSomething middleOfSomething = null;
    @SerializedName("most_rated")
    @Expose
    private MostRated mostRated = null;

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

    public LatestBook getLatestBook() {
        return latestBook;
    }

    public void setLatestBook(LatestBook latestBook) {
        this.latestBook = latestBook;
    }

    public ListnerChoice getListnerChoice() {
        return listnerChoice;
    }

    public void setListnerChoice(ListnerChoice listnerChoice) {
        this.listnerChoice = listnerChoice;
    }

    public MiddleOfSomething getMiddleOfSomething() {
        return middleOfSomething;
    }

    public void setMiddleOfSomething(MiddleOfSomething middleOfSomething) {
        this.middleOfSomething = middleOfSomething;
    }

    public MostRated getMostRated() {
        return mostRated;
    }

    public void setMostRated(MostRated mostRated) {
        this.mostRated = mostRated;
    }

    public class LatestBook implements Serializable {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("BookImagePath")
        @Expose
        private String bookImagePath;
        @SerializedName("LatestData")
        @Expose
        private List<BookData> latestData = null;

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

        public List<BookData> getLatestData() {
            return latestData;
        }

        public void setLatestData(List<BookData> latestData) {
            this.latestData = latestData;
        }

    }

    public class ListnerChoice implements Serializable {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("BookImagePath")
        @Expose
        private String bookImagePath;
        @SerializedName("ListnerData")
        @Expose
        private List<BookData> listnerData = null;

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

        public List<BookData> getListnerData() {
            return listnerData;
        }

        public void setListnerData(List<BookData> listnerData) {
            this.listnerData = listnerData;
        }

    }

    public class MostRated implements Serializable {

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("BookImagePath")
        @Expose
        private String bookImagePath;
        @SerializedName("MostRatedData")
        @Expose
        private List<BookData> mostRatedData = null;

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

        public List<BookData> getMostRatedData() {
            return mostRatedData;
        }

        public void setMostRatedData(List<BookData> mostRatedData) {
            this.mostRatedData = mostRatedData;
        }

    }


    public class MiddleOfSomething implements Serializable{

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("BookImagePath")
        @Expose
        private String bookImagePath;
        @SerializedName("MiddleData")
        @Expose
        private List<BookData> middleData = null;

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

        public List<BookData> getMiddleData() {
            return middleData;
        }

        public void setMiddleData(List<BookData> middleData) {
            this.middleData = middleData;
        }

    }

    /* public class LatestDatum {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cat_name")
        @Expose
        private String bookCatName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;

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

    }*/

   /* public class ListnerDatum {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;

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

        public String getBookHindiName() {
            return bookHindiName;
        }

        public void setBookHindiName(String bookHindiName) {
            this.bookHindiName = bookHindiName;
        }

        public String getBookCoverImage() {
            return bookCoverImage;
        }

        public void setBookCoverImage(String bookCoverImage) {
            this.bookCoverImage = bookCoverImage;
        }

    }*/

    /*public class MiddleDatum {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;

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

        public String getBookHindiName() {
            return bookHindiName;
        }

        public void setBookHindiName(String bookHindiName) {
            this.bookHindiName = bookHindiName;
        }

        public String getBookCoverImage() {
            return bookCoverImage;
        }

        public void setBookCoverImage(String bookCoverImage) {
            this.bookCoverImage = bookCoverImage;
        }

    }*/

   /* public class MostRatedDatum {

        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;

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

        public String getBookHindiName() {
            return bookHindiName;
        }

        public void setBookHindiName(String bookHindiName) {
            this.bookHindiName = bookHindiName;
        }

        public String getBookCoverImage() {
            return bookCoverImage;
        }

        public void setBookCoverImage(String bookCoverImage) {
            this.bookCoverImage = bookCoverImage;
        }

    }*/

    public static class BookData implements Serializable{
        @SerializedName("book_id")
        @Expose
        private String bookId;
        @SerializedName("book_name")
        @Expose
        private String bookName;
        @SerializedName("book_hindi_name")
        @Expose
        private String bookHindiName;
        @SerializedName("book_cover_image")
        @Expose
        private String bookCoverImage;
        @SerializedName("author_name")
        @Expose
        private String autherName;
        @SerializedName("total_ratings")
        @Expose
        private String totalRatings;

        @SerializedName("avg_rating")
        @Expose
        private String avgRating;

        @SerializedName("cart_id")
        @Expose
        private String cartId;

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

        public String getAvgRating() {
            return avgRating;
        }

        public void setAvgRating(String avgRating) {
            this.avgRating = avgRating;
        }

        public String getTotalRatings() {
            return totalRatings;
        }

        public void setTotalRatings(String totalRatings) {
            this.totalRatings = totalRatings;
        }

        public String getAutherName() {
            return autherName;
        }

        public void setAutherName(String autherName) {
            this.autherName = autherName;
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

        public String getBookHindiName() {
            return bookHindiName;
        }

        public void setBookHindiName(String bookHindiName) {
            this.bookHindiName = bookHindiName;
        }

        public String getBookCoverImage() {
            return bookCoverImage;
        }

        public void setBookCoverImage(String bookCoverImage) {
            this.bookCoverImage = bookCoverImage;
        }

        @Override
        public String toString() {
            return "BookData{" +
                    "bookId='" + bookId + '\'' +
                    ", bookName='" + bookName + '\'' +
                    ", bookHindiName='" + bookHindiName + '\'' +
                    ", bookCoverImage='" + bookCoverImage + '\'' +
                    ", autherName='" + autherName + '\'' +
                    '}';
        }
    }

}
