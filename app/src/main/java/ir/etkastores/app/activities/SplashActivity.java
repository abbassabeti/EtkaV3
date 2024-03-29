package ir.etkastores.app.activities;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.notification.NotificationModel;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.utils.EtkaPushNotificationConfig;
import ir.etkastores.app.utils.EtkaRemoteConfigManager;
import ir.etkastores.app.webServices.AccessToken;
import ir.etkastores.app.webServices.ApiProvider;
import ir.etkastores.app.webServices.ApiStatics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class SplashActivity extends BaseActivity {

    private NotificationModel notificationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EtkaPushNotificationConfig.checkSubscribes();
        EtkaRemoteConfigManager.checkRemoteConfigs();

        if (BuildConfig.DEBUG) {
            EtkaPushNotificationConfig.subscribeDev();
        } else {
            EtkaPushNotificationConfig.unSubscribeDev();
        }

        if (getIntent() != null && getIntent().hasExtra(NotificationModel.IS_FROM_NOTIFICATION)) {
            try {
                notificationModel = NotificationModel.fromJson(getIntent().getStringExtra(NotificationModel.NOTIFICATION_OBJECT));
            } catch (Exception err) {
                notificationModel = null;
            }
        }

        if (DiskDataHelper.getForceUpdateVersion() > BuildConfig.VERSION_CODE) return;

        if (ApiStatics.getLastToken() == null) {
            showLogin();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isFinishing()) return;
                    gotoApp();
                }
            }, 3000);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Splash Activity");
        AdjustHelper.sendAdjustEvent(AdjustHelper.SplashOpen);
    }

    private void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) return;
                try {
                    login();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }, 500);
    }

    Call<AccessToken> loginRequest;

    private void login() {
//        loginRequest = ApiProvider.getLogin(ProfileManager.getUserName(), ProfileManager.getUserPassword());
        loginRequest = ApiProvider.getInstance().guestLogin();
        loginRequest.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    ApiStatics.saveToken(response.body());
                    if (ProfileManager.getInstance().isGuest()) {
                        gotoApp();
                    } else {
                        loadProfile();
                    }
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                showRetryDialog(null);
            }
        });
    }

    void showRetryDialog(String message) {
        final MessageDialog messageDialog = MessageDialog.loginError(TextUtils.isEmpty(message) ? getResources().getString(R.string.anErrorHappendInServerConnection) : message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    login();
                } else {
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {
                messageDialog.getDialog().cancel();
            }
        });
    }

    private void loadProfile() {
        ApiProvider.getInstance().getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        ProfileManager.getInstance().saveProfile(response.body().getData());
                        gotoApp();
                    } else {
                        showRetryDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {
                showRetryDialog(null);
            }
        });
    }

    private void gotoApp() {
        if (ProfileManager.getInstance().isFirstRun() && notificationModel == null) {
            WalkthroughActivity.show(this);
        } else {
            MainActivity.show(this, notificationModel);
        }
        finish();
    }

}
