package ir.etkastores.app.Models;

import com.google.gson.annotations.SerializedName;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 9/8/17.
 */

public class UserProfileModel {

    @SerializedName("userName")
    String userName;

    @SerializedName("email")
    String email;

    @SerializedName("totalPoints")
    long totalPoints;

    @SerializedName("remainingPoints")
    long remainingPoints;

    @SerializedName("invitationCode")
    String invitationCode;

    @SerializedName("crmUserId")
    String crmUserId;

    @SerializedName("id")
    String id;

    @SerializedName("firstName")
    String firstName;

    @SerializedName("lastName")
    String lastName;

    @SerializedName("cellPhone")
    String cellPhone;

    @SerializedName("address")
    String address;

    @SerializedName("gender")
    int gender;

    @SerializedName("education")
    String education;

    @SerializedName("birthDate")
    String birthDate;

    @SerializedName("hekamtCardCode")
    String hekamtCardCode;

    @SerializedName("nationalCode")
    String nationalCode;

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public long getRemainingPoints() {
        return remainingPoints;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public String getCrmUserId() {
        return crmUserId;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getAddress() {
        return address;
    }

    public int getGender() {
        return gender;
    }

    public String getEducation() {
        return education;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getHekamtCardCode() {
        return hekamtCardCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTotalPoints(long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setRemainingPoints(long remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public void setCrmUserId(String crmUserId) {
        this.crmUserId = crmUserId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setHekamtCardCode(String hekamtCardCode) {
        this.hekamtCardCode = hekamtCardCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getFirstNameAndLastName(){
        return firstName+" "+lastName;
    }

    public String getGenderValue(){
        if (getGender() == 0){
            return EtkaApp.getInstnace().getResources().getString(R.string.male);
        }else{
            return EtkaApp.getInstnace().getResources().getString(R.string.female);
        }
    }

}
