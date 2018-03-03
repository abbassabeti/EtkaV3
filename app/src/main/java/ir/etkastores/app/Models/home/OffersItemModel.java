package ir.etkastores.app.Models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.Models.ProductModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class OffersItemModel {

    @SerializedName("title")
    String title;

    @SerializedName("bannerUrl")
    String bannerUrl;

    @SerializedName("products")
    List<ProductModel> products;

    public String getTitle() {
        return title;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

}
