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

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
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

    public boolean isSupport(){
        return getType().contentEquals(TicketType.Support);
    }

    public boolean isProductRequest(){
        return getType().contentEquals(TicketType.ProductRequest);
    }


}
