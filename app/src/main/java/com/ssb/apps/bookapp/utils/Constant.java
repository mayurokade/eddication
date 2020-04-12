package com.ssb.apps.bookapp.utils;


/**
 * Created by prateek on 12/04/2018.
 */

public class Constant {

    public static final String RESULT = "result";
    public static final String EMAIL = "email";
    public static final String PIN_TYPE_CODE = "PINTypeCode";
    public static final String REGISTRATION = "R";
    public static final String PIN = "pin";
    public static final String ADDRESS = "address";
    public static final String CUSTOMER_ADDRESS_ID = "customerAddressId";
    public static final int ORDER_HISTORY_FLAG_SPINNER_ADAPTER = 1;
    public static final String PIN_CODE = "pinCode";
    public static final String PASSWORD = "Password";
    public static final String UID = "Id";
    public static final String USER_NAME = "Username";
    public static final double FIX_RATE_PERCENT = 5.0f;
    public static final int CHECK_RETAILER_EXIST = 201;


    public static final String FCM_ID = "fcmToken";
    public static final String RETAILER_ID = "retailerId";
    public static final String ORDER_CREATED_BY = "R";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String SCREEN_POS = "screen_pos";
    public static final int FLAG_SUCCESS = 200 ;
    public static final String MOBILE ="mobile" ;
    public static final String TOKEN ="token" ;
    public static final String ISLOGIN = "isLogin";
    public static String URL;

    public static String SESSION_USER_SALT = "session_user_salt";
    public static final String VALUE_AUTH_KEY = "Basic RmFybVRyYWNrMDE6clFaVUxEZUIxOWt6TEZYUjhBcFBmTXA2SlZ1U0VIZUIxOWt6TEZYRGtUaHFNRktlQjE5a3pMRlhlTWF2M3g4SjRMVk1QRj0=";

    public static final String NAME = "NAME";

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String SALT = "salt";

    public static final String USERID = "userId";
    public static final String ID = "id";
    public static final String AGREETA_USERID = "agreetaUserId";
    public static final String RETAILER_ADDRESS_ID = "retailerAddressId";


    // http://35.154.128.96/FarmTrack_WebAPI_Test/api/v1/Retailer/RegisterBuyer
    /* http://35.154.128.96/FarmTrak_Buyer_WebAPI/api/v1/Buyer/Products*/
    /* http://35.154.128.96/FarmTrak_Buyer_WebAPI/App_Themes/MainTheme/img/buyerproduct/cumin.png*/

    //public static String BASE_URL = ""+ BookApp.cache.writeString(Constant.this,"fcm_token",refreshedToken);
    public static String BASE_URL = "";//Test Server Environment
    public static final String IMAGE_URL = "http://35.154.128.96/FarmTrack_WebAPI_Test/App_Themes/MainTheme/img/";
    public static final String BUYER_IMAGE_URL = "http://35.154.128.96/FarmTrack_WebAPI_Test/App_Themes/MainTheme/img/buyerproduct/";




    //url authenticate user i/p
    public static final String AUTH_USER_ID = "UserId";
    public static final String AUTH_PASSWORD = "Password";
    //retry policy time in ms
    public static final int retry_time = 120 * 1000;

    public static final String
            SESSION_U_ID = "session_u_id",
            SESSION_USER_ID = "session_user_id",

    SESSION_HASH_PASSWORD = "session_hash_password",
            SESSION_SELECTED_OUTLET_ID = "session_selected_outlet_id",
            SESSION_SURVEY_INSTANCE_ID = "session_survey_instance_id",
            SESSION_SELECTED_SUPPLIER_ID = "session_selected_supplier_id",
            SESSION_AUTHORIZATION_KEY = "session_authorization_key",
            SESSION_ALARM_FLAG = "session_alarm_flag",
            SESSION_BACKUP_DATE = "session_backup_date";

    public static final long SPLASH_TIME_OUT = 2000;
    public static final Integer SUCCESS = 200;


}
