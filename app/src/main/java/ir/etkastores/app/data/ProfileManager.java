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

    private static ProfileManager instance;

    private ProfileManager() {

    }

    synchronized public static ProfileManager getInstance() {
        if (instance == null) instance = new ProfileManager();
        return instance;
    }

    private UserProfileModel profileModel;

    public UserProfileModel getProfile() {
        initProfile();
        return profileModel;
    }

    public void saveProfile(UserProfileModel model) {
        try {
            String str = new Gson().toJson(model);
            EtkaApp.getPreference().edit().putString(PROFILE_KEY, str).apply();
            profileModel = model;
            EtkaPushNotificationConfig.unSubscribeGuests();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public boolean hasSavedProfile() {
        if (getProfile() == null) {
            return false;
        } else {
            return true;
        }
    }

    private void initProfile() {
        if (profileModel != null) return;
        try {
            profileModel = new Gson().fromJson(EtkaApp.getPreference().getString(PROFILE_KEY, null), UserProfileModel.class);
        } catch (Exception err) {
            profileModel = null;
        }
    }

    public void clearProfile() {
        try {
            EtkaApp.getPreference().edit().remove(PROFILE_KEY).apply();
            ApiStatics.saveToken(null);
            DiskDataHelper.setLastHekmatCardNumber("");
            profileModel = null;
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public boolean isGuest() {
        UserProfileModel profile = getProfile();
        if (profile == null) {
            return true;
        } else {
            return false;
        }
    }

    public void logOut() {
        clearProfile();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    PushTokenManager.getInstance().clearLastStates();
                    if (BuildConfig.DEBUG) Log.i("instance id Deleted", "....");
                } catch (Exception err) {
                    if (BuildConfig.DEBUG)
                        Log.i("instanceId delete err", "" + err.getLocalizedMessage());
                }
            }
        }).start();
    }

    public boolean isFirstRun() {
        if (BuildConfig.DEBUG) return true;
        return !DiskDataHelper.getBool(IS_FIRST_RUN);
    }

    public void setIsFirstRun(boolean isFirstRun) {
        DiskDataHelper.putBool(IS_FIRST_RUN, !isFirstRun);
    }

}
