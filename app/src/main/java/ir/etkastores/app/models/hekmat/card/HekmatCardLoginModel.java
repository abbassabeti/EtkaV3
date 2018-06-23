package ir.etkastores.app.models.hekmat.card;

import com.google.gson.annotations.SerializedName;

public class HekmatCardLoginModel {

    @SerializedName("CardNumber")
    private String cardNumber;

    @SerializedName("Password")
    private String password;

    public HekmatCardLoginModel(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }
}
