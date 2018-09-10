package ir.etkastores.app.models.factor;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 1/13/18.
 */

public class PurchasedProductModel {

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private String price;

    @SerializedName("count")
    private String count;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("barCode")
    private String barCode;

    @SerializedName("discount")
    private String discount;

    @SerializedName("totalPrice")
    private String totalPrice;

    public String getTitle() {
        if (TextUtils.isEmpty(title)) return "-";
        return title;
    }

    public String getPrice() {
        if (TextUtils.isEmpty(price)) return "-";
        return price;
    }

    public String getCount() {
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
