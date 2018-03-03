package ir.etkastores.app.Models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.etkastores.app.Models.ProductModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class OffersResponseModel {

    @SerializedName("totalItemsCount")
    private int totalItemsCount;

    @SerializedName("items")
    private List<OffersResponseModel> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<OffersResponseModel> getItems() {
        return items;
    }

}
