package ir.etkastores.app.Fragments.Home;

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
import ir.etkastores.app.UI.Views.CategoryGroupHorizontalView;
import ir.etkastores.app.UI.Views.CategorySliderView;

/**
 * Created by Sajad on 12/2/17.
 */

public class EtkaExclusiveWaresSlide extends Fragment implements PageTrigger {

    public static EtkaExclusiveWaresSlide newInstance(){
        return new EtkaExclusiveWaresSlide();
    }

    View view;

    @BindView(R.id.itemsHolder)
    LinearLayout itemsHolder;

    boolean isFirstSelect = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_slide, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    private void initViews(){
        itemsHolder.addView(new CategoryGroupHorizontalView(getActivity()));
    }

    @Override
    public void onPageSelected() {
        if (!isFirstSelect) return;
        isFirstSelect = false;
        initViews();
    }
}
