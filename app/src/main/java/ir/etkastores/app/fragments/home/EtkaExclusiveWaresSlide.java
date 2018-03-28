package ir.etkastores.app.fragments.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 12/2/17.
 */

public class EtkaExclusiveWaresSlide extends Fragment {

    public static EtkaExclusiveWaresSlide newInstance(){
        return new EtkaExclusiveWaresSlide();
    }

    View view;

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

    }

}
