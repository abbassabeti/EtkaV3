package ir.etkastores.app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.AccessToken;
import ir.etkastores.app.webServices.ApiProvider;
import ir.etkastores.app.webServices.ApiStatics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithSMSActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context) {
        context.startActivity(new Intent(context, LoginWithSMSActivity.class));
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.mobilePhoneNumber)
    EditText phoneEt;

    @BindView(R.id.verification)
    EditText verifyEt;

    @BindView(R.id.submitVerification)
    Button submitButton;

    @BindView(R.id.messageTitle)
    TextView messageTitle;

    private Call<OauthResponse<String>> verificationReq;
    private boolean isVerifyStep = false;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_sms);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        setupEnterPhoneNumber();
    }

    @OnClick(R.id.submitVerification)
    public void onSubmitButtonClick() {
        if (!isVerifyStep) {
            sendVerificationCode();
        } else {
            loginWithVerificationCode();
        }
    }

    private void sendVerificationCode() {
        verificationReq = ApiProvider.getApi().requestVerificationCode(phoneEt.getText().toString());
        verificationReq.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.showLong(LoginWithSMSActivity.this, response.body().getMeta().getMessage());
                        setupEnterVerifyCode();
                        isVerifyStep = true;
                    } else {
                        Toaster.showLong(LoginWithSMSActivity.this, response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                if (isFinishing()) return;
            }
        });
    }

    private void handleRetryVerification() {

    }

    public void loginWithVerificationCode() {
        Call<AccessToken> login = ApiProvider.getLoginWithSMSVerification(phoneEt.getText().toString(), verifyEt.getText().toString());
        login.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    AdjustHelper.sendAdjustEvent(AdjustHelper.Login);
                    ApiStatics.saveToken(response.body());
                    loadProfile();
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                if (isFinishing()) return;
            }
        });
    }

    private void loadProfile() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingUserProfileInfo);
        ApiProvider.getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        ProfileManager.saveProfile(response.body().getData());
                        Toaster.showLong(LoginWithSMSActivity.this, R.string.loginSuccessfulMessage);
                        LoginWithSMSActivity.this.finish();
                    } else {
//                        showRetryDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {
                if (isFinishing()) return;
                loadingDialog.cancel();
//                showRetryDialog(getResources().getString(R.string.errorInLogin));
            }
        });
    }

    public void setupEnterPhoneNumber() {
        phoneEt.setVisibility(View.VISIBLE);
        verifyEt.setVisibility(View.GONE);
        submitButton.setText(R.string.requestActivationCode);
        messageTitle.setText(R.string.enterPhoneNumberMessageForVerify);
    }

    public void setupEnterVerifyCode() {
        phoneEt.setVisibility(View.GONE);
        verifyEt.setVisibility(View.VISIBLE);
        submitButton.setText(R.string.checkActivationCode);
        messageTitle.setText(R.string.enterVerificationCode);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
