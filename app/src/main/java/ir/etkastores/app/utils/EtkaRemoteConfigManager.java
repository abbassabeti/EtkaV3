package ir.etkastores.app.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ContactUsManager;
import ir.etkastores.app.webServices.ApiStatics;

/**
 * Created by garshasbi on 5/7/18.
 */

public class EtkaRemoteConfigManager {

    public static void checkRemoteConfigs() {
        final FirebaseRemoteConfig firebaseRemoteConfig;
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings settings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).build();
        firebaseRemoteConfig.setConfigSettings(settings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_defaults);

        firebaseRemoteConfig.fetch(30).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseRemoteConfig.activateFetched();
                    String baseUrl = firebaseRemoteConfig.getString("v3_api_server");
                    String minVersion = firebaseRemoteConfig.getString("v3_force_update_min_version_code");
                    final String updateUrl = firebaseRemoteConfig.getString("v3_force_update_url");
                    ContactUsManager.getInstance().saveEmail(firebaseRemoteConfig.getString("v3_contact_us_email"));
                    ContactUsManager.getInstance().savePhone(firebaseRemoteConfig.getString("v3_contact_us_phone"));
                    int minAppVersion = Integer.parseInt(minVersion);
                    if (minAppVersion > BuildConfig.VERSION_CODE) {
                        DiskDataHelper.setIsForceUpdateAvailable(true);
                        DiskDataHelper.setForceUpdateUrl(updateUrl);
                    } else {
                        DiskDataHelper.setIsForceUpdateAvailable(false);
                    }
                    ApiStatics.setBaseUrl(baseUrl);
                }else{
                    DiskDataHelper.setIsForceUpdateAvailable(false);
                }
            }
        });

    }

}