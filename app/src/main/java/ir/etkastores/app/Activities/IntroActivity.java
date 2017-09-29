package ir.etkastores.app.Activities;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Fragments.IntroFragments.IntroFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.ActivityUtils;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_intro);
        ButterKnife.bind(this);
        ActivityUtils.addFragment(this,R.id.introFragmentsHolder,new IntroFragment(),IntroFragment.TAG,false);
    }


}
