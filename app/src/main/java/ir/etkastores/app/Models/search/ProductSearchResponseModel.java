package ir.etkastores.app.Models.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import butterknife.BindView;
import ir.etkastores.app.Models.ProductModel;

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
