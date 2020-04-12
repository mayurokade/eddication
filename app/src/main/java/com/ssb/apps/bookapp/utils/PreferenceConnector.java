package com.ssb.apps.bookapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by swarali on 5/11/17.
 */
public class PreferenceConnector {
    public static final String PREF_NAME = "pref_FarmTrak";
    public static final int MODE = Context.MODE_PRIVATE;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    //   public static final String LANGUAGE_CODE = "lang_code";


    public void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }


    public boolean readBoolean(Context context, String key,
                               boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();
    }

    public int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public void setUserSalt(Context context, String userSalt) {
        writeString(context, Constant.SESSION_USER_SALT, userSalt);
    }

    public String getUserSalt(Context context) {
        return readString(context, Constant.SESSION_USER_SALT, "");
    }

    public void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    //------------------ CLEAR ALL PREFERENCES -----------------------------------------
    public void clearAllPreference(Context context) {
        getEditor(context).clear().apply();
        //setIsAppInstalledFirstTime(context, 1);
    }

    public void setUId(Context context, String userId) {
        writeString(context, Constant.SESSION_U_ID, userId);
    }

    public String getUId(Context context) {

        return readString(context, Constant.SESSION_U_ID, "");
    }

    public void setUserId(Context context, String userId) {
        writeString(context, Constant.SESSION_USER_ID, userId);
    }

    public String getUserId(Context context) {

        return readString(context, Constant.SESSION_USER_ID, "");
    }
}


