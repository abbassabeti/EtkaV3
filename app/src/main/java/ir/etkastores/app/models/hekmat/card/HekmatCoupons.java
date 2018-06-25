package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.etkastores.app.utils.procalendar.XCalendar;

public class HekmatCoupons {

    @SerializedName("couponNumber")
    private int couponNumber;

    @SerializedName("couponTitle")
    private String couponTitle;

    @SerializedName("announcedDateTime")
    private String announcedDateTime;

    @SerializedName("expiredDateTime")
    private String expiredDateTime;

    @SerializedName("type")
    private String type;

    public int getCouponNumber() {
        return couponNumber;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public String getAnnouncedDateTime() {
        return announcedDateTime;
    }

    public String getExpiredDateTime() {
        return expiredDateTime;
    }

    public String getType() {
        return type;
    }

    public String getFormattedStart(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = sdf.parse(announcedDateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            XCalendar c = XCalendar.fromGerigorian(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            return c.getCalendar(XCalendar.JalaliType).getDateNumerical("/");
        }catch (Exception err){
            return "--";
        }
    }

    public String getFormattedEnd(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = sdf.parse(expiredDateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            XCalendar c = XCalendar.fromGerigorian(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            return c.getCalendar(XCalendar.JalaliType).getDateNumerical("/");
        }catch (Exception err){
            return "--";
        }
    }

}
