package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

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

}
