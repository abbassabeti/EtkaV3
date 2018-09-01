package ir.etkastores.app.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Sajad on 9/29/17.
 */

public class ActivityUtils {

    public static void replaceFragment(FragmentActivity activity, int fragmentHolder, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction().replace(fragmentHolder, fragment, tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static void replaceChildFragment(Fragment parentFragment, int fragmentHolder, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction().replace(fragmentHolder, fragment, tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static void addFragment(FragmentActivity activity, int fragmentHolder, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction().add(fragmentHolder, fragment, tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static void addChildFragment(Fragment parentFragment, int fragmentHolder, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction().add(fragmentHolder, fragment, tag);
        if (addToBackStack) transaction.addToBackStack("");
        transaction.commitAllowingStateLoss();
    }

    public static Fragment getFragmentByTag(FragmentActivity activity, String tag) {
        try {
            return activity.getSupportFragmentManager().findFragmentByTag(tag);
        } catch (Exception err) {
            return null;
        }
    }

}
