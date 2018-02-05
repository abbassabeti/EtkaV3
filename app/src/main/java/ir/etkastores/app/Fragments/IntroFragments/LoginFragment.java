package ir.etkastores.app.Fragments.IntroFragments;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.MainActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Dialogs.ResetPasswordDialog;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.Utils.UserSettings;
import ir.etkastores.app.WebService.AccessToken;
import ir.etkastores.app.WebService.ApiProvider;
import ir.etkastores.app.WebService.ApiStatics;
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

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

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
        String userEmail = UserSettings.getEmailAddress();
        String password = UserSettings.getPasswrod();
        if (!TextUtils.isEmpty(userEmail)){
            emailAddressInput.setText(userEmail);
        }

        if (!TextUtils.isEmpty(password)){
            passwordInput.setText(password);
        }
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.enter);
        toolbar.setActionListeners(this);
    }

    @OnClick(R.id.clubCardCheckBoxHolder)
    public void onClubCardCheckBoxHolderClick() {
        clubCardCheckBox.performClick();
    }

    @OnClick(R.id.registerButton)
    public void onRegisterClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.introFragmentsHolder,new RegisterFragment(),RegisterFragment.TAG,true);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        String userName, password;

        if (loginType == LOGIN_TYPE_CLUBE_CARD){
            userName = clubCardNumberInput.getText().toString();
            password = clubCardPasswordInput.getText().toString();
        }else{
            userName = emailAddressInput.getText().toString();
            password = passwordInput.getText().toString();
        }

        if (TextUtils.isEmpty(userName)){
            Toaster.show(getActivity(),"userName is empty");
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toaster.show(getActivity(),"password is empty");
            return;
        }

        login(userName,password);
    }

    @OnClick(R.id.forgotPasswordButton)
    public void onForgotPasswordClick(){
        new ResetPasswordDialog().show(getActivity().getSupportFragmentManager(),ResetPasswordDialog.TAG);
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
        passwordInput.setText("#abcE1234#");
        emailAddressInput.setText("sajadgarshasbi@gmail.com");
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
    private void login(String userName,String password){
        loadingDialog = DialogHelper.showLoading(getActivity(),R.string.inLogin);
        loginRequest = ApiProvider.getLogin(userName,password);
        loginRequest.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()){
                    loadingDialog.cancel();
                    ApiStatics.saveToken(response.body());
                    goToApp();
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

    private void goToApp(){
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onPause() {
        cancelRequest();
        super.onPause();
    }

    private void cancelRequest(){
        if (loginRequest != null) loginRequest.cancel();
    }

    void showRetryDialog(){
        final MessageDialog messageDialog = MessageDialog.loginError();
        messageDialog.show(getChildFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    onLoginClick();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {
                messageDialog.getDialog().cancel();
            }
        });
    }

}
