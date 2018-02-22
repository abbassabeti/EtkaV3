package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 12/28/17.
 */

public class StoreModel {

    @SerializedName("id")
    long id;

    @SerializedName("code")
    long code;

    @SerializedName("name")
    String name;

    @SerializedName("parentName")
    String parentName;

    @SerializedName("managerName")
    String managerName;

    @SerializedName("provinceName")
    String provinceName;

    @SerializedName("contactInfo")
    ContactInfoModel contactInfo;

    @SerializedName("openingHours")
    OpeningHoursModel openingHours;

    @SerializedName("features")
    List<FeatureModel> features;

    @SerializedName("latitude")
    double latitude;

    @SerializedName("longitude")
    double longitude;

    @SerializedName("ranking")
    String ranking;

    @SerializedName("storeImage")
    String storeImage;

    @SerializedName("managerImage")
    String managerImage;

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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
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

    public int getIcon() {
        switch (getRanking()) {
            case "اتکا ممتاز":
                return R.drawable.marker_green;

            case "اتکا بازار":
                return R.drawable.marker_purple;

            case "اتکا محله":
                return R.drawable.marker_blue;

        }
        return R.drawable.marker_red;
    }

}
