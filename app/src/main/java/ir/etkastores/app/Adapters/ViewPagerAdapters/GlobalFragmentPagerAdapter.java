package ir.etkastores.app.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Sajad on 9/1/17.
 */

public class GlobalFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> pages;

    public GlobalFragmentPagerAdapter(FragmentManager fm, List<Fragment> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "پشتیبانی" + (position + 1);
    }

}
