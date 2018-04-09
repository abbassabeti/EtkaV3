package ir.etkastores.app.models;

import com.google.gson.annotations.SerializedName;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 9/3/17.
 */


public class OauthResponse<T> {

    @SerializedName("data")
    T data;

    @SerializedName("meta")
    Meta meta;

    public static class Meta {

        @SerializedName("statusCode")
        int statusCode;

        @SerializedName("message")
        String message;

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

    }

    public boolean isSuccessful() {
        checkNullMeta();
        if (getMeta().getStatusCode() == 200) {
            return true;
        } else {
            return false;
        }

    }

    public T getData() {
        checkNullMeta();
        return data;
    }

    public Meta getMeta() {
        checkNullMeta();
        return meta;
    }

    private void checkNullMeta(){
        if (meta == null){
            meta = new Meta();
            meta.statusCode = -1;
            meta.message = EtkaApp.getInstance().getResources().getString(R.string.errorInConnectingToServer);
        }
    }

}
