package ir.etkastores.app.models.factor;

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

    public String getUserId() {
        return userId;
    }

    public int getTake() {
        return take;
    }

    public int getSkip() {
        return skip;
    }

}
