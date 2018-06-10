package ir.etkastores.app.data;

import android.text.TextUtils;

import com.google.gson.Gson;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.webServices.ApiStatics;

/**
 * Created by Sajad on 2/13/18.
 */

public class ProfileManager {

    public static final String GUEST_USER_NAME = "GuestUser";
    public static final String GUEST_USER_PASSWORD = "Et_#usj78Se";
//    private static final String USER_NAME = "USER_NAME";
//    private static final String USER_PASSWORD = "USER_PASSWORD";
    private final static String PROFILE_KEY = "PROFILE_KEY";
    private final static String IS_FIRST_RUN = "IS_FIRST_RUN";

    private static UserProfileModel profileModel;

    public static UserProfileModel getProfile() {
        initProfile();
        return profileModel;
    }

    public static void saveProfile(UserProfileModel model) {
        try {
            String str = new Gson().toJson(model);
            EtkaApp.getPreference().edit().putString(PROFILE_KEY, str).apply();
            profileModel = model;
        } catch (Exception err) {
        }
    }

    public static boolean hasSavedProfile() {
        initProfile();
        if (profileModel == null) {
            return false;
        } else {
            return true;
        }
    }

    private static void initProfile() {
        if (profileModel != null) return;
        try {
            profileModel = new Gson().fromJson(EtkaApp.getPreference().getString(PROFILE_KEY, null), UserProfileModel.class);
        } catch (Exception err) {
            profileModel = null;
        }
    }

    public static void clearProfile() {
        try {
            EtkaApp.getPreference().edit().remove(PROFILE_KEY).apply();
            ApiStatics.saveToken(null);
            profileModel = null;
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static boolean isGuest() {
        UserProfileModel profile = getProfile();
        if (profile == null){
            return true;
        }else{
            return false;
        }
    }

    public static void logOut() {
//        saveUserNameAndPassword("","");
        clearProfile();
    }

//    public static String getUserName() {
//        if (isGuest()) {
//            return GUEST_USER_NAME;
//        } else {
//            return DiskDataHelper.getString(USER_NAME);
//        }
//    }
//
//    public static String getUserPassword() {
//        if (isGuest()) {
//            return GUEST_USER_PASSWORD;
//        } else {
//            return DiskDataHelper.getString(USER_PASSWORD);
//        }
//    }

//    public static void saveUserNameAndPassword(String userName, String password) {
//        DiskDataHelper.putString(USER_NAME, userName);
//        DiskDataHelper.putString(USER_PASSWORD, password);
//    }

    public static boolean isFirstRun() {
        if (BuildConfig.DEBUG) return true;
        return !DiskDataHelper.getBool(IS_FIRST_RUN);
    }

    public static void setIsFirstRun(boolean isFirstRun) {
        DiskDataHelper.putBool(IS_FIRST_RUN, !isFirstRun);
    }

}
