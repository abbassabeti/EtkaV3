package ir.etkastores.app.models.tickets;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

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
    private int departmentRef;

    @SerializedName("answer")
    public RequestProductAnswerModel answer;

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

    public int getDepartmentRef() {
        return departmentRef;
    }

    public RequestProductAnswerModel getAnswer() {
        return answer;
    }

}
