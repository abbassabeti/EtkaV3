package ir.etkastores.app.utils;

import android.content.Context;
import android.os.Build;

import java.io.File;

import io.michaelrocks.paranoid.Obfuscate;

import static io.fabric.sdk.android.services.common.CommonUtils.isEmulator;

@Obfuscate
public class RootUtils {

    public static boolean isRooted(Context context) {
        boolean isEmulator = isEmulator(context);
        String buildTags = Build.TAGS;
        if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
            return true;
        } else {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            } else {
                file = new File("/system/xbin/su");
                return !isEmulator && file.exists();
            }
        }
    }


}
