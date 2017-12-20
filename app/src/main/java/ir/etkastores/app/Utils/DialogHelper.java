package ir.etkastores.app.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;

/**
 * Created by Sajad on 12/20/17.
 */

public class DialogHelper {

    public static AlertDialog showLoading(Context context,int message){
        if (context == null) return null;
        return showLoading(context,context.getString(message));
    }

    public static AlertDialog showLoading(Context context,String message){
        if (context == null) return null;
        AlertDialog alertDialog = new ProgressDialog.Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.show();
        alertDialog.setCancelable(false);
        return alertDialog;
    }

}
