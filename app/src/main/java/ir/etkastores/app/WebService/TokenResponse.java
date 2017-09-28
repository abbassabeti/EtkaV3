package ir.etkastores.app.WebService;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sajad on 9/4/17.
 */

public class TokenResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private String expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("UserId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccessToken() {
        return  accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}