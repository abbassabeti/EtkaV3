package ir.etkastores.app.models.factor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sajad on 1/13/18.
 */

public class FactorModel {

    @SerializedName("id")
    long id;

    @SerializedName("factorCode")
    long factorCode;

    @SerializedName("totalPrice")
    String totalPrice;

    @SerializedName("totalDiscount")
    long totalDiscount;

    @SerializedName("date")
    String date;

    @SerializedName("purchasedProducts")
    List<PurchasedProductModel> purchasedProducts;

    boolean isExpanded = false;

    public long getId() {
        return id;
    }

    public long getFactorCode() {
        return factorCode;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public long getTotalDiscount() {
        return totalDiscount;
    }

    public String getDate() {
        return date;
    }

    public List<PurchasedProductModel> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFactorCode(long factorCode) {
        this.factorCode = factorCode;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalDiscount(long totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPurchasedProducts(List<PurchasedProductModel> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}