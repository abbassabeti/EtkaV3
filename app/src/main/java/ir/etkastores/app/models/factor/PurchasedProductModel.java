package ir.etkastores.app.models.factor;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
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
}
