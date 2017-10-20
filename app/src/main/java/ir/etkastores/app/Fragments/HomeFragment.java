package ir.etkastores.app.Fragments;

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
import ir.etkastores.app.Fragments.SupportFragments.ContactUsFramgment;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.RTLTabLayout;

/**
 * Created by Sajad on 9/1/17.
 */

public class HomeFragment extends Fragment {

    private View view;

    @BindView(R.id.homeToolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    GlobalFragmentPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_home,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    private void initViews(){
        createDummyPages();
    }

    private void createDummyPages(){
        List<FragmentTitleModel> pages = new ArrayList<>();

        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب اول"));
        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب دوم"));
        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب سوم"));
        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب چهارم"));
        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب پنجم"));
        pages.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),"تب ششم"));

        pagerAdapter = new GlobalFragmentPagerAdapter(getFragmentManager(),pages);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
