package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.procalendar.XCalendar;

public class InstallmentItem {

    @SerializedName("amount")
    private String amount;

    @SerializedName("merchantName")
    private String merchantName;

    @SerializedName("type")
    private String type;

    @SerializedName("installmentCount")
    private int installmentCount;

    @SerializedName("installmentNumber")
    private int installmentNumber;

    @SerializedName("dateTime")
    private String dateTime;

    public String getAmount() {
        return amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getType() {
        return type;
    }

    public int getInstallmentCount() {
        return installmentCount;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public String getDateTime() {
        return dateTime;
    }


    public String getFormattedDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = sdf.parse(dateTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            XCalendar c = XCalendar.fromGerigorian(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            return c.getCalendar(XCalendar.JalaliType).getDateNumerical("/");
        } catch (Exception err) {
            return "--";
        }
    }

    public String getFormattedAmount() {
        try {
            double amount = Double.parseDouble(getAmount());
            DecimalFormat formatter = new DecimalFormat("#,###");
            return formatter.format(amount) + " " + EtkaApp.getInstance().getResources().getString(R.string.rial);
        } catch (Exception err) {
            return "--";
        }
    }
}
