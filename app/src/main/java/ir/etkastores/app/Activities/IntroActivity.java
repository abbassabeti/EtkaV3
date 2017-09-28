package ir.etkastores.app.Activities;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.ResetPasswordDialog;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        new ResetPasswordDialog().show(getSupportFragmentManager(),"");

    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
