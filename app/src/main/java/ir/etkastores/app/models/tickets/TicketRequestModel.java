package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

/**
 * Created by garshasbi on 3/25/18.
 */

public class TicketRequestModel {

    @SerializedName("TicketCode")
    private String ticketCode;

    @SerializedName("Title")
    private String title;

    @SerializedName("StoreRef")
    private long storeRef;

    @SerializedName("Message")
    private String message;

    @SerializedName("DepartmentRef")
    private Integer departmentRef;

    public TicketRequestModel() {

    }

    public TicketRequestModel(String title, String message, long storeRef) {
        this.title = title;
        this.storeRef = storeRef;
        this.message = message;
    }

    public TicketRequestModel(String title, String message, int departmentRef) {
        this.title = title;
        this.message = message;
        this.departmentRef = departmentRef;
        this.ticketCode = null;
    }

    public String getTitle() {
        return title;
    }

    public long getStoreRef() {
        return storeRef;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStoreRef(long storeRef) {
        this.storeRef = storeRef;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
