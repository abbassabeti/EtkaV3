package ir.etkastores.app.models.tickets;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

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

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("ticketCode")
    private String ticketCode;

    @SerializedName("departmentRef")
    private Integer departmentRef;

    @SerializedName("answer")
    private RequestProductAnswerModel answer;

    @SerializedName("userTicket")
    private boolean isUserTicket;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        if (status == null) return "";
        return status;
    }

    public int getStatusTextColor() {

        switch (getStatus()) {

            case TicketStatus.Answered:
                return Color.GREEN;

            case TicketStatus.UnAnswered:
                return ContextCompat.getColor(EtkaApp.getInstance(), R.color.colorPrimary);

            case TicketStatus.Closed:
                return Color.RED;

        }
        return Color.BLACK;
    }

    public static class TicketType {

        public static final String ProductRequest = "ProductRequest";
        public static final String Support = "Support";

        public static String getDisplayValueOfType(String value) {

            switch (value) {

                case ProductRequest:
                    return "درخواست کالا";

                case Support:
                    return "پشتیبانی";

            }
            return "";

        }
    }

    public String getMessage() {
        return message;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public Integer getDepartmentRef() {
        return departmentRef;
    }

    public RequestProductAnswerModel getAnswer() {
        return answer;
    }

    public boolean isUserTicket() {
        return isUserTicket;
    }

    public static TicketItem fromJson(String json) {
        try {
            return new Gson().fromJson(json, TicketItem.class);
        } catch (Exception err) {
            return null;
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public void setDepartmentRef(int departmentRef) {
        this.departmentRef = departmentRef;
    }

    public void setAnswer(RequestProductAnswerModel answer) {
        this.answer = answer;
    }

    public void setUserTicket(boolean userTicket) {
        isUserTicket = userTicket;
    }
}
