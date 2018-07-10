package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class RequestProductAnswerModel {

    @SerializedName("id")
    private long id;

    @SerializedName("message")
    private String message;

    @SerializedName("ticketRef")
    private long ticketRef;

    @SerializedName("date")
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        if (message == null) return "";
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTicketRef() {
        return ticketRef;
    }

    public void setTicketRef(long ticketRef) {
        this.ticketRef = ticketRef;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
