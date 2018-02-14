package ir.etkastores.app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.maps.MapView;
import com.google.firebase.messaging.FirebaseMessaging;

import ir.etkastores.app.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        initMap();
    }

    private void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
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
        },300);
    }

}
