package ir.etkastores.app.utils;

import android.content.Context;

import com.google.gson.Gson;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.webServices.AccessToken;

/**
 * Created by Sajad on 9/8/17.
 */

public class DiskDataHelper {

    public static void putString(String key, String value) {
        EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putString(key, value)
                .apply();
    }

    public static String getString(String key) {
        return EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getString(key, null);
    }

    public static void putInt(String key, int value) {
        EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putInt(key, value)
                .apply();
    }

    public static int getInt(String key) {
        return EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getInt(key, 0);
    }

    public static void putBool(String key, boolean value) {
        EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE).edit()
                .putBoolean(key, value)
                .apply();
    }

    public static boolean getBool(String key) {
        return EtkaApp.getInstance().getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                .getBoolean(key, false);
    }


    // Custom Methods

    private final static String LAST_TOKEN_KEY = "LAST_TOKEN_KEY";

    public static void saveLastToken(AccessToken token) {
        if (token != null) {
            putString(LAST_TOKEN_KEY, new Gson().toJson(token));
        } else {
            putString(LAST_TOKEN_KEY, "");
        }
    }

    public static AccessToken getLastToken() {
        try {
            return new Gson().fromJson(getString(LAST_TOKEN_KEY), AccessToken.class);
        } catch (Exception err) {
            return null;
        }
    }

    private final static String FORCE_UPDATE_VERSION = "FORCE_UPDATE_VERSION";
    private final static String FORCE_UPDATE_URL = "FORCE_UPDATE_URL";

    public static int getForceUpdateVersion() {
        return getInt(FORCE_UPDATE_VERSION);
    }

    public static void setMinForceUpdateVersion(int version) {
        putInt(FORCE_UPDATE_VERSION, version);
    }

    public static void setForceUpdateUrl(String url){
        putString(FORCE_UPDATE_URL,url);
    }

    public static String getForceUpdateUrl() {
        return getString(FORCE_UPDATE_URL);
    }

}
