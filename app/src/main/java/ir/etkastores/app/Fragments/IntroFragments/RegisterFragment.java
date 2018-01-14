package ir.etkastores.app.Fragments.IntroFragments;

import android.app.AlertDialog;
import android.content.Context;
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
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.Utils.UserSettings;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/29/17.
 */

public class RegisterFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    public static final String TAG = "REGISTER_FRAGMENT_TAG";

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;
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
        toolbar.setTitle(R.string.register);
        toolbar.setActionListeners(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        ActivityUtils.replaceFragment(getActivity(),R.id.introFragmentsHolder,new LoginFragment(),LoginFragment.TAG,true);
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
        registerApi = ApiProvider.getApi().registerNewUser(requestModel);
        registerApi.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                loadingDialog.cancel();
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        Toaster.showLong(getActivity(),R.string.registerSuccessful);
                        UserSettings.setEmailAddress(requestModel.getEmail());
                        UserSettings.setPasswrod(requestModel.getPassword());
                        onToolbarBackClick();
                    }else{
                        if (TextUtils.isEmpty(response.body().getMeta().getMessage())){
                            Toaster.showLong(getActivity(),response.body().getMeta().getErrorsMessage());
                        }else{
                            Toaster.showLong(getActivity(),response.body().getMeta().getMessage());
                        }
                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                loadingDialog.cancel();
                Toaster.show(getActivity(),R.string.registerFailTryLater);
            }
        });
    }

}
