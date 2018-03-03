package ir.etkastores.app.Models.hekmat;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 1/21/18.
 */

public class HekmatProductModel {

    public static HekmatProductModel fromJson(String json){
        try {
            return new Gson().fromJson(json,HekmatProductModel.class);
        }catch (Exception err){
            return null;
        }
    }

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private String price;

    @SerializedName("discountedPrice")
    private String discountedPrice;

    @SerializedName("share")
    private String share;

    @SerializedName("subject")
    private String subject;

    @SerializedName("distribution")
    private String distribution;

    @SerializedName("stores")
    private List<Integer> stores;

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public String getShare() {
        return share;
    }

    public String getSubject() {
        return subject;
    }

    public String getDistribution() {
        return distribution;
    }

    public List<Integer> getStores() {
        return stores;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public void setStores(List<Integer> stores) {
        this.stores = stores;
    }

}
