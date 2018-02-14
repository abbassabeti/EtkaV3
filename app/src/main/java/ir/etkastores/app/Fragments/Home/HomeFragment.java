package ir.etkastores.app.Fragments.Home;

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
import ir.etkastores.app.Adapters.ViewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.Adapters.ViewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.RTLTabLayout;

/**
 * Created by Sajad on 9/1/17.
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{

    private View view;

//    @BindView(R.id.homeToolbar)
//    EtkaToolbar toolbar;

    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    List<FragmentTitleModel> pages = new ArrayList<>();
    GlobalFragmentPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews(){
        createDummyPages();
    }

    private void createDummyPages(){
        pages = new ArrayList<>();

        pages.add(new FragmentTitleModel(ForYouSlide.newInstance(),R.string.forYou));
//        pages.add(new FragmentTitleModel(SpecialOffersSlide.newInstance(),R.string.specialOffers));
//        pages.add(new FragmentTitleModel(TopSalesSlide.newInstance(),R.string.topSales));
//        pages.add(new FragmentTitleModel(EtkaExclusiveWaresSlide.newInstance(),R.string.etkaٰExclusiveWares));
        pages.add(new FragmentTitleModel(HekmatWaresSlide.newInstance(),R.string.hekmatWares));

        pagerAdapter = new GlobalFragmentPagerAdapter(getChildFragmentManager(),pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pages.size());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((PageTrigger)pages.get(position).getFragment()).onPageSelected();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
