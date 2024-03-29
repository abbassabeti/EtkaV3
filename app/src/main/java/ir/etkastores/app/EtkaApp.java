package ir.etkastores.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.utils.EventsManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sajad on 7/8/17.
 */

@Obfuscate
public class EtkaApp extends MultiDexApplication {

    private static EtkaApp instance;

    private static GoogleAnalytics mGoogleAnalytics;
    private static Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;

        mGoogleAnalytics = GoogleAnalytics.getInstance(this);

        initFont();
        initAdjust();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static EtkaApp getInstance() {
        return instance;
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public synchronized static SharedPreferences getPreference() {
        return instance.getSharedPreferences("PREFERENCES", MODE_PRIVATE);
    }

    private void initAdjust() {
//        String appToken = "uoflo2801534"; // old
        String appToken = "s20be4m17aio"; // new
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        if (!BuildConfig.DEBUG) environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        config.setAppSecret(1, 343603687, 1571206015, 2010524332, 474861284);
        if (!BuildConfig.DEBUG) config.setLogLevel(LogLevel.SUPRESS);
        config.setReadMobileEquipmentIdentity(true);
        config.setSendInBackground(true);
        Adjust.setEnabled(true);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }

    synchronized public Tracker getGoogleAnalyticsTracker() {
        if (mTracker == null) {
            mTracker = mGoogleAnalytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }

    public void screenView(String screenName) {
        EventsManager.screenView(screenName);
    }

    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }

}
