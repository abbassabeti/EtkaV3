package ir.etkastores.app.models.store;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 12/28/17.
 */

public class StoreModel implements Cloneable {

    @SerializedName("id")
    long id;

    @SerializedName("code")
    String code;

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

    @SerializedName("inStoreModeUrl")
    String inStoreModeUrl;

    @SerializedName("GeofenceArea")
    private int geofenceArea;

    @SerializedName("GeofenceEnabled")
    private boolean geofenceEnabled;

    int ic = -1;

    public static StoreModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, StoreModel.class);
        } catch (Exception err) {
            return null;
        }
    }

    public StoreModel(long id, String code, String name, String parentName, String managerName, String provinceName, ContactInfoModel contactInfo, OpeningHoursModel openingHours, List<FeatureModel> features, double latitude, double longitude, String ranking, String storeImage, String managerImage, String inStoreModeUrl) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.parentName = parentName;
        this.managerName = managerName;
        this.provinceName = provinceName;
        this.contactInfo = contactInfo;
        this.openingHours = openingHours;
        this.features = features;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ranking = ranking;
        this.storeImage = storeImage;
        this.managerImage = managerImage;
        this.inStoreModeUrl = inStoreModeUrl;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
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
        if (ic > 0) return ic;
        switch (getRanking()) {
            case "اتکا ممتاز":
                ic = R.drawable.marker_green;
                return R.drawable.marker_green;

            case "اتکا بازار":
                ic = R.drawable.marker_purple;
                return R.drawable.marker_purple;

            case "اتکا محله":
                ic = R.drawable.marker_blue;
                return R.drawable.marker_blue;

        }
        ic = R.drawable.marker_red;
        return R.drawable.marker_red;
    }

    @Override
    public StoreModel clone() {
        return new StoreModel(id, code, name, parentName, managerName, provinceName, contactInfo, openingHours, features, latitude, longitude, ranking, storeImage, managerImage, inStoreModeUrl);
    }

    public String getInStoreModeUrl() {
        return inStoreModeUrl;
    }

    public boolean hasInStoreMode() {
        return !TextUtils.isEmpty(getInStoreModeUrl());
    }

    public int getGeofenceArea() {
        return geofenceArea;
    }

    public boolean isGeofenceEnabled() {
        return geofenceEnabled;
    }

}
