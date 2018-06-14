package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestProductResponse {

    @SerializedName("totalItemsCount")
    private int totalItemsCount;

    @SerializedName("items")
    private List<RequestProductModel> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<RequestProductModel> getItems() {
        return items;
    }
}
