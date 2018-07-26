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
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.HekmatChangePasswordRequest;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HekmatCardChangePasswordActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context) {
        Intent intent = new Intent(context, HekmatCardChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.oldPasswordEt)
    EditText oldPasswordEt;

    @BindView(R.id.newPasswordEt)
    EditText newPasswordEt;

    @BindView(R.id.confirmPasswordEt)
    EditText confirmPasswordEt;

    private HekmatChangePasswordRequest requestModel;

    private AlertDialog loadingDialog;
    private Call<OauthResponse<String>> req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat_card_change_password);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    private void initViews() {
        newPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
        confirmPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
        oldPasswordEt.setTransformationMethod(new PasswordTransformationMethod());
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (req != null && req.isExecuted()) req.cancel();
    }

    @OnClick(R.id.submitButton)
    public void onSubmitButtonClick() {

        requestModel = new HekmatChangePasswordRequest();

        if (TextUtils.isEmpty(oldPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enterOldPassword);
            return;
        }

        if (TextUtils.isEmpty(newPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enterPassword);
            return;
        }

        if (newPasswordEt.getText().toString().length() < 6) {
            Toaster.show(this, R.string.passwortMustBeAtLeast6Character);
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enterConfirmPassword);
            return;
        }

        if (!newPasswordEt.getText().toString().contentEquals(confirmPasswordEt.getText().toString())) {
            Toaster.show(this, R.string.enteredPasswordNotMathWithConfirmPassword);
            return;
        }

        requestModel.setOldPassword(oldPasswordEt.getText().toString());
        requestModel.setNewPassword(newPasswordEt.getText().toString());
        requestModel.setConfirmNewPassword(confirmPasswordEt.getText().toString());

        sendRequest();
    }

    private void sendRequest() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inSendingRequest);
        req = ApiProvider.getAuthorizedApi().changeHekmatPassword(requestModel);
        req.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.show(HekmatCardChangePasswordActivity.this, R.string.passwordChangeSuccessfully);
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
