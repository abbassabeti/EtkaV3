package ir.etkastores.app.Fragments.IntroFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.ActivityUtils;

/**
 * Created by Sajad on 9/29/17.
 */

public class IntroFragment extends Fragment {

    public static final String TAG = "INTRO_FRAGMENT_TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.introFragmentsHolder,new RegisterFragment(),"",true);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.introFragmentsHolder,new LoginFragment(),"",true);
    }

}
