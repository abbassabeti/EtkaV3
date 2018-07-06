package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

public class HekmatRegisterRequest {

    @SerializedName("PhoneNumber")
    String phoneNumber;

    @SerializedName("PAN")
    String PAN;

    @SerializedName("EmployeeNumber")
    String employeeNumber;

    @SerializedName("Password")
    String password;

    @SerializedName("ConfirmPassword")
    String confirmPassword;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPAN() {
        return PAN;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
