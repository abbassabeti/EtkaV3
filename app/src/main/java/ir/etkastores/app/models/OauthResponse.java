package ir.etkastores.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sajad on 9/3/17.
 */


public class OauthResponse<T> {

    @SerializedName("data")
    T data;

    @SerializedName("meta")
    Meta meta;

    public class Meta {

        @SerializedName("statusCode")
        int statusCode;

        @SerializedName("message")
        String message;

        @SerializedName("errors")
        List<String> errors;

        public int getStatusCode() {
            return statusCode;
        }

        public String getMessage() {
            return message;
        }

        public String getErrorsMessage(){
            return Arrays.toString(errors.toArray());
        }

        public List<String> getErrors(){
            return errors;
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
