package ir.etkastores.app.fragments.home;

import android.app.Activity;
import android.content.Intent;
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
import ir.etkastores.app.activities.NewsActivity;
import ir.etkastores.app.activities.NewsListActivity;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.activities.ScannerActivity;
import ir.etkastores.app.adapters.viewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.adapters.viewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.R;
import ir.etkastores.app.data.StaticsData;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.RTLTabLayout;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.IntentHelper;

/**
 * Created by Sajad on 9/1/17.
 */

public class HomeFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    private View view;

    @BindView(R.id.homeToolbar)
    EtkaToolbar toolbar;

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
        toolbar.setActionListeners(this);
        createPages();
    }

    private void createPages() {
        pages = new ArrayList<>();

//        pages.add(new FragmentTitleModel(ForYouSlide.newInstance(),R.string.forYou));
        pages.add(new FragmentTitleModel(SpecialOffersSlide.newInstance(), R.string.specialOffers));
//        pages.add(new FragmentTitleModel(TopSalesSlide.newInstance(),R.string.topSales));
//        pages.add(new FragmentTitleModel(EtkaExclusiveWaresSlide.newInstance(),R.string.etkaٰExclusiveWares));
        pages.add(new FragmentTitleModel(HekmatWaresSlide.newInstance(), R.string.hekmatWares));

        pagerAdapter = new GlobalFragmentPagerAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(pages.size());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onToolbarBackClick() {

    }

    @Override
    public void onActionClick(int actionCode) {
        switch (actionCode) {
            case NEWS_BUTTON:
                AdjustHelper.sendAdjustEvent(AdjustHelper.NewsButton);
                NewsListActivity.show(getActivity());
                break;

            case SCANNER_BUTTON:
                ScannerActivity.show(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == ScannerActivity.SCAN_REQUEST_CODE){
            String code = data.getStringExtra(ScannerActivity.DATA);
            if (code.startsWith(StaticsData.etkaStoreScheme)){
                IntentHelper.showWeb(getActivity(),code);
            }else{
                ProductActivity.show(getActivity(),code);
            }
        }
    }
}
