package ir.etkastores.app.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.CategorySliderView;

/**
 * Created by Sajad on 12/2/17.
 */

public class ForYouSlide extends Fragment implements PageTrigger {

    public static ForYouSlide newInstance(){
        return new ForYouSlide();
    }

    View view;

    @BindView(R.id.itemsHolder)
    LinearLayout itemsHolder;

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
        itemsHolder.addView(new CategorySliderView(getActivity()));
//        itemsHolder.addView(new CategoryGroupHorizontalView(getActivity()));
//        itemsHolder.addView(new SpecialCategoriesView(getActivity()));
//        itemsHolder.addView(new CategoryGroupHorizontalView(getActivity()));
//        itemsHolder.addView(new CategoryGroupHorizontalView(getActivity()));
    }

    @Override
    public void onPageSelected() {

    }
}
