package ir.etkastores.app.models.store;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 12/28/17.
 */

public class StoreModel implements Cloneable {

    @SerializedName("id")
    private long id;

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String name;

    @SerializedName("parentName")
    private String parentName;

    @SerializedName("managerName")
    private String managerName;

    @SerializedName("provinceName")
    private String provinceName;

    @SerializedName("contactInfo")
    private ContactInfoModel contactInfo;

    @SerializedName("openingHours")
    private OpeningHoursModel openingHours;

    @SerializedName("features")
    private List<FeatureModel> features;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("ranking")
    private String ranking;

    @SerializedName("storeImage")
    private String storeImage;

    @SerializedName("managerImage")
    private String managerImage;

    @SerializedName("GeofenceArea")
    private int geofenceArea;

    @SerializedName("GeofenceEnabled")
    private boolean geofenceEnabled;

    @SerializedName("HasInStoreMap")
    private boolean hasInStoreMap;

    @SerializedName("HasInStoreOffer")
    private boolean hasInStoreOffer;

    @SerializedName("HasInStoreSurvey")
    private boolean hasInStoreSurvey;

    int ic = -1;

    public static StoreModel fromJson(String json) {
        try {
            return new Gson().fromJson(json, StoreModel.class);
        } catch (Exception err) {
            return null;
        }
    }

    public StoreModel(long id, String code, String name, String parentName, String managerName, String provinceName, ContactInfoModel contactInfo, OpeningHoursModel openingHours, List<FeatureModel> features, double latitude, double longitude, String ranking, String storeImage, String managerImage, boolean hasInStoreMap, boolean hasInStoreOffer, boolean hasInStoreSurvey) {
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
        this.hasInStoreMap = hasInStoreMap;
        this.hasInStoreOffer = hasInStoreOffer;
        this.hasInStoreSurvey = hasInStoreSurvey;
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
        return new StoreModel(id, code, name, parentName, managerName, provinceName, contactInfo, openingHours, features, latitude, longitude, ranking, storeImage, managerImage, hasInStoreMap, hasInStoreOffer, hasInStoreSurvey);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public int getGeofenceArea() {
        return geofenceArea;
    }

    public boolean isGeofenceEnabled() {
        return geofenceEnabled;
    }

    public boolean isHasInStoreMap() {
        return hasInStoreMap;
    }

    public boolean isHasInStoreOffer() {
        return hasInStoreOffer;
    }

    public boolean isHasInStoreSurvey() {
        return hasInStoreSurvey;
    }
}
