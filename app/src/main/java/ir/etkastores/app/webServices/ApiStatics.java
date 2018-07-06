package ir.etkastores.app.webServices;

import android.text.TextUtils;

import ir.etkastores.app.utils.DiskDataHelper;

/**
 * Created by Sajad on 9/3/17.
 */

public class ApiStatics {

    private static final String BASE_URL_KEY = "BASE_URL";
    //        private static String BASE_URL = "http://46.209.6.91:4102";
    private static String BASE_URL = "https://api.ecrmapp.ir/";

    public static String getBaseUrl() {
        String savedUrl = DiskDataHelper.getString(BASE_URL_KEY);
        if (TextUtils.isEmpty(savedUrl)) {
            return BASE_URL;
        } else {
            BASE_URL = savedUrl;
        }
        return BASE_URL;
    }

    public static void setBaseUrl(String url) {
        DiskDataHelper.putString(BASE_URL_KEY, url);
        BASE_URL = url;
    }

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
    public static final String HEKMAT = "api/v1/Hekmat/get";
    public static final String HOME = "/api/v1/home";
    public static final String CHANGE_PASSWORD = "/api/v1/Account/ChangePassword";
    public static final String SUPPORT_TICKETS = "/api/v1/Ticket/SupportTickets";
    public static final String SUPPORT_TICKET = "/api/v1/Ticket/SupportTicket";
    public static final String SUPPORT_CONVERSATION = "/api/v1/Ticket/SupportConversation";
    public static final String PRODUCT_TICKETS = "/api/v1/Ticket/ProductRequest";
    public static final String TICKET_ANSWER = "/api/v1/Ticket/TicketAnswer";
    public static final String GET_TICKET_ANSWER = "/api/v1/Ticket/GetTicketAnswer";
    public static final String SAVE_PRODUCTS = "api/v1/SaveProducts";
    public static final String NEWS = "/api/v1/news";
    public static final String SURVEY = "/api/v1/Survey";
    public static final String DEPARTMENTS = "/api/v1/Ticket/Departments";
    public static final String DEVICE_GROUP_TOKEN = "/api/v1/Notification/DeviceGroup";
    public static final String ACTIVATION_CODE = "/api/v1/Account/GetActivationCode";
    public static final String CREDIT_TRANSACTION = "/api/v1/hekmat/GetCreditTransaction";
    public static final String INSTALLMENT_TRANSACTION = "/api/v1/hekmat/GetInstallmentTransaction";
    public static final String HEKMAT_LOGIN = "/api/v1/hekmat/HekmatCardLogin";


    public static final String CLIENT_ID = "AS1zb49R43RTIn5934dn34Prxxa34RT";
    public static final String CLIENT_SECRET = "AS85dwD5asd8E4ESD85";

    public static final String GRAND_TYPE_PASSWORD = "password";
    public static final String GRAND_TYPE_VERIFY = "verify";
    public static final String GRAND_TYPE_REFRESH_TOKEN = "refresh_token";

    private static AccessToken lastToken;

    public static AccessToken getLastToken() {
        if (lastToken == null) lastToken = DiskDataHelper.getLastToken();
        return lastToken;
    }

    public static void saveToken(AccessToken token) {
        DiskDataHelper.saveLastToken(token);
        lastToken = token;
    }

}
