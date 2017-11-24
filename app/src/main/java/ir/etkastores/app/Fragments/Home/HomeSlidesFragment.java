package ir.etkastores.app.Fragments.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ir.etkastores.app.R;

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

    }

}
