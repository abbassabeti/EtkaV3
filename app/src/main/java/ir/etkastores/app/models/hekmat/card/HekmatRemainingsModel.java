package ir.etkastores.app.models.hekmat.card;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HekmatRemainingsModel {

    // saghfe etebar
    @Expose
    @SerializedName("maxCredit")
    private String maxCredit;

    // mojodi bone riali
    @Expose
    @SerializedName("remainDebit")
    private String remainDebit;

    //mojodi etebar
    @Expose
    @SerializedName("remainCredit")
    private String remainCredit;

    //kalabarg haye daryaft nashode
    @Expose
    @SerializedName("coupons")
    private List<HekmatCoupons> coupons;

    public String getRemainDebit() {
        return remainDebit;
    }

    public String getRemainCredit() {
        return remainCredit;
    }

    public List<HekmatCoupons> getCoupons() {
        if (coupons == null) return new ArrayList<>();
        return coupons;
    }

    public String getMaxCredit() {
        return maxCredit;
    }

    public static HekmatRemainingsModel fromJSon(String json) {
        try {
            return new Gson().fromJson(json, HekmatRemainingsModel.class);
        } catch (Exception err) {
            return null;
        }
    }

}
