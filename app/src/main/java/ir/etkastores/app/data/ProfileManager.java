package ir.etkastores.app.data;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.utils.EtkaPushNotificationConfig;
import ir.etkastores.app.webServices.ApiStatics;

/**
 * Created by Sajad on 2/13/18.
 */

public class ProfileManager {

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
            EtkaPushNotificationConfig.unregisterGuests();
        } catch (Exception err) {
            err.printStackTrace();
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
        if (profile == null) {
            return true;
        } else {
            return false;
        }
    }

    public static void logOut() {
        clearProfile();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PushTokenManager.getInstance().clearLastStates();
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    if (BuildConfig.DEBUG) Log.i("instance id Deleted", "....");
                } catch (Exception err) {
                    if (BuildConfig.DEBUG)
                        Log.i("instanceId delete err", "" + err.getLocalizedMessage());
                }
            }
        }).start();
    }

    public static boolean isFirstRun() {
        if (BuildConfig.DEBUG) return true;
        return !DiskDataHelper.getBool(IS_FIRST_RUN);
    }

    public static void setIsFirstRun(boolean isFirstRun) {
        DiskDataHelper.putBool(IS_FIRST_RUN, !isFirstRun);
    }

}
