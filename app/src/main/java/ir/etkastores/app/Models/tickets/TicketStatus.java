package ir.etkastores.app.Models.tickets;

/**
 * Created by garshasbi on 3/25/18.
 */

public class TicketStatus {

    public final static String Closed = "Closed";

    public final static String Answered = "Answered";

    public final static String UnAnswered = "UnAnswered";

    public static String getDisplayValueOfStatus(String val) {

        switch (val) {

            case Closed:
                return "بسته";

            case Answered:
                return "پاسخ داده شده";

            case UnAnswered:
                return "در انتظار پاسخ";

        }

        return "";
    }

}
