package ir.etkastores.app.models.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.models.ProductModel;

/**
 * Created by Sajad on 2/3/18.
 */

public class ProductSearchResponseModel {

    @SerializedName("totalItemsCount")
    int totalItemsCount;

    @SerializedName("items")
    List<ProductModel> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<ProductModel> getItems() {
        return items;
    }
}
