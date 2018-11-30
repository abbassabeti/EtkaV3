package ir.etkastores.app.utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;

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

    public static void registerHekmat() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(HEKMAT_TOPIC);
            DiskDataHelper.putBool(HEKMAT_TOPIC, true);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unregisterHekmat() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(HEKMAT_TOPIC);
            DiskDataHelper.putBool(HEKMAT_TOPIC, false);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void registerUserIdTopic() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(DiskDataHelper.getLastToken().getUserId());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unregisterUserIdTopic() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(DiskDataHelper.getLastToken().getUserId());
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void registerDev() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(DEV_TOPIC);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unregisterDev() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(DEV_TOPIC);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void deleteInstanceId() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }).start();

    }

    public static void registerGlobal() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_TOPIC);
            FirebaseMessaging.getInstance().subscribeToTopic("v"+BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isHekmatSubscribed() {
        return DiskDataHelper.getBool(HEKMAT_TOPIC);
    }

    public static void unregisterGuests() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(GUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerGuests() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().subscribeToTopic(GUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
