package ir.etkastores.app.models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class HomeItemsModel {

    @SerializedName("title")
    private String title;

    @SerializedName("priority")
    private int priority;

    @SerializedName("banners")
    private List<BannerModel> banners;

    @SerializedName("products")
    private List<ProductModel> products;

    @SerializedName("loadMoreJson")
    private String loadMoreJson;

    private SearchProductRequestModel searchProductRequestModel;

    public String getTitle() {
        return title;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public int getPriority() {
        return priority;
    }

    public List<BannerModel> getBanners() {
        return banners;
    }

    public SearchProductRequestModel getSearchProductRequestModel() {
        if (searchProductRequestModel == null) searchProductRequestModel = SearchProductRequestModel.fromJson(loadMoreJson);
        return searchProductRequestModel;
    }

}
