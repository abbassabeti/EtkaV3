package ir.etkastores.app.data;

import com.google.gson.Gson;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.Models.UserProfileModel;

/**
 * Created by Sajad on 2/13/18.
 */

public class ProfileManager {

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

    public static boolean isLogin(){
        return false;
    }

    public static void logOut(){

    }

}
