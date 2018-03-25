package ir.etkastores.app.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.profile.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.Utils.EtkaPushNotificationConfig;
import ir.etkastores.app.Utils.IntentHelper;
import ir.etkastores.app.WebService.AccessToken;
import ir.etkastores.app.WebService.ApiProvider;
import ir.etkastores.app.WebService.ApiStatics;
import ir.etkastores.app.data.ProfileManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EtkaPushNotificationConfig.registerGlobal();
        checkRemoteConfigs();
    }

    private void checkRemoteConfigs() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings settings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true).build();
        firebaseRemoteConfig.setConfigSettings(settings);
        firebaseRemoteConfig.setDefaults(R.xml.remote_defaults);

        firebaseRemoteConfig.fetch(180).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseRemoteConfig.activateFetched();
                    String baseUrl = firebaseRemoteConfig.getString("v3_api_server");
                    String minVersion = firebaseRemoteConfig.getString("v3_force_update_min_version_code");
                    final String updateUrl = firebaseRemoteConfig.getString("v3_force_update_url");
                    int minAppVersion = Integer.parseInt(minVersion);
                    if (minAppVersion > BuildConfig.VERSION_CODE) {
                        MessageDialog dialog = MessageDialog.forceUpdate();
                        dialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
                            @Override
                            public void onDialogMessageButtonsClick(int button) {
                                if (button == RIGHT_BUTTON) {
                                    IntentHelper.showWeb(SplashActivity.this, updateUrl);
                                } else {
                                    finish();
                                }
                            }

                            @Override
                            public void onDialogMessageDismiss() {

                            }
                        });
                        return;
                    }
                    ApiStatics.setBaseUrl(baseUrl);
                } else {

                }
                prepareAppForRun();
            }
        });

    }

    private void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    login();
                }catch (Exception err){
                    err.printStackTrace();
                }
            }
        }, 500);
    }

    private void initMap() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mapView = new MapView(SplashActivity.this);
                    mapView.onCreate(null);
                    showLogin();
                } catch (Exception err) {
                }
            }
        }, 100);
    }

    private void prepareAppForRun() {
        initMap();
    }


    Call<AccessToken> loginRequest;
    private void login(){
        loadingDialog = DialogHelper.showLoading(this,R.string.inLogin);
        loginRequest = ApiProvider.getLogin(ProfileManager.getUserName(),ProfileManager.getUserPassword());
        loginRequest.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                loadingDialog.cancel();
                if (response.isSuccessful()){
                    ApiStatics.saveToken(response.body());
                    if (ProfileManager.isGuest()){
                        gotoApp();
                    }else{
                        loadProfile();
                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                loadingDialog.cancel();
                showRetryDialog();
            }
        });
    }

    void showRetryDialog(){
        final MessageDialog messageDialog = MessageDialog.loginError();
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    login();
                }else{
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

    private void loadProfile(){
        loadingDialog = DialogHelper.showLoading(this,R.string.inLoadingUserProfileInfo);
        ApiProvider.getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        ProfileManager.saveProfile(response.body().getData());
                        gotoApp();
                    }else{
                        showRetryDialog();
                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {
                loadingDialog.cancel();
                showRetryDialog();
            }
        });
    }

    private void gotoApp(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
