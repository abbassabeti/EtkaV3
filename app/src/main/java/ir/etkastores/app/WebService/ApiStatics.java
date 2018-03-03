package ir.etkastores.app.WebService;

import ir.etkastores.app.Utils.DiskDataHelper;

/**
 * Created by Sajad on 9/3/17.
 */

public class ApiStatics {

    public static String BASE_URL = "http://217.147.92.168:8008";

    public static final String TOKEN = "/Token";
    public static final String REGISTER = "/api/v1/Account/Register";
    public static final String EDIT_PROFILE = "/api/v1/Account/Edit";
    public static final String RESET_PASSWORD = "/api/v1/Account/ResetPassword";
    public static final String PRODUCT_CATEGORY_LEVEL = "/api/v1/ProductCategory/Level";
    public static final String PRODUCT_CATEGORY_CHILD = "/api/v1/ProductCategory/child";
    public static final String SEARCH = "/api/v1/search";
    public static final String PRODUCT = "/api/v1/Product/get";
    public static final String STORE = "/api/v1/store";
    public static final String PROFILE = "api/v1/account/info";
    public static final String FACTOR = "api/v1/Factor";
    public static final String HEKMAT = "api/v1/Hekmat";
    public static final String HOME = "/api/v1/home";

    public static final String CLIENT_ID = "AS1zb49R43RTIn5934dn34Prxxa34RT";
    public static final String CLIENT_SECRET = "AS85dwD5asd8E4ESD85";

    public static final String GRAND_TYPE_PASSWORD = "password";
    public static final String GRAND_TYPE_REFRESH_TOKEN = "refresh_token";

    private static AccessToken lastToken;

    public static AccessToken getLastToken(){
        return lastToken;
    }

    public static void saveToken(AccessToken token){
        DiskDataHelper.saveLastToken(token);
        lastToken = token;
    }

}
