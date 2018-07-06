package ir.etkastores.app.models.factor;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 1/13/18.
 */

public class PurchasedProductModel {

    @SerializedName("title")
    String title;

    @SerializedName("price")
    String price;

    @SerializedName("count")
    int count;

    @SerializedName("imageUrl")
    String imageUrl;

    @SerializedName("barCode")
    String barCode;

    @SerializedName("discount")
    String discount;

    @SerializedName("totalPrice")
    String totalPrice;

    public String getTitle() {
        if (TextUtils.isEmpty(title)) return "-";
        return title;
    }

    public String getPrice() {
        if (TextUtils.isEmpty(price)) return "-";
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getDiscount() {
        if (TextUtils.isEmpty(discount)) return "-";
        return discount;
    }

    public String getTotalPrice() {
        if (TextUtils.isEmpty(totalPrice)) return "-";
        return totalPrice;
    }
}
