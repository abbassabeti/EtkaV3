package ir.etkastores.app.models;

import com.google.gson.annotations.SerializedName;

public class GetVerificationCodeResponse {

    @SerializedName("isNewUser")
    private boolean isNewUser;

    public boolean isNewUser() {
        return isNewUser;
    }

}
