package ir.etkastores.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Sajad on 7/8/17.
 */

public class EtkaApp extends MultiDexApplication {

    private static EtkaApp instnace;

    @Override
    public void onCreate() {
        super.onCreate();
        instnace = this;

        initFont();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static EtkaApp getInstnace() {
        return instnace;
    }

    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

}
