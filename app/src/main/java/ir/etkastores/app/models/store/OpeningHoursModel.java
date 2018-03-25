package ir.etkastores.app.models.store;

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

    public String getOpeningTime(){
        return morning +" \n "+afternoon;
    }


}
