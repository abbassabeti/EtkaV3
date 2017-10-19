package ir.etkastores.app.Adapters.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sajad on 9/1/17.
 */

public class GlobalFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentTitleModel> pages;

    public GlobalFragmentPagerAdapter(FragmentManager fm, List<FragmentTitleModel> pages) {
        super(fm);
        this.pages = pages;
        Collections.reverse(this.pages);
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }

}
