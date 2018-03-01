package ir.etkastores.app.Utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by garshasbi on 3/1/18.
 */

public class EtkaPushNotificationConfig {

    private static final String HEKMAT_TOPIC = "hekmat";
    private static final String GLOBAL_TOPIC = "global";

    public static void enableHekmat(){
        FirebaseMessaging.getInstance().subscribeToTopic(HEKMAT_TOPIC);
    }

    public static void disableHekmat(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(HEKMAT_TOPIC);
    }

    public static void registerUserIdTopic(){

    }

    public static void setLoginState(){

    }

    public static void deleteInstanceId(){
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        }catch (Exception err){
            err.printStackTrace();
        }

    }

    public static void enableGlobal(){
        FirebaseMessaging.getInstance().subscribeToTopic(GLOBAL_TOPIC);
    }

}
