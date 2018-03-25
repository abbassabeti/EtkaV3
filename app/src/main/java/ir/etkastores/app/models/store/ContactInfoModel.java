package ir.etkastores.app.models.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 12/28/17.
 */

public class ContactInfoModel {

    @SerializedName("address")
    String address;

    @SerializedName("phoneNumbers")
    List<String> phoneNumbers;

    @SerializedName("cellPhoneNumbers")
    List<String> cellPhoneNumbers;

    @SerializedName("managerPhoneNumber")
    String managerPhoneNumber;

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
