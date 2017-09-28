package ir.etkastores.app.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 9/8/17.
 */

public class UserProfileModel {

    @SerializedName("Id")
    private String id;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("CellPhone")
    private String cellPhone;

    @SerializedName("Address")
    private String address;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("Education")
    private String education;

    @SerializedName("BirhtDate")
    private String birhtDate;

    @SerializedName("HekmatCard")
    private String hekmatCard;

    @SerializedName("NationalCode")
    private String nationalCode;

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

    public String getGender() {
        return gender;
    }

    public String getEducation() {
        return education;
    }

    public String getBirhtDate() {
        return birhtDate;
    }

    public String getHekmatCard() {
        return hekmatCard;
    }

    public String getNationalCode() {
        return nationalCode;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setBirhtDate(String birhtDate) {
        this.birhtDate = birhtDate;
    }

    public void setHekmatCard(String hekmatCard) {
        this.hekmatCard = hekmatCard;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
