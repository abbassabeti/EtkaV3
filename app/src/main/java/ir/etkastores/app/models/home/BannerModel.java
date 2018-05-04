package ir.etkastores.app.models.home;

import com.google.gson.annotations.SerializedName;

import ir.etkastores.app.models.search.SearchProductRequestModel;

/**
 * Created by garshasbi on 5/4/18.
 */

public class BannerModel {

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("loadMoreJson")
    private String loadMoreJson;

    @SerializedName("externalUrl")
    private String externalUrl;

    private SearchProductRequestModel searchProductRequest;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLoadMoreJson() {
        return loadMoreJson;
    }

    public void setLoadMoreJson(String loadMoreJson) {
        this.loadMoreJson = loadMoreJson;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public SearchProductRequestModel getSearchProductRequest() {
        return SearchProductRequestModel.fromJson(loadMoreJson);
    }
}
