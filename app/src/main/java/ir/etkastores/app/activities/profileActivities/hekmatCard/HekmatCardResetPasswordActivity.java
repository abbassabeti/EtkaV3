package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.HekmatSetPasswordRequest;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class HekmatCardResetPasswordActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String CARD_NUMBER = "CARD_NUMBER";

    public static void show(Context context, String cardNumber) {
        Intent intent = new Intent(context, HekmatCardResetPasswordActivity.class);
        intent.putExtra(CARD_NUMBER, cardNumber);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.cardNumberEt)
    EditText cardNumberEt;

    @BindView(R.id.employeeNumberEt)
    EditText employeeNumberEt;

    @BindView(R.id.passwordEt)
    EditText passwordEt;

    @BindView(R.id.confirmPasswordEt)
    EditText confirmPasswordEt;

    private String cardNumber;

    private HekmatSetPasswordRequest requestModel;

    private AlertDialog loadingDialog;
    private Call<OauthResponse<String>> req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.getInstance().isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_hekmat_kard_reset_password);
        ButterKnife.bind(this);
        cardNumber = getIntent().getExtras().getString(CARD_NUMBER, "");
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Reset Password Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        cardNumberEt.setText(cardNumber);
        passwordEt.setTransformationMethod(new PasswordTransformationMethod());
        confirmPasswordEt.setTransformationMethod(new PasswordTransformationMethod());

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.submitButton)
    public void onSubmit() {

        if (TextUtils.isEmpty(cardNumberEt.toString()) || cardNumberEt.getText().length() < 16) {
            Toaster.show(this, R.string.enterHekmatCardNumberCorreclty);
            return;
        }

        if (TextUtils.isEmpty(employeeNumberEt.getText())) {
            Toaster.show(this, R.string.enterYourEmployeeNumber);
            return;
        }

        if (TextUtils.isEmpty(passwordEt.getText().toString())) {
            Toaster.show(this, R.string.enterPassword);
            return;
        }

        if (passwordEt.getText().toString().length() < 6) {
            Toaster.show(this, R.string.passwortMustBeAtLeast6Character);
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enterConfirmPassword);
            return;
        }

        if (!passwordEt.getText().toString().contentEquals(confirmPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enteredPasswordNotMathWithConfirmPassword);
            return;
        }

        requestModel = new HekmatSetPasswordRequest();
        requestModel.setPAN(cardNumberEt.getText().toString());
        requestModel.setEmployeeNumber(employeeNumberEt.getText().toString());
        requestModel.setNewPassword(passwordEt.getText().toString());
        requestModel.setConfirmPassword(confirmPasswordEt.getText().toString());
        sendRequest();
    }

    private void sendRequest() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inSendingRequest);
        req = ApiProvider.getInstance().getAuthorizedApi().resetHekmatPassword(requestModel);
        req.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.show(HekmatCardResetPasswordActivity.this, R.string.resetPasswordSuccessFully);
                        finish();
                    } else {
                        showRetry(response.body().getMeta().getMessage(), false);
                    }
                } else {
                    onFailure(call, null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                showRetry(getResources().getString(R.string.errorInSendingRequest), true);
            }
        });
    }


    private void showRetry(String message, boolean showRetry) {
        String retry = getResources().getString(R.string.retry);
        if (!showRetry) retry = null;
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                getResources().getString(R.string.error),
                message,
                retry,
                getResources().getString(R.string.close));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    sendRequest();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
