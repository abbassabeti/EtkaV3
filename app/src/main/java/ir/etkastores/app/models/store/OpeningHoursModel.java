package ir.etkastores.app.models.store;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/28/17.
 */

public class OpeningHoursModel {

    @SerializedName("morning")
    String morning;

    @SerializedName("afternoon")
    String afternoon;

    public String getMorning() {
        return morning;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public String getOpeningTime() {
        String result = morning;
        if (!TextUtils.isEmpty(afternoon)) result = result + " \n " + afternoon;
        return result;
    }


}
