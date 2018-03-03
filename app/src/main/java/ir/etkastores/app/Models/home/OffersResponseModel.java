package ir.etkastores.app.Models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.Models.ProductModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class OffersResponseModel {

    @SerializedName("totalItemsCount")
    int totalItemsCount;

    @SerializedName("items")
    List<OffersItemModel> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<OffersItemModel> getItems() {
        return items;
    }

}
