package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 12/28/17.
 */

public class StoreModel {

    @SerializedName("Id")
    public long id;

    @SerializedName("Code")
    public long code;

    @SerializedName("Name")
    public String name;

    @SerializedName("ParentName")
    public String parentName;

    @SerializedName("ManagerName")
    public String managerName;

    @SerializedName("ProvinceName")
    public String provinceName;

    @SerializedName("ContactInfo")
    public ContactInfoModel contactInfo;

    @SerializedName("OpeningHours")
    public OpeningHoursModel openingHours;

    @SerializedName("Features")
    public List<FeatureModel> features;

    @SerializedName("Latitude")
    public long latitude;

    @SerializedName("Longitude")
    public long longitude;

    @SerializedName("Ranking")
    public String ranking;

    @SerializedName("StoreImage")
    public String storeImage;

    @SerializedName("ManagerImage")
    public String managerImage;

    public long getId() {
        return id;
    }

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public ContactInfoModel getContactInfo() {
        return contactInfo;
    }

    public OpeningHoursModel getOpeningHours() {
        return openingHours;
    }

    public List<FeatureModel> getFeatures() {
        return features;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getRanking() {
        return ranking;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public String getManagerImage() {
        return managerImage;
    }

}
