package ir.etkastores.app.Fragments.IntroFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;

/**
 * Created by Sajad on 9/29/17.
 */

public class RegisterFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    public static final String TAG = "REGISTER_FRAGMENT_TAG";

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.register);
        toolbar.setActionListeners(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.introFragmentsHolder,new LoginFragment(),LoginFragment.TAG,true);
    }

    @Override
    public void onToolbarBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
