package ir.etkastores.app.Utils;

import android.content.Context;

import com.google.gson.Gson;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.WebService.TokenResponse;

/**
 * Created by Sajad on 9/8/17.
 */

public class DiskDataHelper {

    public static void putString(String key, String value) {
        EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putString(key,value)
                .apply();
    }

    public static String getString(String key) {
        return EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getString(key,null);
    }

    public static void putInt(String key, int value) {
        EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putInt(key,value)
                .apply();
    }

    public static int getInt(String key) {
        return EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getInt(key,0);
    }

    public static void putBool(String key, boolean value) {
        EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putBoolean(key,value)
                .apply();
    }

    public static boolean getBool(String key) {
        return EtkaApp.getInstnace().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getBoolean(key,false);
    }


    // Custom Methods

    private final static String LAST_TOKEN_KEY = "LAST_TOKEN_KEY";
    private final static String USER_PROFILE_KEY = "USER_PROFILE_KEY";

    public static void saveLastToken(TokenResponse token){
        if (token!=null){
            putString(LAST_TOKEN_KEY,new Gson().toJson(token));
        }
    }

    public static TokenResponse getLastToken(){
        try {
            return new Gson().fromJson(getString(LAST_TOKEN_KEY),TokenResponse.class);
        }catch (Exception err){
            return null;
        }
    }

    public static void saveUserProfiel(UserProfileModel profileModel){
        if (profileModel!=null){
            putString(USER_PROFILE_KEY,new Gson().toJson(profileModel));
        }else{
            putString(USER_PROFILE_KEY,"");
        }
    }

    public static UserProfileModel getUserProfileModel(){
        try {
            return new Gson().fromJson(getString(USER_PROFILE_KEY),UserProfileModel.class);
        }catch (Exception err){
            return null;
        }
    }



}
