package com.ssb.apps.bookapp.api;
import com.ssb.apps.bookapp.model.BookInfoModel;
import com.ssb.apps.bookapp.model.CategotryResModel;
import com.ssb.apps.bookapp.model.DashboardResModel;
import com.ssb.apps.bookapp.model.ReviewModel;
import com.ssb.apps.bookapp.model.RoutModel;
import com.ssb.apps.bookapp.model.SearchResModel;
import com.ssb.apps.bookapp.model.StatusModel;
import com.ssb.apps.bookapp.model.UserDetailsModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


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
                                          @Field("deviceId") String deviceId );

    @POST("/app_dashboard")
    Call<DashboardResModel> getDashboardDetails(@Header("secId") String secId,
                                           @Header("secToken") String secToken,
                                           @Header("request") String request );

    @POST("/app_category")
    Call<CategotryResModel> getCategoryDetails(@Header("secId") String secId,
                                                @Header("secToken") String secToken,
                                                @Header("request") String request );

    @FormUrlEncoded
    @POST("/app_search")
    Call<SearchResModel> getSearchDetails(@Header("secId") String secId,
                                            @Header("secToken") String secToken,
                                            @Header("request") String request,
                                            @Field("searchType") String searchType,
                                            @Field("catId") String catId,
                                            @Field("searchKeyword") String searchKeyword );

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

   /* @POST("Authentication/Valida teUser")
    Call<UserDetails> getUserDetails(@Query("userId") String userId,
                                     @Query("password") String password);
    @POST("Authentication/ValidateUser")
    Call<UserDetails> getUserDetails1(*//*@Query("userId") String userId,
                                     @Query("password") String password*//*
            @Body RequestBody params);

    @GET("Calendar/GetCalendarEvents")
    Call<List<CalendarEventsModel>> getCalendarEvents(@Header("X-Auth-Token") String token,
                                                      @Query("userId") long userId,
                                                      @Query("companyId") long companyId,
                                                      @Query("startDate") String startDate,
                                                      @Query("endDate") String endDate);

    @POST("LookupHandler/LookupCompanies")
    Call<List<LookupModel>> getCompanyList(@Header("X-Auth-Token") String token,
                                           @Body HashMap<String, String> params);



    @POST("LookupHandler/LookupActs")
    Call<List<LookupModel>> getActList(@Header("X-Auth-Token") String token,
                                       @Body HashMap<String, String> params);


    @POST("LookupHandler/LookupActsForCompany")
    Call<List<LookupModel>> getActListDoc(@Header("X-Auth-Token") String token,
                                          @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupUserType")
    Call<List<LookupModel>> getUserTypeList(@Header("X-Auth-Token") String token,
                                            @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupFiscalYear")
    Call<List<LookupModel>> getFiscalYear(@Header("X-Auth-Token") String token);

    @POST("CompanyEventManagement/GetFilteredEvents")
    Call<ComplianceListModel> getFilteredList(@Header("X-Auth-Token") String token,
                                              @Body HashMap<String, String> params);

    @POST("CompanyEventManagement/GetPendingEvents")
    Call<ComplianceListModel> getPendingEvents(@Header("X-Auth-Token") String token,
                                               @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupByAct")
    Call<LookupActModel> getLookUpAct(@Header("X-Auth-Token") String token, @Query("userId") long userId,
                                      @Query("companyId") long companyId, @Query("actId") long actId);

    @POST("LookupHandler/LookupUnitsAccessControl")
    Call<List<LookupModel>> getLookupUnit(@Header("X-Auth-Token") String token,
                                          @Body HashMap<String, String> params);

    @POST("CompanyEventManagement/GetQuestionsForCompanyEvent")
    Call<QuestionsCompanyEventModel> getQuestionsForCompanyEvent(@Header("X-Auth-Token") String token,
                                                                 @Query("companyEventId") String companyEventId);


    @GET("CheckList/GetChecklistDocs")
    Call<CheckListDocumentModel> getDocsListCheckList(@Header("X-Auth-Token") String token, @Query("checklistInfoId")
            String checklistInfoId, @Query("companyEventId") String companyEventId);


    @GET("Document/LoadDocument")
    Call<String> getLoadDocument(@Header("X-Auth-Token") String token, @Query("contentId")
            String contentId);


    @POST("CompanyEventManagement/SaveEvent")
    Call<DataModel> getSaveEvents(@Header("X-Auth-Token") String token,
                                  @Query("userId") long userId,
                                  @Body RequestBody params);

    @POST("CompanyEventManagement/EditPayment")
    Call<ComplianceListModel.CompanyEvent> editPaymentEvent(@Header("X-Auth-Token") String token,
                                                            @Body RequestBody params,
                                                            @Query("userId") long userId);


    @POST("CompanyEventManagement/SaveCompanyChecklist")
    Call<JSONObject> editCheckListQuestion(@Header("X-Auth-Token") String token,
                                           @Body RequestBody params,
                                           @Query("userId") long userId);


    @POST("CompanyEventManagement/CalculateInterestAndPenalty")
    Call<InterestAmountModel> getPenaltyAmount(@Header("X-Auth-Token") String token,
                                               @Query("amountDue") String amountDue,
                                               @Query("companyEventId") String companyEventId,
                                               @Query("dueDate") String dueDate,
                                               @Query("paymentDate") String paymentDate,
                                               @Query("amountPaid") String amountPaid);

    @DELETE("CheckList/DeleteCheckListDoc")
    Call<JSONObject> deleteImage(@Header("X-Auth-Token") String token,
                                 @Query("checklistInfoId") String checklistInfoId,
                                 @Query("docId") String docId);

    @POST("CheckList/SaveCheckListDocs")
    Call<JSONObject> saveCheckListRemark(@Header("X-Auth-Token") String token, @Query("checklistInfoId") String checklistInfoId,
                                         @Query("remarks") String remarks);

    @POST("CheckList/UploadCheckListDocument")
    Call<JSONObject> getSaveCheckListDocumnet(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("CompanyEventManagement/UploadDocument")
    Call<JSONObject> getSaveCompanyEventDocumnet(@Header("X-Auth-Token") String token, @Body RequestBody body);


    @POST("CompanyEventManagement/GetEventDocs")
    Call<CheckListDocumentModel> getDocsListCompanyEvent(@Header("X-Auth-Token") String token, @Body RequestBody body);

    //@DELETE("CompanyEventManagement/DeleteEventDoc/")
    // @FormUrlEncoded
    @HTTP(method = "DELETE", path = "CompanyEventManagement/DeleteEventDoc", hasBody = true)
    Call<JSONObject> deleteCompanyEventImage(@Header("X-Auth-Token") String token,
                                             @Body RequestBody body);

    @POST("CompanyEventManagement/GetSelfAssessmentTaxDetails")
    Call<ComplianceListModel> getSelfAssessmentTaxDetails(@Header("X-Auth-Token") String token,
                                                          @Body RequestBody params,
                                                          @Query("companyEventId") long companyEventId);

    @POST("CompanyEventManagement/CalculateAdvanceTaxLiability")
    Call<Payments> saveCalculateAdvTaxLib(@Header("X-Auth-Token") String token, @Query("fiscalYearStart") Integer fiscalYearStart, @Query("legalStatusId") String legalStatusId
            , @Query("taxableIncome") String taxableIncome, @Query("tds") String tds, @Query("turnOverPrevYear") String turnOverPrevYear);

    @POST("CompanyEventManagement/SaveProjectedAdvanceTaxAmounts")
    Call<ComplianceListModel.Projection> saveProjectedAdvanceTaxAmounts(@Header("X-Auth-Token") String token, @Body RequestBody body,
                                                                        @Query("companyEventId") String companyEventId);

    @POST("Dashboard/GetTotalAccruedTaxLiability")
    Call<TotalAccruedTax> getTotalAccruedTaxLiability(@Header("X-Auth-Token") String token,
                                                      @Body RequestBody params);

    @POST("Dashboard/GetContingentLiability")
    Call<ContingentLiabilityModel> getContingentLiability(@Header("X-Auth-Token") String token,
                                                          @Body RequestBody params);

    @POST("Dashboard/GetMyQuestionnaireDelays")
    Call<ChartQuestionrayModel> getMyQuestionnaireDelays(@Header("X-Auth-Token") String token,
                                                         @Body RequestBody params);

    @POST("Dashboard/GetLitigationDetails")
    Call<List<LitifationTrackerModel>> getLitigationDetails(@Header("X-Auth-Token") String token,
                                                            @Body RequestBody params);

    @POST("DocumentManagement/GetDocuments")
    Call<List<DocumentsModel.Documents>> getListDoc(@Header("X-Auth-Token") String token,
                                                    @Body HashMap<String, String> params);

    @POST("DocumentManagement/UploadDocument")
    Call<String> addDocumentDetails(@Header("X-Auth-Token") String token,
                                    @Body RequestBody body);

    @POST("LookupHandler/Lookup")
    Call<List<LookupModel>> getDocType(@Header("X-Auth-Token") String token,
                                       @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupOperatorsAndReviewers")
    Call<List<LookupModel>> getOpraterAndReiview(@Header("X-Auth-Token") String token,
                                                 @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupInterestTypes")
    Call<List<LookupModel>> getIntrestType(@Header("X-Auth-Token") String token);

    @POST("LookupHandler/LookupPaymentTypes")
    Call<List<LookupModel>> getPaymentType(@Header("X-Auth-Token") String token);

    @POST("LookupHandler/LookupEventPeriods")
    Call<List<LookupModel>> getEventPeriods(@Header("X-Auth-Token") String token);

    @GET("Document/LoadDocument")
    Call<String> getDocument(@Header("X-Auth-Token") String token,
                             @Query("contentId") String contentId,
                             @Query("password") String password);
    @POST("MyTrack/GetMyTrackEvents")
    Call<List<MyTrackModel.MyTrack>> getTrackList(@Header("X-Auth-Token") String token,
                                                  @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupByShortDescription")
    Call<List<LookupModel>> getLookupByShortDescription(@Header("X-Auth-Token") String token,
                                                        @Body HashMap<String, String> params);
    @POST("MyTrack/SaveEvent")
    Call<SaveEventModel> addSaveEvent(@Header("X-Auth-Token") String token,
                                      @Body RequestBody body);

    @POST("MyQuestionnaire/GetQuestions")
    Call<List<MyQuestionaryModel>> getQuestionsList(@Header("X-Auth-Token") String token,
                                                    @Body HashMap<String, String> params);

    @POST("MyQuestionnaire/GetEvents")
    Call<List<MyQuestionaryListModel>> getQuestionEvent(@Header("X-Auth-Token") String token,
                                                        @Body HashMap<String, String> params);

    @POST("MyQuestionnaire/SaveEvent")
    Call<SaveEventModel> addSaveEventMyQuestionary(@Header("X-Auth-Token") String token,
                                                   @Body RequestBody body);

    @POST("LitigationTracker/GetLitigations")
    Call<List<LitifationTrackerModel>> getLitigationList(@Header("X-Auth-Token") String token,
                                                         @Body HashMap<String, String> params);


    @POST("LitigationTracker/GetLitigationHistory")
    Call<List<LitifationTrackerModel>> getLitigationHistory(@Header("X-Auth-Token") String token,
                                                            @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupOperatorsAndReviewersByAct")
    Call<List<LookUpOperatorAndReviewerModel>> getOpraterAndReiviewByAct(@Header("X-Auth-Token") String token,
                                                                         @Body HashMap<String, String> params);


    @POST("LitigationTracker/SaveLitigation")
    Call<SaveEventModel> addSaveEventMyLitigation(@Header("X-Auth-Token") String token,
                                                  @Body RequestBody body);

    @POST("LitigationTracker/GetDocuments")
    Call<List<CheckListDocumentModel.Document>> getDocsListLitigation(@Header("X-Auth-Token") String token,
                                                                      @Body HashMap<String, String> params);


    @POST("LitigationTracker/UploadDocument")
    Call<JSONObject> getSaveLitigationDocumnet(@Header("X-Auth-Token") String token, @Body RequestBody body);

   // @DELETE("LitigationTracker/DeleteLitigationDoc")
    @HTTP(method = "DELETE", path = "LitigationTracker/DeleteLitigationDoc", hasBody = true)
    Call<SaveEventModel> deleteDocumentImage(@Header("X-Auth-Token") String token, @Body RequestBody body);


    @HTTP(method = "DELETE", path = "AssessmentTracker/DeleteAssessmentDoc", hasBody = true)
    Call<SaveEventModel> deleteDocumentImageAssess(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("AssessmentTracker/GetAssessments")
    Call<List<AssessmentModel>> getAssessmentList(@Header("X-Auth-Token") String token,
                                                  @Body HashMap<String, String> params);
    @POST("AssessmentTracker/SaveAssessment")
    Call<SaveEventModel> saveOrUpateAssessment(@Header("X-Auth-Token") String token,
                                               @Body RequestBody body);

    @POST("AssessmentTracker/UploadDocument")
    Call<JSONObject> getSaveAssesseDocumnet(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("AssessmentTracker/GetDocuments")
    Call<List<CheckListDocumentModel.Document>> getDocsListAssessment(@Header("X-Auth-Token") String token,
                                                                      @Body HashMap<String, String> params);

    @POST("AssessmentTracker/GetAssessmentHistory")
    Call<List<AssessmentModel>> getAssessmentHistory(@Header("X-Auth-Token") String token,
                                                     @Body HashMap<String, String> params);

    @POST("Notification/GetNotifications")
    Call<List<NotificationModel>> getNotificationList(@Header("X-Auth-Token") String token,
                                                      @Query("index") int index);

    @POST("LookupHandler/LookupCompanyLawApplicableCompanies")
    Call<List<LookupModel>> getompanyLawApplicableCompanies(@Header("X-Auth-Token") String token,
                                                            @Body HashMap<String, String> params);

    @POST("Meetings/GetMeetings")
    Call<List<MeetingModel>> getMeetingList(@Header("X-Auth-Token") String token,
                                            @Body HashMap<String, String> params);

    @POST("LookupHandler/LookupParameterValues")
    Call<List<LookupModel>> getMeetingCategoryWise(@Header("X-Auth-Token") String token,
                                                   @Body HashMap<String, String> params);

    @POST("Meetings/GetControlVisibility")
    Call<MeetingListModel> getMeetlistDyamic(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("Meetings/SaveMeeting")
    Call<SaveEventModel> getSaveMeeting(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @DELETE("Meetings/DeleteMeeting")
    Call<SaveEventModel> deleteMeeting(@Header("X-Auth-Token") String token,
                                       @Query("companyId") long companyId,
                                       @Query("groupId") int groupId);

    @POST("CompanyLaw/GetControlVisibility")
    Call<MeetingListModel> getAddMeetingDyanmically(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("CompanyLaw/SaveEvent")
    Call<SaveEventDetialsModel> getSaveEvent(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("CompanyLaw/GetCategories")
    Call<List<LookupModel>> getCategories(@Header("X-Auth-Token") String token,
                                          @Body HashMap<String, String> params);
    @POST("CompanyLaw/GetEventGroups")
    Call<List<EventModel.EventList>> getEventList(@Header("X-Auth-Token") String token,
                                                  @Body HashMap<String, String> params);

    @POST("CompanyLaw/GetEventGroupDetails")
    Call<List<EventDetailModel>> getEventDetails(@Header("X-Auth-Token") String token,
                                                 @Body HashMap<String, String> params);

    @HTTP(method = "DELETE", path = "CompanyLaw/DeleteGroup", hasBody = true)
    Call<SaveEventModel> deleteEvent(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("CompanyEventManagement/ReviewerApproval")
    Call<SaveEventModel> setApproval(@Header("X-Auth-Token") String token,
                                     @Body HashMap<String, String> params);

    @POST("Settings/ChangePassword")
    Call<SaveEventModel> changePassword(@Header("X-Auth-Token") String token,
                                        @Body HashMap<String, String> params);

    @POST("Dashboard/GetCompletedEvents")
    Call<DashboardModel> getCompletedEvents(@Header("X-Auth-Token") String token,
                                            @Body RequestBody params);

    @POST("Dashboard/GetEventsPendingForReview")
    Call<DashboardModel> getEventsPendingForReview(@Header("X-Auth-Token") String token,
                                                   @Body RequestBody params);

    @POST("Dashboard/GetEventsOverDue")
    Call<DashboardModel> getEventsOverDue(@Header("X-Auth-Token") String token,
                                          @Body RequestBody params);

    @POST("Dashboard/GetEventsDue")
    Call<DashboardModel> getEventsDue(@Header("X-Auth-Token") String token,
                                      @Body RequestBody params);

    @POST("Dashboard/GetTotalCashLoss")
    Call<DashboardModel> getTotalCashLoss(@Header("X-Auth-Token") String token,
                                          @Body RequestBody params);*/
}
