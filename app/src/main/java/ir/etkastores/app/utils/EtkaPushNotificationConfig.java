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
    private static final String GLOBAL_TOPIC = "global";
    private static final String DEV_TOPIC = "dev";
    private static final String LOGIN_USER = "logins";
    private static final String GUEST = "guests";

    public static void subscribeHekmat() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(HEKMAT_TOPIC);
            DiskDataHelper.putBool(HEKMAT_TOPIC, true);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unSubscribeHekmat() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(HEKMAT_TOPIC);
            DiskDataHelper.putBool(HEKMAT_TOPIC + "KEY", true);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void subscribeDev() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(DEV_TOPIC);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void unSubscribeDev() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(DEV_TOPIC);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public static void subscribeGlobal() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_TOPIC);
            FirebaseMessaging.getInstance().subscribeToTopic("v" + BuildConfig.VERSION_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isHekmatSubscribed() {
        return !DiskDataHelper.getBool(HEKMAT_TOPIC + "KEY");
    }

    public static void unSubscribeGuests() {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().unsubscribeFromTopic(GUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void subscribeGuests() {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(LOGIN_USER);
            FirebaseMessaging.getInstance().subscribeToTopic(GUEST);
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


}
