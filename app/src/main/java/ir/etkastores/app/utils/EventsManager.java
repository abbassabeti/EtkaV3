package ir.etkastores.app.utils;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.google.android.gms.analytics.HitBuilders;

import ir.etkastores.app.EtkaApp;

/**
 * Created by garshasbi on 4/12/18.
 */

public class EventsManager {

    public static void screenView(String screenName){
        try {
            sendAdjustEvent(screenName);
            EtkaApp.getInstance().getGoogleAnalyticsTracker().setScreenName(screenName);
            EtkaApp.getInstance().getGoogleAnalyticsTracker().send(new HitBuilders.ScreenViewBuilder().build());
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void sendEvent(String category, String action, String label){
        sendAdjustEvent(category);
        sendGoogleAnalyticsEvent(category,action,label);
    }

    private static void sendAdjustEvent(String name){
        try {
            AdjustEvent event = new AdjustEvent(name.toLowerCase());
            Adjust.trackEvent(event);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    private static void sendGoogleAnalyticsEvent(String category, String action, String label){
        try {

        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
