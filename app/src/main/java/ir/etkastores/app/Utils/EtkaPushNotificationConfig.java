package ir.etkastores.app.Utils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by garshasbi on 3/1/18.
 */

public class EtkaPushNotificationConfig {

    private static final String HEKMAT_TOPIC = "hekmat";
    private static final String GLOBAL_TOPIC = "global";

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

}
