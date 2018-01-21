package ir.etkastores.app.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Sajad on 1/22/18.
 */

public class IntentHelper {

    public static void openWayTracer(Activity activity, double lat, double lon, String title){
        try {
            String uri = "geo:" + lat + "," + lon + "?q=" + title;
            activity.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
        }catch (Exception err){
            showWeb(activity,"http://maps.google.com/maps?saddr=" +lat+","+lon);
        }
    }

    public static void showWeb(Activity activity, String url){
        try {
            activity.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
