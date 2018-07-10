package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

public class HekmatChangePasswordRequest {

    @SerializedName("OldPassword")
    String OldPassword;

    @SerializedName("NewPassword")
    String NewPassword;

    @SerializedName("ConfirmNewPassword")
    String ConfirmNewPassword;

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        ConfirmNewPassword = confirmNewPassword;
    }

}
