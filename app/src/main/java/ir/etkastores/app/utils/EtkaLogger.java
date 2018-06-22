package ir.etkastores.app.utils;

import android.util.Log;

public class EtkaLogger {

    public static void log(String title, String message){
        Log.e("ETK "+title,message);
    }

}
