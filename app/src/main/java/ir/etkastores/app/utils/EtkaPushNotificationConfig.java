package ir.etkastores.app.utils;

import com.google.firebase.messaging.FirebaseMessaging;

import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.data.ProfileManager;

/**
 * Created by garshasbi on 3/1/18.
 */

@Obfuscate
public class EtkaPushNotificationConfig {

    private static final String HEKMAT_TOPIC = "hekmat";
    private static final String HEKMAT_KEY = "hekmatKey";
    private static final String GLOBAL_TOPIC = "global";
    private static final String DEV_TOPIC = "dev";
    private static final String LOGIN_USER = "logins";
    private static final String GUEST = "guests";

    public static void subscribeHekmat() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(HEKMAT_TOPIC);
            FirebaseMessaging.getInstance().subscribeToTopic(getVersionTopic(HEKMAT_TOPIC));
            DiskDataHelper.putBool(HEKMAT_KEY, false);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unSubscribeHekmat() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(HEKMAT_TOPIC);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getVersionTopic(HEKMAT_TOPIC));
            DiskDataHelper.putBool(HEKMAT_KEY, true);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void subscribeDev() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(DEV_TOPIC);
            FirebaseMessaging.getInstance().subscribeToTopic(getVersionTopic(DEV_TOPIC));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unSubscribeDev() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(DEV_TOPIC);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getVersionTopic(DEV_TOPIC));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void subscribeGlobal() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_TOPIC);
            FirebaseMessaging.getInstance().subscribeToTopic(getVersionTopic(GLOBAL_TOPIC));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isHekmatSubscribed() {
        return !DiskDataHelper.getBool(HEKMAT_KEY);
    }

    public static void unSubscribeGuests() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().subscribeToTopic(getVersionTopic(LOGIN_USER));
            FirebaseMessaging.getInstance().unsubscribeFromTopic(GUEST);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getVersionTopic(GUEST));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void subscribeGuests() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getVersionTopic(LOGIN_USER));
            FirebaseMessaging.getInstance().subscribeToTopic(GUEST);
            FirebaseMessaging.getInstance().subscribeToTopic(getVersionTopic(GUEST));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkSubscribes() {

        subscribeGlobal();

        if (isHekmatSubscribed()) {
            subscribeHekmat();
        } else {
            unSubscribeHekmat();
        }

        if (ProfileManager.getInstance().isGuest()) {
            subscribeGuests();
        } else {
            unSubscribeGuests();
        }

        if (BuildConfig.DEBUG) {
            subscribeDev();
        } else {
            unSubscribeDev();
        }

    }


    private static String getVersionTopic(String topic) {
        return "v" + BuildConfig.VERSION_NAME + "_" + topic;
    }

}
