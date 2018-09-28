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

    private final static String SELECTED_TAB_KEY = "SELECTED_TAB";

    public static HomeFragment newInstance(int selectedTab) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED_TAB_KEY, selectedTab);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    private View view;

    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    List<FragmentTitleModel> pages = new ArrayList<>();
    GlobalFragmentPagerAdapter pagerAdapter;

    private int selectedTab;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedTab = getArguments().getInt(SELECTED_TAB_KEY);
    }

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

        pages.add(new FragmentTitleModel(SpecialOffersSlide.newInstance(), R.string.specialOffers));
        pages.add(new FragmentTitleModel(HekmatWaresSlide.newInstance(), R.string.hekmatWares));
        pages.add(new FragmentTitleModel(NewsListFragment.newInstance(), R.string.news));

        pagerAdapter = new GlobalFragmentPagerAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pages.size());
        tabLayout.setupWithViewPager(viewPager);

        switch (selectedTab) {

            case SpecialOffersSlide.TAB_POSITION_ID:
                viewPager.setCurrentItem(2);
                break;

            case HekmatWaresSlide.TAB_POSITION_ID:
                viewPager.setCurrentItem(1);
                break;

            case NewsListFragment.TAB_POSITION_ID:
                viewPager.setCurrentItem(0);
                break;

        }
    }

}
