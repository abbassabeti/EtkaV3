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
            EtkaApp.getInstance().getGoogleAnalyticsTracker().setScreenName(screenName);
            EtkaApp.getInstance().getGoogleAnalyticsTracker().send(new HitBuilders.ScreenViewBuilder().build());
        }catch (Exception err){

        }
    }

    public static void sendEvent(String category, String action, String label){
        sendAdjustEvent(category,action,label);
        sendGoogleAnalyticsEvent(category,action,label);
    }

    private static void sendAdjustEvent(String category, String action, String label){
        try {
            AdjustEvent event = new AdjustEvent("abc123");
            event.setRevenue(0.01, "EUR");
            event.setOrderId("{OrderId}");
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
