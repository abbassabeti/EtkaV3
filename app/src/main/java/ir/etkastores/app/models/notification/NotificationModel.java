package ir.etkastores.app.models.notification;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import ir.etkastores.app.activities.SplashActivity;
import ir.etkastores.app.EtkaApp;

/**
 * Created by Sajad on 2/14/18.
 */

public class NotificationModel {

    public final static String IS_FROM_NOTIFICATION = "IS_FROM_NOTIFICATION";
    public final static String NOTIFICATION_OBJECT = "NOTIFICATION_OBJECT";

    public final static int ACTION_OPEN_APP = 0;
    public final static int ACTION_OPEN_URL = 1;
    public final static int ACTION_OPEN_HEKMAT_PRODUCTS = 2;
    public final static int ACTION_OPEN_SEARCH = 3;
    public final static int ACTION_OPEN_MAP = 4;
    public final static int ACTION_OPEN_PROFILE = 5;
    public final static int ACTION_OPEN_PRODUCT = 6;
    public final static int ACTION_OPEN_FAQ = 7;
    public final static int ACTION_OPEN_EDIT_PROFILE = 8;
    public final static int ACTION_OPEN_CONSUME_YOUR_POINTS = 9;
    public final static int ACTION_OPEN_HEKMAT_ACCOUNT = 10;
    public final static int ACTION_OPEN_NEXT_SHOPPING = 11;
    public final static int ACTION_OPEN_SHOPPING_HISTORY = 12;
    public final static int ACTION_OPEN_INVITE_FRIENDS = 13;
    public final static int ACTION_OPEN_SUPPORT = 14;
    public final static int ACTION_OPEN_TICKET = 15;
    public final static int ACTION_OPEN_NEW_TICKET = 16;
    public final static int ACTION_OPEN_PROFILE_SETTING = 17;
    public final static int ACTION_OPEN_ABOUT_ETKA_STORES = 18;
    public final static int ACTION_OPEN_USER_PRIVACY = 19;
    public final static int ACTION_OPEN_TERM_OF_USE = 20;
    public final static int ACTION_OPEN_STORE_PROFILE = 21;
    public final static int ACTION_NEWS_LIST = 22;
    public final static int ACTION_NEWS = 23;
    public final static int ACTION_GALLERY = 24;

    public static NotificationModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, NotificationModel.class);
        } catch (Exception err) {
            return null;
        }
    }

    @SerializedName("title")
    String title;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    String data;

    @SerializedName("action")
    int action;

    @SerializedName("id")
    int id;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public int getAction() {
        return action;
    }

    public Intent getIntent() {
        Intent intent = null;

        if (getAction() == ACTION_OPEN_URL){
            intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(getData()));
        }else{
            intent = getAppIntent();
        }

        return intent;
    }

    private Intent getAppIntent(){
        Intent intent = new Intent(EtkaApp.getInstance(), SplashActivity.class);
        intent.putExtra(IS_FROM_NOTIFICATION,true);
        intent.putExtra(NOTIFICATION_OBJECT,toJson());
        return intent;
    }

    public String toJson(){
        try {
            return new Gson().toJson(this);
        }catch (Exception err){
            return "";
        }
    }

}
