package ir.etkastores.app.Models.store;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 12/28/17.
 */

public class OpeningHoursModel {

    @SerializedName("morningStart")
    String morningStart;

    @SerializedName("morningFinish")
    String morningFinish;

    @SerializedName("afternoonStart")
    String afternoonStart;

    @SerializedName("afternoonFinish")
    String afternoonFinish;

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
