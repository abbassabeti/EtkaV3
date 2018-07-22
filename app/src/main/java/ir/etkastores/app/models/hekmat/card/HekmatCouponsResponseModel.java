package ir.etkastores.app.models.hekmat.card;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HekmatCouponsResponseModel {

    @SerializedName("coupons")
    private List<HekmatCoupons> coupons;

    public List<HekmatCoupons> getCoupons() {
        if (coupons == null) return new ArrayList<>();
        return coupons;
    }

    public void setCoupons(List<HekmatCoupons> coupons) {
        this.coupons = coupons;
    }

    public static HekmatCouponsResponseModel fromJson(String json){
        try {
            return new Gson().fromJson(json,HekmatCouponsResponseModel.class);
        }catch (Exception err){
            return null;
        }
    }
}
