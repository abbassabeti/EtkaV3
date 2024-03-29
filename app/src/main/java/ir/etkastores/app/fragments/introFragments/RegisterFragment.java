package ir.etkastores.app.fragments.introFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.profile.RegisterUserRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/29/17.
 */

public class RegisterFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    public static final String TAG = "REGISTER_FRAGMENT_TAG";

    @BindView(R.id.firstNameInput)
    AppCompatEditText firsNameInput;
    @BindView(R.id.lastNameInput)
    AppCompatEditText lastNameInput;
    @BindView(R.id.emailAddressInput)
    AppCompatEditText emailAddressInput;
    @BindView(R.id.passwordInput)
    AppCompatEditText passwordInput;
    @BindView(R.id.presenterCodeInput)
    AppCompatEditText presenterCodeInput;

    View view;

    private AlertDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        passwordInput.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void initToolbar(){

    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.loginRegisterFragmentHolder,new LoginFragment(),LoginFragment.TAG,true);
    }

    @Override
    public void onToolbarBackClick() {
        try {
            getActivity().onBackPressed();
        }catch (Exception err){}
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.registerButton)
    void onRegisterButtonClick(){

        if (TextUtils.isEmpty(firsNameInput.getText().toString())){
            Toaster.show(getActivity(),R.string.firstNameCantBeEmpty);
            return;
        }

        if (TextUtils.isEmpty(lastNameInput.getText().toString())){
            Toaster.show(getActivity(),R.string.lastNameCantBeEmpty);
            return;
        }

        if (TextUtils.isEmpty(passwordInput.getText().toString())){
            Toaster.show(getActivity(),R.string.passwordCantBeEmpty);
            return;
        }

        if (TextUtils.isEmpty(emailAddressInput.getText().toString())){
            Toaster.show(getActivity(),R.string.emailAddresCantBeEmpty);
            return;
        }

        register();

    }

    Call<OauthResponse<String>> registerApi;
    private void register(){
        loadingDialog = DialogHelper.showLoading(getActivity(),R.string.inRegistering);
        final RegisterUserRequestModel requestModel = new RegisterUserRequestModel();
        requestModel.setEmail(emailAddressInput.getText().toString());
        requestModel.setPassword(passwordInput.getText().toString());
        requestModel.setFirstName(firsNameInput.getText().toString());
        requestModel.setLastName(lastNameInput.getText().toString());
        registerApi = ApiProvider.getInstance().getApi().registerNewUser(requestModel);
        registerApi.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (!isAdded()) return;
                loadingDialog.cancel();
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        AdjustHelper.sendAdjustEvent(AdjustHelper.Register);
                        Toaster.showLong(getActivity(),R.string.registerSuccessful);
//                        UserSettings.setEmailAddress(requestModel.getEmail());
//                        UserSettings.setPasswrod(requestModel.getPassword());
                        onToolbarBackClick();
                    }else{
                        showRetryDialog(response.body().getMeta().getMessage());
                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                if (!isAdded()) return;
                loadingDialog.cancel();
                showRetryDialog(EtkaApp.getInstance().getResources().getString(R.string.registerFailTryLater));
            }
        });
    }

    private void showRetryDialog(final String message){
        final MessageDialog messageDialog = MessageDialog.registerError(message);
        messageDialog.show(getChildFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    register();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
