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
import ir.etkastores.app.Adapters.ViewPagerAdapters.HomeCategorySliderPagerAdapter;
import ir.etkastores.app.Fragments.Home.HomeSlideSections.SliderSection;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Widgets.ViewPager16X9;

/**
 * Created by Sajad on 11/24/17.
 */

public class HomeSlidesFragment extends Fragment {

    public final static int FOR_YOU = 1;
    public final static int SPECIAL_OFFERS = 2;
    public final static int TOP_SALES = 3;
    public final static int ETKA_EXCLUSIVE_WARES = 4;
    public final static int HEKMAT_WARES = 5;

    public static HomeSlidesFragment newInstance(int type){
        return new HomeSlidesFragment();
    }

    @BindView(R.id.testPager)
    ViewPager16X9 pager;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_slide, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        return view;
    }

    private void initViews(){
        List<String> items = new ArrayList<>();
        items.add("4");
        items.add("3");
        items.add("2");
        items.add("1");

        HomeCategorySliderPagerAdapter adapter = new HomeCategorySliderPagerAdapter(getActivity(),items);
        pager.setAdapter(adapter);
        pager.setCurrentItem(items.size()-1);
        pager.setOffscreenPageLimit(items.size());

//        SliderSection sliderSection = SliderSection.newInstance();
//        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.headerFrameLayout,sliderSection).commit();
    }

}
