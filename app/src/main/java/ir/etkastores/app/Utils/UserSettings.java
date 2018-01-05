package ir.etkastores.app.Utils;

/**
 * Created by Sajad on 1/5/18.
 */

public class UserSettings {

    private final static String USER_EMAIL_ADDRESS = "USER_EMAIL_ADDRESS";
    private final static String USER_PASSWORD = "USER_PASSWORD";

    public static String getEmailAddress(){
        return DiskDataHelper.getString(USER_EMAIL_ADDRESS);
    }

    public static String getPasswrod(){
        return DiskDataHelper.getString(USER_PASSWORD);
    }

    public static void setEmailAddress(String emailAddress){
        DiskDataHelper.putString(USER_EMAIL_ADDRESS,emailAddress);
    }

    public static void setPasswrod(String passwrod){
        DiskDataHelper.putString(USER_PASSWORD,passwrod);
    }

}
