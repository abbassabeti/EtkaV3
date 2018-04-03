package ir.etkastores.app.models.tickets;

/**
 * Created by garshasbi on 3/25/18.
 */

public class TicketStatus {

    public final static int Closed = 0;

    public final static int Answered = 1;

    public final static int UnAnswered = 2;

    public static String getDisplayValueOfStatus(int val) {

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
