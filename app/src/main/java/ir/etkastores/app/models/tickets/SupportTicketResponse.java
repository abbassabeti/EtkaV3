package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportTicketResponse {

    @SerializedName("totalItemsCount")
    private int totalItemsCount;

    @SerializedName("items")
    private List<SupportTicketModel> items;

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public List<SupportTicketModel> getItems() {
        return items;
    }
}
