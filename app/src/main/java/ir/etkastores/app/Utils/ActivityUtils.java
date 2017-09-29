package ir.etkastores.app.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sajad on 9/29/17.
 */

public class ActivityUtils {

    public static void replaceFragment(FragmentActivity activity, int fragmentHolder, Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction().replace(fragmentHolder,fragment,tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static void addFragment(FragmentActivity activity,int fragmentHolder, Fragment fragment,String tag,boolean addToBackStack){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction().add(fragmentHolder,fragment,tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

}
