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
    public final static String ACTION_CODE = "ACTION_CODE";
    public final static String DATA = "DATA";

    public final static int OPEN_APP = 0;
    public final static int OPEN_URL = 1;
    public final static int OPEN_HEKMAT_PRODUCTS = 2;
    public final static int OPEN_SEARCH = 3;
    public final static int OPEN_MAP = 4;
    public final static int OPEN_PROFILE = 5;
    public final static int OPEN_PRODUCT = 6;
    public final static int OPEN_FAQ = 7;
    public final static int OPEN_EDIT_PROFILE = 8;
    public final static int OPEN_EDIT_YOUR_POINTS = 9;
    public final static int OPEN_HEKMAT_ACCOUNT = 10;
    public final static int OPEN_NEXT_SHOPPING = 11;
    public final static int OPEN_SHOPPING_HISTORY = 12;
    public final static int OPEN_INVITE_FRIENDS = 13;
    public final static int OPEN_SUPPORT = 14;
    public final static int OPEN_TICKET = 15;
    public final static int OPEN_NEW_TICKET = 16;
    public final static int OPEN_PROFILE_SETTING = 17;
    public final static int OPEN_ABOUT_ETKA_STORES = 18;
    public final static int OPEN_USER_PRIVACY = 19;
    public final static int OPEN_TERM_OF_USE = 20;
    public final static int OPEN_STORE_PROFILE = 21;

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
        switch (getAction()) {

            case OPEN_APP: {
                intent = getAppIntent();
                break;
            }

            case OPEN_URL: {
                intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(getData()));
                break;
            }

            case OPEN_HEKMAT_PRODUCTS: {
                intent = getAppIntent();
                intent.putExtra(DATA,getData());
                break;
            }

            case OPEN_SEARCH: {
                intent = getAppIntent();
                intent.putExtra(DATA,getData());
                break;
            }

            case OPEN_MAP: {
                intent = getAppIntent();
                intent.putExtra(DATA,getData());
                break;
            }

            case OPEN_PROFILE: {
                intent = getAppIntent();
                break;
            }

            case OPEN_PRODUCT: {
                intent = getAppIntent();
                intent.putExtra(DATA,getData());
                break;
            }

            case OPEN_FAQ: {
                intent = getAppIntent();
                break;
            }

            case OPEN_EDIT_PROFILE: {
                intent = getAppIntent();
                break;
            }

            case OPEN_EDIT_YOUR_POINTS: {
                intent = getAppIntent();
                break;
            }

            case OPEN_HEKMAT_ACCOUNT: {
                intent = getAppIntent();
                break;
            }

            case OPEN_NEXT_SHOPPING: {
                intent = getAppIntent();
                break;
            }

            case OPEN_SHOPPING_HISTORY: {
                intent = getAppIntent();
                break;
            }

            case OPEN_INVITE_FRIENDS: {
                intent = getAppIntent();
                break;
            }

            case OPEN_SUPPORT: {
                intent = getAppIntent();
                break;
            }

            case OPEN_TICKET: {
                intent = getAppIntent();
                intent.putExtra(DATA,getData());
                break;
            }

            case OPEN_NEW_TICKET: {
                intent = getAppIntent();
                break;
            }

            case OPEN_PROFILE_SETTING: {
                intent = getAppIntent();
                break;
            }

            case OPEN_ABOUT_ETKA_STORES: {
                intent = getAppIntent();
                break;
            }

            case OPEN_USER_PRIVACY: {
                intent = getAppIntent();
                break;
            }

            case OPEN_TERM_OF_USE: {
                intent = getAppIntent();
                break;
            }

            case OPEN_STORE_PROFILE: {
                intent = getAppIntent();
                break;
            }

        }
        return intent;
    }

    private Intent getAppIntent(){
        Intent intent = new Intent(EtkaApp.getInstance(), SplashActivity.class);
        intent.putExtra(IS_FROM_NOTIFICATION,true);
        intent.putExtra(ACTION_CODE,getAction());
        return intent;
    }

}
