package com.classtune.classtuneuni.utils;

import android.content.SharedPreferences;

import com.classtune.classtuneuni.model.User;


/**
 * Created by RR on 13-Nov-18.
 */

public class AppSharedPreference {
    public static final String keyModelTestPrefs = "modelTestPrefs";

    private static final String keyIsFirstTime = "isFirstTime";
    private static final String keyUserName = "username";
    private static final String keyUserPassword = "userpassword";
    private static final String keyApiKey = "api_key";
    private static final String keyRememberMe = "rememberMe";
    private static final String keyUDID = "udid";
    private static final String keyFCMId = "fcm_id";

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences(keyModelTestPrefs, 0);
    }

    public static boolean getUsingFirstTime() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(keyIsFirstTime, true);
    }

    public static void setUsingFirstTime(boolean isFirstTime) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(keyIsFirstTime, isFirstTime);
        editor.apply();
    }

    public static void savePrefBoolean(String key, boolean value) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPrefBoolean(String key) {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(key, false);
    }

    public static void setUserBasicInfo(User user) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();
//
//        if (user.getId() != null)
//            editor.putString(AppConstant.USER_NAME, user.getName());
        editor.putString(AppConstant.USER_EMAIL, user.getEmail());
//        editor.putString(AppConstant.USER_ID, user.getId());
//        editor.putString(AppConstant.USER_IMAGE, user.getImage());
        editor.apply();
    }

    public static User getUserBasicInfo() {
        final SharedPreferences pref = getSharedPreferences();
        User user = new User();

//        user.setName(pref.getString(AppConstant.USER_NAME, ""));
        user.setEmail(pref.getString(AppConstant.USER_EMAIL, ""));
//        user.setId(pref.getString(AppConstant.USER_ID, ""));
//        user.setImage(pref.getString(AppConstant.USER_IMAGE, ""));
        return user;
    }

    public static void setUserNameAndPassword(String userId, String password, String api_key, boolean rememberMe) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyUserName, userId);
        editor.putString(keyUserPassword, password);
        editor.putString(keyApiKey, api_key);
        editor.putBoolean(keyRememberMe, rememberMe);
        editor.apply();
    }



    public static void setUDID(String udid) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyUDID, udid);
        editor.apply();
    }

    public static String getUDID() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUDID, "");
    }

    public static void setFcm(String fcm_id) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyFCMId, fcm_id);
        editor.apply();
    }

    public static String getFcm() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyFCMId, "");
    }

    public static String getUserName() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUserName, "");
    }
    public static boolean getRememberMe() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(keyRememberMe, false);
    }

    public static String getUserPassword() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUserPassword, "");
    }

    public static String getApiKey() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyApiKey, "");
    }

}
