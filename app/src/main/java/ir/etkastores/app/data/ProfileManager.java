package ir.etkastores.app.data;

import android.text.TextUtils;

import com.google.gson.Gson;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.Utils.DiskDataHelper;

/**
 * Created by Sajad on 2/13/18.
 */

public class ProfileManager {

    private static final String GUEST_USER_NAME = "GuestUser";
    private static final String GUEST_USER_PASSWORD = "Et_#usj78Se";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PASSWORD = "USER_PASSWORD";
    private final static String PROFILE_KEY = "PROFILE_KEY";

    private static UserProfileModel profileModel;

    public static UserProfileModel getProfile(){
        initProfile();
        return profileModel;
    }

    public static void saveProfile(UserProfileModel model){
        try {
            String str = new Gson().toJson(model);
            EtkaApp.getPreference().edit().putString(PROFILE_KEY,str).apply();
            profileModel = model;
        }catch (Exception err){}
    }

    public static boolean hasSavedProfile(){
        initProfile();
        if (profileModel == null){
            return false;
        }else{
            return true;
        }
    }

    private static void initProfile(){
        if (profileModel != null) return;
        try {
            profileModel = new Gson().fromJson(EtkaApp.getPreference().getString(PROFILE_KEY,null),UserProfileModel.class);
        }catch (Exception err){
            profileModel = null;
        }
    }

    public static void clearProfile(){
        try {
            EtkaApp.getPreference().edit().remove(PROFILE_KEY).apply();
        }catch (Exception err){

        }
    }

    public static boolean isGuest(){
        UserProfileModel profile = getProfile();
        if (profile != null && !TextUtils.isEmpty(profile.getUserName()) && !profile.getUserName().contentEquals(GUEST_USER_NAME)){
            return false;
        }else{
            return true;
        }
    }

    public static void logOut(){

    }

    public static String getUserName(){
        if (isGuest()){
            return GUEST_USER_NAME;
        }else{
            return DiskDataHelper.getString(USER_NAME);
        }
    }

    public static String getUserPassword(){
        if (isGuest()){
            return GUEST_USER_PASSWORD;
        }else{
            return DiskDataHelper.getString(USER_PASSWORD);
        }
    }

    public static void saveUserNameAndPassword(String userName, String password){
        DiskDataHelper.putString(USER_NAME,userName);
        DiskDataHelper.putString(USER_PASSWORD,password);
    }

}
