package ir.etkastores.app.Activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.IntroFragments.IntroFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.ActivityUtils;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        ActivityUtils.addFragment(this, R.id.introFragmentsHolder, new IntroFragment(), IntroFragment.TAG, false);
    }


}
