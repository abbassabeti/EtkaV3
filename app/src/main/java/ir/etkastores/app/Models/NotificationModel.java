package ir.etkastores.app.Models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 2/14/18.
 */

public class NotificationModel {

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

    public static NotificationModel fromJson(String json){
        try {
            return  new Gson().fromJson(json,NotificationModel.class);
        }catch (Exception err){
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

}
