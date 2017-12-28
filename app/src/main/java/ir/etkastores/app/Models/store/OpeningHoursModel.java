package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/28/17.
 */

public class OpeningHoursModel {

    @SerializedName("MorningStart")
    public String morningStart;

    @SerializedName("MorningFinish")
    public String morningFinish;

    @SerializedName("AfternoonStart")
    public String afternoonStart;

    @SerializedName("AfternoonFinish")
    public String afternoonFinish;

    public String getMorningStart() {
        return morningStart;
    }

    public String getMorningFinish() {
        return morningFinish;
    }

    public String getAfternoonStart() {
        return afternoonStart;
    }

    public String getAfternoonFinish() {
        return afternoonFinish;
    }

}
