package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HekmatRemainingsModel {

    @SerializedName("remainDebit")
    private String remainDebit;

    @SerializedName("remainCredit")
    private String remainCredit;

    @SerializedName("coupons")
    private List<HekmatCoupons> coupons;

    public String getRemainDebit() {
        return remainDebit;
    }

    public String getRemainCredit() {
        return remainCredit;
    }

    public List<HekmatCoupons> getCoupons() {
        return coupons;
    }
}
