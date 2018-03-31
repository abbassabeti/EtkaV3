package ir.etkastores.app.utils;

import android.graphics.Typeface;

import ir.etkastores.app.EtkaApp;

/**
 * Created by Sajad on 10/19/17.
 */

public class FontUtils {

    private static Typeface commonTypeFace = null;
    private static Typeface boldTypeFace = null;

    public static Typeface getCommonTypeFace(){
        if (commonTypeFace == null)commonTypeFace = Typeface.createFromAsset(EtkaApp.getInstance().getAssets(), "fonts/IRANSans-Regular.ttf");
        return commonTypeFace;
    }

    public static Typeface getBoldTypeFace(){
        if (boldTypeFace == null)boldTypeFace = Typeface.createFromAsset(EtkaApp.getInstance().getAssets(), "fonts/IRANSans-Bold.ttf");
        return boldTypeFace;
    }

}
