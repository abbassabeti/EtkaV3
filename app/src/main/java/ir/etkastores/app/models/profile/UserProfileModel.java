package ir.etkastores.app.models.profile;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.procalendar.XCalendar;
import ir.etkastores.app.utils.procalendar.repositories.CalendarRepoInterface;

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
    Integer gender;

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

    public Integer getGender() {
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
        if (getGender() == null) return "-";
        if (getGender() == 0){
            return EtkaApp.getInstance().getResources().getString(R.string.male);
        }else{
            return EtkaApp.getInstance().getResources().getString(R.string.female);
        }
    }

    public XCalendar getBirthDateXCalendar(){
        try {
            String dt = getBirthDate();
            if (dt.contains("T")){
                dt = dt.substring(0,dt.indexOf("T")-1);
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dt);
            XCalendar xCalendar = new XCalendar(date.getTime());
            CalendarRepoInterface c = xCalendar.getCalendar(XCalendar.GregorianType);
            if (c.getYear() == 1){
                return null;
            }else{
                return xCalendar;
            }
        }catch (Exception err){
            return null;
        }
    }

    public static class EducationItems{
        public static final String Illiterate = "Illiterate";
        public static final String Diploma = "Diploma";
        public static final String AssociateDegree = "AssociateDegree";
        public static final String Bachelor = "Bachelor";
        public static final String Master = "Master";
        public static final String PHD = "PHD";
    }

    public static String translateEducation(String education){
        switch (education){
            case EducationItems.Illiterate:
                return "زیر دیپلم";

            case EducationItems.Diploma:
                return "دیپلم";

            case EducationItems.AssociateDegree:
                return "فوق دیپلم";

            case EducationItems.Bachelor:
                return "لیسانس";

            case EducationItems.Master:
                return "فوق لیسانس";

            case EducationItems.PHD:
                return "دکتری و بالاتر";

        }
        return "";
    }

}
