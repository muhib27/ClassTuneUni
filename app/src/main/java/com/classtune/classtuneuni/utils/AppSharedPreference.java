package com.classtune.classtuneuni.utils;

import android.content.SharedPreferences;

import com.classtune.classtuneuni.assignment.AssignmentSection;
import com.classtune.classtuneuni.model.User;
import com.classtune.classtuneuni.response.StCourseSection;


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
    private static final String keyUserType = "userType";

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

    public static void setUserNameAndPassword(String userId, String password, String api_key, boolean rememberMe, String userType) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putString(keyUserName, userId);
        editor.putString(keyUserPassword, password);
        editor.putString(keyApiKey, api_key);
        editor.putString(keyUserType, userType);
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

    public static String getUserType() {
        final SharedPreferences pref = getSharedPreferences();
        return pref.getString(keyUserType, "");
    }


    private static final String COURSE_ID = "course_id";
    private static final String COURSE_OFFER_SECTION_ID = "offered_section_id";

    public static void setUserTab(AssignmentSection assignmentSection, int i) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();
        String key = COURSE_ID + assignmentSection.getCourseCode() + i;
        String key1 = COURSE_OFFER_SECTION_ID + assignmentSection.getCourseCode() + i;

        editor.putString(key, assignmentSection.getCourseId());
        editor.putString(key1, assignmentSection.getOfferedSectionId());

        editor.apply();


    }
    public static void setStUserTab(StCourseSection stCourseSection, int i) {
        final SharedPreferences pref = getSharedPreferences();
        final SharedPreferences.Editor editor = pref.edit();
        String key = COURSE_ID + stCourseSection.getCourseCode() + i;
        String key1 = COURSE_OFFER_SECTION_ID + stCourseSection.getCourseCode() + i;

        editor.putString(key, stCourseSection.getCourseCode());
        editor.putString(key1, stCourseSection.getCourseOfferSectionId());

        editor.apply();


    }
    public static StCourseSection getStUserTab(String tabTag, int pos) {
        final SharedPreferences pref = getSharedPreferences();
        StCourseSection stCourseSection = new StCourseSection();
        String key = COURSE_ID + tabTag + pos;
        String key1 = COURSE_OFFER_SECTION_ID + tabTag + pos;

        stCourseSection.setCourseCode(pref.getString(COURSE_ID + tabTag + pos, ""));
        stCourseSection.setCourseOfferSectionId(pref.getString(COURSE_OFFER_SECTION_ID + tabTag + pos, ""));

        return stCourseSection;
    }

    public static AssignmentSection getUserTab(String tabTag, int pos) {
        final SharedPreferences pref = getSharedPreferences();
        AssignmentSection assignmentSection = new AssignmentSection();
        String key = COURSE_ID + tabTag + pos;
        String key1 = COURSE_OFFER_SECTION_ID + tabTag + pos;

        assignmentSection.setCourseId(pref.getString(COURSE_ID + tabTag + pos, ""));
        assignmentSection.setOfferedSectionId(pref.getString(COURSE_OFFER_SECTION_ID + tabTag + pos, ""));
     //   assignmentSection.setOfferedSectionId(COURSE_OFFER_SECTION_ID + tabTag, "");
//        user.setName(pref.getString(AppConstant.USER_NAME, ""));
//        user.setEmail(pref.getString(AppConstant.USER_EMAIL, ""));
//        user.setId(pref.getString(AppConstant.USER_ID, ""));
//        user.setImage(pref.getString(AppConstant.USER_IMAGE, ""));
        return assignmentSection;
    }
}
