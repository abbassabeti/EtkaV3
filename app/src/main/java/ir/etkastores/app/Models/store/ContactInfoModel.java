package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 12/28/17.
 */

public class ContactInfoModel {

    @SerializedName("Address")
    public String address;

    @SerializedName("PhoneNumbers")
    public List<String> phoneNumbers;

    @SerializedName("CellPhoneNumbers")
    public List<String> cellPhoneNumbers;

    @SerializedName("ManagerPhoneNumber")
    public String managerPhoneNumber;

    public String getAddress() {
        return address;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<String> getCellPhoneNumbers() {
        return cellPhoneNumbers;
    }

    public String getManagerPhoneNumber() {
        return managerPhoneNumber;
    }
}
