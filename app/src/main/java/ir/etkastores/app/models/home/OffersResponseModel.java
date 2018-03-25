package ir.etkastores.app.models.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
