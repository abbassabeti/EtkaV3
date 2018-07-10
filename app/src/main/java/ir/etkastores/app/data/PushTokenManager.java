package ir.etkastores.app.data;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushTokenManager {

    private final static String LAST_REFRESHED_TOKEN = "LAST_REFRESHED_TOKEN";
    private final static String LAST_SYNCED_TOKEN = "LAST_SYNCED_TOKEN";
    private final static int MAX_RETRY_COUNT = 5;

    private static PushTokenManager instance;

    public static PushTokenManager getInstance() {
        if (instance == null) instance = new PushTokenManager();
        return instance;
    }

    private Call<OauthResponse<String>> req;
    private int retryCounter = MAX_RETRY_COUNT;

    private PushTokenManager() {

    }

    public void updateToken(String token) {
        DiskDataHelper.putString(LAST_REFRESHED_TOKEN, token);
        syncToken();
    }

    public void syncToken() {
        try {
            String token = FirebaseInstanceId.getInstance().getToken();
            if (!getLastRefreshedToken().contentEquals(token)) {
                DiskDataHelper.putString(LAST_REFRESHED_TOKEN, token);
            }
        } catch (Exception err) {
        }

        if (TextUtils.isEmpty(getLastRefreshedToken())) {
            try {
                String token = FirebaseInstanceId.getInstance().getToken();
                DiskDataHelper.putString(LAST_REFRESHED_TOKEN, token);
            } catch (Exception err) {
                err.printStackTrace();
            }
            if (TextUtils.isEmpty(getLastRefreshedToken())) return;
        }
        if (ProfileManager.isGuest()) return;
        if (req != null && req.isExecuted()) return;
        if (!isTokenSynced()) sendRequest();
    }

    public boolean isTokenSynced() {
        if (TextUtils.isEmpty(getLastSyncedToken())) return false;
        return getLastRefreshedToken().contentEquals(getLastSyncedToken());
    }

    private void sendRequest() {
        final String token = getLastRefreshedToken();
        req = ApiProvider.getAuthorizedApi().syncLastPushToken(token);
        req.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        DiskDataHelper.putString(LAST_SYNCED_TOKEN, token);
                        retryCounter = MAX_RETRY_COUNT;
                        if (BuildConfig.DEBUG) Log.i("FCM Token synced", "" + getLastSyncedToken());
                    } else {
                        retryCounter = MAX_RETRY_COUNT;
                        req = null;
                    }
                } else {
                    retryCounter = MAX_RETRY_COUNT;
                    req = null;
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                if (retryCounter > 0) {
                    retryCounter -= 1;
                    sendRequest();
                } else {
                    req = null;
                    retryCounter = MAX_RETRY_COUNT;
                }
            }
        });

    }

    private String getLastRefreshedToken() {
        return DiskDataHelper.getString(LAST_REFRESHED_TOKEN);
    }

    private String getLastSyncedToken() {
        return DiskDataHelper.getString(LAST_SYNCED_TOKEN);
    }

}
