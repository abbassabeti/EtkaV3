package ir.etkastores.app.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 1/22/18.
 */

public class IntentHelper {

    public static void openWayTracer(Activity activity, double lat, double lon, String title){
        try {
            String uri = "geo:" + lat + "," + lon + "?q=" + title;
            activity.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
        }catch (Exception err){
            showWeb(activity,getGoogleMapLocationAddress(lat,lon));
        }
    }

    public static void showWeb(Activity activity, String url){
        try {
            activity.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void share(Activity activity,String title, String body){
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
            activity.startActivity(Intent.createChooser(sharingIntent, activity.getResources().getString(R.string.shareUsing)));
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static String getGoogleMapLocationAddress(double lat, double lng){
        return "http://maps.google.com/maps?saddr=" +lat+","+lng;
    }

}
