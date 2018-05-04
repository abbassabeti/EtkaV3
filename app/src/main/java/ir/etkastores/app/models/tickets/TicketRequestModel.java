package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/25/18.
 */

public class TicketRequestModel {

    public static class TicketType{
        public final static String ProductRequest = "ProductRequest";
        public final static String Support = "Support";
    }

    @SerializedName("Title")
    private String title;

    @SerializedName("TicketType")
    private String ticketType;

    @SerializedName("StoreRef")
    private long storeRef;

    @SerializedName("Message")
    private String message;

    public TicketRequestModel() {

    }

    public TicketRequestModel(String title, String ticketType, long storeRef) {
        this.title = title;
        this.ticketType = ticketType;
        this.storeRef = storeRef;
    }

    public String getTitle() {
        return title;
    }

    public String getTicketType() {
        return ticketType;
    }

    public long getStoreRef() {
        return storeRef;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setStoreRef(long storeRef) {
        this.storeRef = storeRef;
    }

    public String getDisplayTicketType(){
        switch (getTicketType()){
            case TicketType.ProductRequest:
                return "درخواست کالا";

            case TicketType.Support:
                return "پشتیبانی";
        }
        return "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
