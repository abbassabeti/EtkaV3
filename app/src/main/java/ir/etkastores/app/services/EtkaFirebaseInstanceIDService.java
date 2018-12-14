package ir.etkastores.app.services;

import android.util.Log;

import com.adjust.sdk.Adjust;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.data.PushTokenManager;
import ir.etkastores.app.utils.EtkaPushNotificationConfig;

/**
 * Created by Sajad on 2/11/18.
 */

public class EtkaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Adjust.setPushToken(refreshedToken);
        PushTokenManager.getInstance().updateToken(refreshedToken);
        if (BuildConfig.DEBUG) Log.d("FCM", "Refreshed token: " + refreshedToken);
        EtkaPushNotificationConfig.checkSubscribes();
    }
}
