package ir.etkastores.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sajad on 7/8/17.
 */

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

    public synchronized static SharedPreferences getPreference(){
        return instance.getSharedPreferences("PREFERENCES",MODE_PRIVATE);
    }

    synchronized public Tracker getGoogleAnalyticsTracker() {
        if (mTracker == null) {
            mTracker = mGoogleAnalytics.newTracker(R.xml.app_tracker);
        }
        return mTracker;
    }

    public void screenView(String screenName){
        try {
            getGoogleAnalyticsTracker().setScreenName(screenName);
            getGoogleAnalyticsTracker().send(new HitBuilders.ScreenViewBuilder().build());
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
