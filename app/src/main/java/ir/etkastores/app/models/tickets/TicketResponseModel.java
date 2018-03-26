package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by garshasbi on 3/27/18.
 */

public class TicketResponseModel {

    @SerializedName("totalItemsCount")
    private int totalItemsCount;

    @SerializedName("items")
    private List<TicketItem> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<TicketItem> getItems() {
        return items;
    }
}
