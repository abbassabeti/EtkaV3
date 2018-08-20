package ir.etkastores.app.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.GetVerificationCodeResponse;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.utils.EventsManager;
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
    Button submitVerificationButton;

    @BindView(R.id.requestVerificationCode)
    Button requestVerificationCodeButton;

    @BindView(R.id.messageTitle)
    TextView messageTitle;

    @BindView(R.id.countDownTimer)
    TextView countDownTimerTv;

    @BindView(R.id.invitationCode)
    EditText invitationCodeEt;

    @BindView(R.id.invitationCodeHolder)
    View invitationCodeHolder;

    private Call<OauthResponse<GetVerificationCodeResponse>> verificationReq;
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
    public void onSubmitVerificationClick() {
        if (verifyEt.getText().toString().isEmpty()) {
            Toaster.show(this, R.string.pleaseEnterVerificationCode);
            return;
        }
        loginWithVerificationCode();
    }

    @OnClick(R.id.requestVerificationCode)
    public void onRequestVerificationCodeClick() {
        if (isTimerRunning) {
            Toaster.show(this, String.format(getResources().getString(R.string.youCanRequestActivationCodeAfterXSecond), counter));
            return;
        }
        sendVerificationCode();
    }

    private void sendVerificationCode() {
        hideKeyboard(phoneEt);
        showLoading(getResources().getString(R.string.inSendinfVerificationCode));
        verificationReq = ApiProvider.getApi().requestVerificationCode(phoneEt.getText().toString());
        verificationReq.enqueue(new Callback<OauthResponse<GetVerificationCodeResponse>>() {
            @Override
            public void onResponse(Call<OauthResponse<GetVerificationCodeResponse>> call, Response<OauthResponse<GetVerificationCodeResponse>> response) {
                if (isFinishing()) return;
                hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.showLong(LoginWithSMSActivity.this, response.body().getMeta().getMessage());
                        if (!response.body().getData().isNewUser()) showGetInvitationCodeView();
                        setupEnterVerifyCode();
                        isVerifyStep = true;
                        startTimer();
                    } else {
                        showRetryVerification(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<GetVerificationCodeResponse>> call, Throwable t) {
                if (isFinishing()) return;
                hideLoading();
                showRetryVerification(getResources().getString(R.string.errorInSendingVerificationCode));
            }
        });
    }

    public void loginWithVerificationCode() {
        hideKeyboard(verifyEt);
        showLoading(getResources().getString(R.string.inLogin));
        Call<AccessToken> login = ApiProvider.getLoginWithSMSVerification(phoneEt.getText().toString(), verifyEt.getText().toString(), invitationCodeEt.getText().toString());
        login.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (isFinishing()) return;
                hideLoading();
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
                hideLoading();
                Toaster.showLong(LoginWithSMSActivity.this, R.string.errorInLogin);
            }
        });
    }

    private void loadProfile() {
        showLoading(getResources().getString(R.string.inLoadingUserProfile));
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingUserProfileInfo);
        ApiProvider.getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (isFinishing()) return;
                hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        ProfileManager.saveProfile(response.body().getData());
                        Toaster.showLong(LoginWithSMSActivity.this, R.string.loginSuccessfulMessage);
                        LoginWithSMSActivity.this.finish();
                    } else {
                        Toaster.showLong(LoginWithSMSActivity.this, response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {
                if (isFinishing()) return;
                hideLoading();
                Toaster.showLong(LoginWithSMSActivity.this, R.string.errorInLogin);
            }
        });
    }

    public void setupEnterPhoneNumber() {
        phoneEt.setVisibility(View.VISIBLE);
        verifyEt.setVisibility(View.GONE);
        submitVerificationButton.setVisibility(View.GONE);
        messageTitle.setText(R.string.enterPhoneNumberMessageForVerify);
    }

    public void setupEnterVerifyCode() {
        phoneEt.setVisibility(View.GONE);
        verifyEt.setVisibility(View.VISIBLE);
        submitVerificationButton.setVisibility(View.VISIBLE);
        requestVerificationCodeButton.setVisibility(View.GONE);
        messageTitle.setText(R.string.enterVerificationCode);
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
        hideLoading();
    }

    void showLoading(String message) {
        hideLoading();
        loadingDialog = DialogHelper.showLoading(this, message);
    }

    void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    void showRetryVerification(String message) {
        if (isFinishing()) return;
        final MessageDialog messageDialog = MessageDialog.errorRetry(message);
        EventsManager.sendEvent("Dev", "Verification err", "|" + phoneEt.getText().toString() + "|" + message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    sendVerificationCode();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int counter;
    private boolean isTimerRunning = false;

    private void startTimer() {
        if (isFinishing()) return;
        counter = 180;
        isTimerRunning = true;
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(180 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (isFinishing()) return;
            counter--;
            int min = counter / 60;
            int sec = counter % 60;
            if (min > 0) {
                countDownTimerTv.setText(String.format(getResources().getString(R.string.youCanRequestActivationCodeAfterXMinAndXSecond), min, sec));
            } else {
                countDownTimerTv.setText(String.format(getResources().getString(R.string.youCanRequestActivationCodeAfterXSecond), sec));
            }
        }

        @Override
        public void onFinish() {
            if (isFinishing()) return;
            isTimerRunning = false;
            requestVerificationCodeButton.setVisibility(View.VISIBLE);
            countDownTimerTv.setText("");
        }
    };

    private void showGetInvitationCodeView() {
        invitationCodeHolder.setVisibility(View.VISIBLE);
    }

}
