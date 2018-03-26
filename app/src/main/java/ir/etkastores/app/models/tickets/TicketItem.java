package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/27/18.
 */

public class TicketItem {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("date")
    private String date;

    @SerializedName("type")
    private int type;

    @SerializedName("status")
    private int status;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

}
