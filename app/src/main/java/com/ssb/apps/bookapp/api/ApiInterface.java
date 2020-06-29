package com.ssb.apps.bookapp.api;

import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.model.BucketModel;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.ProfileModel;
import com.ssb.apps.bookapp.model.ReviewModel;
import com.ssb.apps.bookapp.model.RoutModel;
import com.ssb.apps.bookapp.model.SearchResModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.model.UserDetailsModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("/AppRoute/EddApp.php")
    Call<RoutModel> getBaseUrl(@Field("appName") String appName);

    @FormUrlEncoded
    @POST("/app_auth")
    Call<RoutModel> getAuthriseMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/app_otpverify")
    Call<UserDetailsModel> getUserDetails(@Field("mobile") String mobile,
                                          @Field("otp") String otp,
                                          @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("/app_auth_email")
    Call<UserDetailsModel> loginWithFb(@Field("email") String email,
                                          @Field("name") String name,
                                          @Field("appType") String appType,
                                          @Field("deviceId") String deviceId);

    @POST("/app_dashboard")
    Call<DashboardResModel> getDashboardDetails(@Header("secId") String secId,
                                                @Header("secToken") String secToken,
                                                @Header("request") String request);

    @POST("/app_category")
    Call<CategotryResModel> getCategoryDetails(@Header("secId") String secId,
                                               @Header("secToken") String secToken,
                                               @Header("request") String request);

    @FormUrlEncoded
    @POST("/app_search")
    Call<SearchResModel> getSearchDetails(@Header("secId") String secId,
                                          @Header("secToken") String secToken,
                                          @Header("request") String request,
                                          @Field("searchType") String searchType,
                                          @Field("catId") String catId,
                                          @Field("searchKeyword") String searchKeyword);

    @FormUrlEncoded
    @POST("/app_book")
    Call<BookInfoModel> getBookInfo(@Header("secId") String secId,
                                    @Header("secToken") String secToken,
                                    @Header("request") String request,
                                    @Field("bookId") String bookId);

    @FormUrlEncoded
    @POST("/app_all_book_review")
    Call<ReviewModel> getBookReview(@Header("secId") String secId,
                                    @Header("secToken") String secToken,
                                    @Header("request") String request,
                                    @Field("bookId") String bookId);

    @FormUrlEncoded
    @POST("/app_book_review")
    Call<StatusModel> addBookReview(@Header("secId") String secId,
                                    @Header("secToken") String secToken,
                                    @Header("request") String request,
                                    @Field("bookId") String bookId,
                                    @Field("reviewStar") String reviewStar,
                                    @Field("comment") String comment);

    @FormUrlEncoded
    @POST("/app_add_book_cart")
    Call<StatusModel> addBookBucket(@Header("secId") String secId,
                                    @Header("secToken") String secToken,
                                    @Header("request") String request,
                                    @Field("bookId") String bookId);


    @POST("/app_user_cart")
    Call<BucketModel> getBucketList(@Header("secId") String secId,
                                    @Header("secToken") String secToken,
                                    @Header("request") String request);

    @FormUrlEncoded
    @POST("/app_remove_book_cart")
    Call<StatusModel> getDeleteBookFromBucketList(@Header("secId") String secId,
                                                  @Header("secToken") String secToken,
                                                  @Header("request") String request,
                                                  @Field("bookId") String bookId,
                                                  @Field("cartId") String cartId);

    @FormUrlEncoded
    @POST("/app_register")
    Call<StatusModel> regstrationCall(@Field("name") String name,
                                          @Field("mobile") String mobile,
                                          @Field("email") String email,
                                          @Field("appType") String appType);

    @FormUrlEncoded
    @POST("/app_author_register")
    Call<StatusModel> writerRegister(@Header("secId") String secId,
                                     @Header("secToken") String secToken,
                                     @Header("request") String request,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("bio") String bio);

    @FormUrlEncoded
    @POST("/app_purchase_book")
    Call<StatusModel> callPurchaseAPI(@Header("secId") String secId,
                                      @Header("secToken") String secToken,
                                      @Header("request") String request,
                                      @Field("bId") String bId,
                                      @Field("tranId") String tranId);

    @POST("/app_profile")
    Call<ProfileModel> getUserProfile(@Header("secId") String secId,
                                      @Header("secToken") String secToken,
                                      @Header("request") String request);
}
