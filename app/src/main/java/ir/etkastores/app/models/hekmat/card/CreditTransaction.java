package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

public class CreditTransaction {

    @SerializedName("amount")
    private String amount;

    @SerializedName("merchantName")
    private String merchantName;

    @SerializedName("type")
    private String type;

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

    public String getDateTime() {
        return dateTime;
    }

}
