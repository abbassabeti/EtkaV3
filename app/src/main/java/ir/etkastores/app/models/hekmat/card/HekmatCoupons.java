package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

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
}
