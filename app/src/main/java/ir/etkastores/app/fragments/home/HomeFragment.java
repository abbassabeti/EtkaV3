package ir.etkastores.app.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.viewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.adapters.viewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.ui.views.RTLTabLayout;

/**
 * Created by Sajad on 9/1/17.
 */

public class HomeFragment extends Fragment {

    private View view;

    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    List<FragmentTitleModel> pages = new ArrayList<>();
    GlobalFragmentPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Home Tab Fragment");
    }

    private void initViews() {
        createPages();
    }

    private void createPages() {
        pages = new ArrayList<>();

//        pages.add(new FragmentTitleModel(ForYouSlide.newInstance(),R.string.forYou));
        pages.add(new FragmentTitleModel(SpecialOffersSlide.newInstance(), R.string.specialOffers));
//        pages.add(new FragmentTitleModel(TopSalesSlide.newInstance(),R.string.topSales));
//        pages.add(new FragmentTitleModel(EtkaExclusiveWaresSlide.newInstance(),R.string.etkaٰExclusiveWares));
        pages.add(new FragmentTitleModel(HekmatWaresSlide.newInstance(), R.string.hekmatWares));
        pages.add(new FragmentTitleModel(NewsListFragment.newInstance(), R.string.news));

        pagerAdapter = new GlobalFragmentPagerAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pages.size());
        tabLayout.setupWithViewPager(viewPager);
    }

}
