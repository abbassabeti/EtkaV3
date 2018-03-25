package ir.etkastores.app.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;

import ir.etkastores.app.EtkaApp;

/**
 * Created by Sajad on 10/19/17.
 */

public class FragmentTitleModel {

    private Fragment fragment;
    private String title;

    public FragmentTitleModel(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public FragmentTitleModel(Fragment fragment,int title) {
        this.fragment = fragment;
        this.title = EtkaApp.getInstance().getResources().getString(title);
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
