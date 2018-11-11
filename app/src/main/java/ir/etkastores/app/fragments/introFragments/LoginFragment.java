package ir.etkastores.app.fragments.introFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.dialogs.ResetPasswordDialog;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.AccessToken;
import ir.etkastores.app.webServices.ApiProvider;
import ir.etkastores.app.webServices.ApiStatics;
import ir.etkastores.app.data.ProfileManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/29/17.
 */

public class LoginFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener,
        AppCompatCheckBox.OnCheckedChangeListener {

    public static final String TAG = "LOGIN_FRAGMENT_TAG";

    private final int LOGIN_TYPE_EMAIL = 1;
    private final int LOGIN_TYPE_CLUBE_CARD = 2;

    private int loginType;

    @BindView(R.id.clubCardCheckBox)
    AppCompatCheckBox clubCardCheckBox;
    @BindView(R.id.emailAddressInputHolder)
    View emailAddressInputHolder;
    @BindView(R.id.passwordInputHolder)
    View passwordInputHolder;
    @BindView(R.id.clubCardNumberInputHolder)
    View clubCardNumberInputHolder;
    @BindView(R.id.clubCardPasswordInputHolder)
    View clubCardPasswordInputHolder;
    @BindView(R.id.emailAddressInput)
    AppCompatEditText emailAddressInput;
    @BindView(R.id.passwordInput)
    AppCompatEditText passwordInput;
    @BindView(R.id.clubCardNumberInput)
    AppCompatEditText clubCardNumberInput;
    @BindView(R.id.clubCardPasswordInput)
    AppCompatEditText clubCardPasswordInput;

    View view;

    AlertDialog loadingDialog;
    private ResetPasswordDialog resetPasswordDialog;
    private Call<OauthResponse<String>> resetPasswordReq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clubCardCheckBox.setOnCheckedChangeListener(this);

        showManualLoginControl();

        initToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
//        String userEmail = UserSettings.getEmailAddress();
//        String password = UserSettings.getPasswrod();
//        if (!TextUtils.isEmpty(userEmail)) {
//            emailAddressInput.setText(userEmail);
//        }
//
//        if (!TextUtils.isEmpty(password)) {
//            passwordInput.setText(password);
//        }
    }

    private void initToolbar() {

    }

    @OnClick(R.id.clubCardCheckBoxHolder)
    public void onClubCardCheckBoxHolderClick() {
        clubCardCheckBox.performClick();
    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick() {
        ActivityUtils.replaceFragment(getActivity(), R.id.loginRegisterFragmentHolder, new RegisterFragment(), RegisterFragment.TAG, true);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick() {
        String userName, password;

        if (loginType == LOGIN_TYPE_CLUBE_CARD) {
            userName = clubCardNumberInput.getText().toString();
            password = clubCardPasswordInput.getText().toString();
        } else {
            userName = emailAddressInput.getText().toString();
            password = passwordInput.getText().toString();
        }

        if (TextUtils.isEmpty(userName)) {
            Toaster.show(getActivity(), "userName is empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toaster.show(getActivity(), "password is empty");
            return;
        }

        login(userName, password);
    }

    @OnClick(R.id.forgotPasswordButton)
    public void onForgotPasswordClick() {
        resetPasswordDialog = new ResetPasswordDialog();
        resetPasswordDialog.show(getChildFragmentManager(), new ResetPasswordDialog.OnUserEnterPhoneNumberToResetListener() {
            @Override
            public void onUserSetPhoneNumberToReset(String phone) {
                resetPasswordReq = ApiProvider.getInstance().getApi().resetPassword(phone);
                sendResetPasswordRequest();
            }
        });
    }

    private void sendResetPasswordRequest() {
        resetPasswordReq.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        showSuccessResetPasswordDialog(response.body().getMeta().getMessage());
                        resetPasswordDialog.getDialog().cancel();
                    } else {
                        Toaster.showLong(getActivity(), response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable throwable) {
                if (!isAdded()) return;
                Toaster.showLong(getActivity(), R.string.errorInResettingPasswordTryLater);
            }
        });
    }

    private void showSuccessResetPasswordDialog(final String message) {
        final MessageDialog messageDialog = MessageDialog.resetPasswordSuccess(message);
        messageDialog.show(getChildFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (!isAdded()) return;
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @Override
    public void onToolbarBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean hasCard) {
        if (hasCard)
            showClubCardLoginControl();
        else
            showManualLoginControl();
    }

    private void showManualLoginControl() {
        loginType = LOGIN_TYPE_EMAIL;
//        passwordInput.setText("#abcE1234#");
//        emailAddressInput.setText("sajadgarshasbi@gmail.com");
        emailAddressInputHolder.setVisibility(View.VISIBLE);
        passwordInputHolder.setVisibility(View.VISIBLE);
        clubCardNumberInputHolder.setVisibility(View.GONE);
        clubCardPasswordInputHolder.setVisibility(View.GONE);
        passwordInput.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void showClubCardLoginControl() {
        loginType = LOGIN_TYPE_CLUBE_CARD;
        emailAddressInputHolder.setVisibility(View.GONE);
        passwordInputHolder.setVisibility(View.GONE);
        clubCardNumberInputHolder.setVisibility(View.VISIBLE);
        clubCardPasswordInputHolder.setVisibility(View.VISIBLE);
        clubCardPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
    }

    Call<AccessToken> loginRequest;

    private void login(final String userName, final String password) {
//        loadingDialog = DialogHelper.showLoading(getActivity(), R.string.inLogin);
//        loginRequest = ApiProvider.getLogin(userName, password);
//        loginRequest.enqueue(new Callback<AccessToken>() {
//            @Override
//            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//                if (!isAdded()) return;
//                loadingDialog.cancel();
//                if (response.isSuccessful()) {
//                    AdjustHelper.sendAdjustEvent(AdjustHelper.Login);
//                    ApiStatics.saveToken(response.body());
//                    ProfileManager.saveUserNameAndPassword(userName, password);
//                    loadProfile();
//                } else {
//                    onFailure(null, null);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AccessToken> call, Throwable t) {
//                if (!isAdded()) return;
//                loadingDialog.cancel();
//                showRetryDialog(getResources().getString(R.string.anErrorHappendInServerConnection));
//            }
//        });
    }

    @Override
    public void onPause() {
        cancelRequest();
        super.onPause();
    }

    private void cancelRequest() {
        if (loginRequest != null) loginRequest.cancel();
    }

    void showRetryDialog(String message) {
        final MessageDialog messageDialog = MessageDialog.loginError(message);
        messageDialog.show(getChildFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (!isAdded()) return;
                if (button == RIGHT_BUTTON) {
                    onLoginClick();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {
                if (!isAdded()) return;
                messageDialog.getDialog().cancel();
            }
        });
    }

    private void loadProfile() {
        loadingDialog = DialogHelper.showLoading(getActivity(), R.string.inLoadingUserProfileInfo);
        ApiProvider.getInstance().getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        ProfileManager.getInstance().saveProfile(response.body().getData());
                        Toaster.showLong(getActivity(), R.string.loginSuccessfulMessage);
                        getActivity().finish();
                    } else {
                        showRetryDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {
                if (!isAdded()) return;
                loadingDialog.cancel();
                showRetryDialog(getResources().getString(R.string.errorInLogin));
            }
        });
    }

}
