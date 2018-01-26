package ir.etkastores.app.Models.Factor;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 1/13/18.
 */

public class FactorRequestModel {

    @SerializedName("UserId")
    String userId;

    @SerializedName("Take")
    int take;

    @SerializedName("Skip")
    int skip;

    public FactorRequestModel() {
        take = 10;
        skip = 0;
    }

    public FactorRequestModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public FactorRequestModel setTake(int take) {
        this.take = take;
        return this;
    }

    public FactorRequestModel setSkip(int skip) {
        this.skip = skip;
        return this;
    }

}
