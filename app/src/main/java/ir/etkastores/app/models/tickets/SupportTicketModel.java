package ir.etkastores.app.models.tickets;

import com.google.gson.annotations.SerializedName;

public class SupportTicketModel {

    @SerializedName("TicketCode")
    private String ticketCode;

    @SerializedName("DepartmentRef")
    private int departmentRef;

    @SerializedName("Title")
    private String title;

    @SerializedName("Message")
    private String message;

    @SerializedName("Date")
    private String date;

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public int getDepartmentRef() {
        return departmentRef;
    }

    public void setDepartmentRef(int departmentRef) {
        this.departmentRef = departmentRef;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
