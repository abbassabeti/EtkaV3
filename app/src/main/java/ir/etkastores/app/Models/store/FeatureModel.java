package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/28/17.
 */

public class FeatureModel {

    @SerializedName("Name")
    String name;

    @SerializedName("Value")
    String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
