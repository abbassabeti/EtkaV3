package ir.etkastores.app.models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.models.ProductModel;

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
