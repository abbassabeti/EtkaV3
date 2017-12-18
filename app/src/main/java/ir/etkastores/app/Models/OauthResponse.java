package ir.etkastores.app.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 9/3/17.
 */


public class OauthResponse<T> {

    @SerializedName("data")
    public T data;

    @SerializedName("meta")
    public Meta meta;

    public class Meta {

        @SerializedName("statusCode")
        public int statusCode;

        @SerializedName("message")
        public String message;

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }
    }

    public boolean isSuccessful() {
        if (getMeta().getStatusCode() == 200) {
            return true;
        } else {
            return false;
        }

    }

    public T getData() {
        return data;
    }

    public Meta getMeta() {
        return meta;
    }
}
