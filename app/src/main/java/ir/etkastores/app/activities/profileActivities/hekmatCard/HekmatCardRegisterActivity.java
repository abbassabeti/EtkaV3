package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class HekmatCardRegisterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String CARD_NUMBER = "CARD_NUMBER";
    private final static String PASSWORD = "PASSWORD";

    public static void show(Context context, String cardNumber, String password) {
        Intent intent = new Intent(context, HekmatCardRegisterActivity.class);
        intent.putExtra(CARD_NUMBER, cardNumber);
        intent.putExtra(PASSWORD, password);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;


    @BindView(R.id.phoneNumberEt)
    EditText phoneNumberEt;

    @BindView(R.id.cardNumberEt)
    EditText cardNumberEt;

    @BindView(R.id.employeeNumberEt)
    EditText employeeNumberEt;

    @BindView(R.id.passwordEt)
    EditText passwordEt;

    @BindView(R.id.confirmPasswordEt)
    EditText confirmPasswordEt;

    private String cardNumber;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat_card_register);
        ButterKnife.bind(this);
        cardNumber = getIntent().getExtras().getString(CARD_NUMBER,"");
        password = getIntent().getExtras().getString(PASSWORD,"");
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        passwordEt.setTransformationMethod(new PasswordTransformationMethod());
        confirmPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
        phoneNumberEt.setText(ProfileManager.getProfile().getCellPhone());
        cardNumberEt.setText(cardNumber);
        passwordEt.setText(password);
        phoneNumberEt.requestFocus();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
