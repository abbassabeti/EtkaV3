package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

public class HekmatSetPasswordRequest {

    @SerializedName("PAN")
    public String PAN;

    @SerializedName("EmployeeNumber")
    public String EmployeeNumber;

    @SerializedName("NewPassword")
    public String NewPassword;

    @SerializedName("ConfirmPassword")
    public String ConfirmPassword;

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public void setEmployeeNumber(String employeeNumber) {
        EmployeeNumber = employeeNumber;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
