package ir.etkastores.app.models;

import com.google.gson.annotations.SerializedName;

public class ErrorModel {

    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }

}
