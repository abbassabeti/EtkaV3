package ir.etkastores.app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

/**
 * Created by Sajad on 9/1/17.
 */

public class ProfileFragment extends Fragment {

    public final static int IN_TABBAR_POSITION = 0;

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_profile,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    private void initViews(){
        toolbar.hideBack();
        toolbar.setTitle(R.string.profile);
    }

}
