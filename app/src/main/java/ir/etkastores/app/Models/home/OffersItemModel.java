package ir.etkastores.app.Models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.Models.ProductModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class OffersItemModel {

    @SerializedName("title")
    private String title;

    @SerializedName("bannerUrl")
    private String bannerUrl;

    @SerializedName("products")
    private List<ProductModel> products;

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
