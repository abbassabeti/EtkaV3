package ir.etkastores.app.Fragments.Home.HomeSlideSections;

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
import ir.etkastores.app.R;

/**
 * Created by Sajad on 11/24/17.
 */

public class SliderSection extends Fragment {

    public static SliderSection newInstance(){
        return new SliderSection();
    }

    private View view;

    @BindView(R.id.pager)
    ViewPager pager;

    private HomeCategorySliderPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.home_section_slider,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    private void initViews(){
        List<String> items = new ArrayList<>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        adapter = new HomeCategorySliderPagerAdapter(getActivity(),items);
        pager.setAdapter(adapter);
        pager.setCurrentItem(items.size()-1);
        pager.setOffscreenPageLimit(items.size());
    }

}
