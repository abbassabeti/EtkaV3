package ir.etkastores.app.utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import io.michaelrocks.paranoid.Obfuscate;

/**
 * Created by garshasbi on 3/1/18.
 */

@Obfuscate
public class EtkaPushNotificationConfig {

    private static final String HEKMAT_TOPIC = "hekmat";
    private static final String GLOBAL_TOPIC = "global";
    private static final String DEV_TOPIC = "dev";
    private static final String LOGIN_USER = "logins";
    private static final String GUEST = "guests";

    public static void registerHekmat(){
        FirebaseMessaging.getInstance().subscribeToTopic(HEKMAT_TOPIC);
        DiskDataHelper.putBool(HEKMAT_TOPIC,true);
    }

    public static void unregisterHekmat(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(HEKMAT_TOPIC);
        DiskDataHelper.putBool(HEKMAT_TOPIC,false);
    }

    public static void registerUserIdTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic(DiskDataHelper.getLastToken().getUserId());
    }

    public static void unregisterUserIdTopic(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(DiskDataHelper.getLastToken().getUserId());
    }

    public static void registerDev(){
        FirebaseMessaging.getInstance().subscribeToTopic(DEV_TOPIC);
    }

    public static void unregisterDev(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(DEV_TOPIC);
    }

    public static void deleteInstanceId(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                }catch (Exception err){
                    err.printStackTrace();
                }
            }
        }).start();

    }

    public static void registerGlobal(){
        FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_TOPIC);
    }

    public static boolean isHekmatSubscribed(){
        return DiskDataHelper.getBool(HEKMAT_TOPIC);
    }

    public static void unregisterGuests(){
        FirebaseMessaging.getInstance().subscribeToTopic(LOGIN_USER);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(GUEST);
    }

    public static void registerGuests(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(LOGIN_USER);
        FirebaseMessaging.getInstance().subscribeToTopic(GUEST);
    }


}
