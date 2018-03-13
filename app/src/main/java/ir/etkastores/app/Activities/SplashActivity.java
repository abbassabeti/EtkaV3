package ir.etkastores.app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.Models.NotificationModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.Utils.EtkaPushNotificationConfig;
import ir.etkastores.app.Utils.IntentHelper;
import ir.etkastores.app.WebService.ApiStatics;

public class SplashActivity extends BaseActivity {

    FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EtkaPushNotificationConfig.registerGlobal();
        checkRemoteConfigs();
    }

    private void checkRemoteConfigs() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings settings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).build();
        firebaseRemoteConfig.setConfigSettings(settings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_defaults);

        firebaseRemoteConfig.fetch(180).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseRemoteConfig.activateFetched();
                    String baseUrl = firebaseRemoteConfig.getString("v3_api_server");
                    String minVersion = firebaseRemoteConfig.getString("v3_force_update_min_version_code");
                    final String updateUrl = firebaseRemoteConfig.getString("v3_force_update_url");
                    int minAppVersion = Integer.parseInt(minVersion);
                    if (minAppVersion > BuildConfig.VERSION_CODE) {
                        MessageDialog dialog = MessageDialog.forceUpdate();
                        dialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
                            @Override
                            public void onDialogMessageButtonsClick(int button) {
                                if (button == RIGHT_BUTTON) {
                                    IntentHelper.showWeb(SplashActivity.this, updateUrl);
                                } else {
                                    finish();
                                }
                            }

                            @Override
                            public void onDialogMessageDismiss() {

                            }
                        });
                        return;
                    }
                    ApiStatics.setBaseUrl(baseUrl);
                } else {

                }
                prepareAppForRun();
            }
        });

    }

    private void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    if (getIntent() != null && getIntent().hasExtra(NotificationModel.IS_FROM_NOTIFICATION)) {
                        intent.putExtra(NotificationModel.IS_FROM_NOTIFICATION, getIntent().getStringExtra(NotificationModel.IS_FROM_NOTIFICATION));
                        intent.putExtra(NotificationModel.ACTION_CODE, getIntent().getStringExtra(NotificationModel.ACTION_CODE));
                        if (getIntent().hasExtra(NotificationModel.DATA)) {
                            intent.putExtra(NotificationModel.DATA, getIntent().getStringExtra(NotificationModel.DATA));
                        }
                    }
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } catch (Exception err) {
                }
            }
        }, 2000);
    }

    private void initMap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mapView = new MapView(SplashActivity.this);
                    mapView.onCreate(null);
                    showLogin();
                } catch (Exception err) {
                }
            }
        }, 100);
    }

    private void prepareAppForRun() {
        initMap();
    }

}
