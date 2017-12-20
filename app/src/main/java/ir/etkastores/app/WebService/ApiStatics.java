package ir.etkastores.app.WebService;

/**
 * Created by Sajad on 9/3/17.
 */

public class ApiStatics {

    public static final String BASE_URL = "http://217.147.92.168:8008";

    public static final String END_POINT_TOKEN = "/Token";
    public static final String END_POINT_REGISTER = "/api/v1/Account/Register";
    public static final String END_POINT_EDIT_PROFILE = "/api/v1/Account/EditProfile";
    public static final String END_POINT_RESET_PASSWORD = "/api/v1/Account/ResetPassword";

    public static final String CLIENT_ID = "AS1zb49R43RTIn5934dn34Prxxa34RT";
    public static final String CLINET_SECRET = "AS85dwD5asd8E4ESD85";

    public static final String GRAND_TYPE_PASSWORD = "password";
    public static final String GRAND_TYPE_REFRESH_TOKEN = "refresh_token";

    private static AccessToken LastToken;

    public static AccessToken getLastToken(){
        return getLastToken();
    }

    public static void saveToken(AccessToken token){
        LastToken = token;
    }

}
