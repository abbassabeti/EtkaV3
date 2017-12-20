package ir.etkastores.app.UI;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sajad on 12/20/17.
 */

public class Toaster {

    public static void showLong(Context context, String message){
        show(context,message, Toast.LENGTH_LONG);
    }

    public static void showLong(Context context, int message){
        show(context,message, Toast.LENGTH_LONG);
    }

    public static void show(Context context, String message){
        show(context,message, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int message){
        show(context,message, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int message,int duration){
        if (context == null) return;
        show(context,context.getString(message),duration);
    }

    public static void show(Context context, String message,int duration){
        if (context == null) return;
        try{
            Toast.makeText(context,message,duration).show();
        }catch (Exception err){}
    }

}
