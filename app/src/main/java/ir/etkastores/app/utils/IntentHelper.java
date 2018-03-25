package ir.etkastores.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import ir.etkastores.app.R;

/**
 * Created by Sajad on 1/22/18.
 */

public class IntentHelper {

    public static void openWayTracer(Context context, double lat, double lon){
        try {
            String uri = "geo:" + lat + "," + lon + "?q=";
            context.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
        }catch (Exception err){
            showWeb(context,getGoogleMapLocationAddress(lat,lon));
        }
    }

    public static void showWeb(Context context, String url){
        try {
            context.startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void share(Context context, String title, String body){
        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(sharingIntent, context.getResources().getString(R.string.shareUsing)));
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static String getGoogleMapLocationAddress(double lat, double lng){
        return "https://www.google.com/maps/search/?api=1&query="+lat+","+lng;
    }

    public static void showDialer(Context context, String number){
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number));
            context.startActivity(intent);
        }catch (Exception err){
            err.printStackTrace();
        }
    }

}
