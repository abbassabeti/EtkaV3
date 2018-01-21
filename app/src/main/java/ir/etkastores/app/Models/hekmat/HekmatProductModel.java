package ir.etkastores.app.Models.hekmat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 1/21/18.
 */

public class HekmatProductModel {

    @SerializedName("Title")
    private String title;

    @SerializedName("BarCode")
    private String barCode;

    @SerializedName("Price")
    private String price;

    @SerializedName("DiscountedPrice")
    private String discountedPrice;

    @SerializedName("Share")
    private String share;

    public String getTitle() {
        return title;
    }

    public String getBarCode() {
        return barCode;
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

}
