package ir.etkastores.app.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.IntroFragments.LoginFragment;
import ir.etkastores.app.Fragments.IntroFragments.RegisterFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;

public class LoginRegisterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String TYPE = "TYPE";
    private final static int LOGIN = 1;
    private final static int REGISTER = 2;

    public static void showLogin(Context context){
        Intent intent = new Intent(context,LoginRegisterActivity.class);
        intent.putExtra(TYPE,LOGIN);
        context.startActivity(intent);
    }

    public static void showRegister(Context context){
        Intent intent = new Intent(context,LoginRegisterActivity.class);
        intent.putExtra(TYPE,REGISTER);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        type = getIntent().getIntExtra(TYPE,0);
        if (type == LOGIN){
            showLogin();
        }else{
            showRegister();
        }
    }

    private void showLogin(){
        ActivityUtils.addFragment(this, R.id.loginRegisterFragmentHolder, new LoginFragment(), LoginFragment.TAG, false);
    }

    private void showRegister(){
        ActivityUtils.addFragment(this, R.id.loginRegisterFragmentHolder, new RegisterFragment(), RegisterFragment.TAG, false);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
