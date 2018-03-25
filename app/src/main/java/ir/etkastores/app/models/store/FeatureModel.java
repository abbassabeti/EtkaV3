package ir.etkastores.app.models.store;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/28/17.
 */

public class FeatureModel {

    @SerializedName("name")
    String name;

    @SerializedName("value")
    String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
