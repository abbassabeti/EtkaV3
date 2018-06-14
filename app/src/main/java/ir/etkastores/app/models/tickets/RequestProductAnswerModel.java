package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class RequestProductAnswerModel {

    @SerializedName("Id")
    public long id;

    @SerializedName("Message")
    public String message;

    @SerializedName("TicketRef")
    public long ticketRef;

    @SerializedName("Date")
    public String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
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
