package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class TicketFilterModel {

    @SerializedName("UserId")
    String userId;

    @SerializedName("Take")
    int take;

    @SerializedName("Page")
    int page;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
