package ir.etkastores.app.Activities;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.IntroFragments.LoginFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;

public class LoginRegisterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        ActivityUtils.addFragment(this, R.id.introFragmentsHolder, new LoginFragment(), LoginFragment.TAG, false);
        toolbar.setActionListeners(this);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
