package ir.etkastores.app.Fragments.IntroFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;
import ir.etkastores.app.Utils.DialogHelper;
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
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.registerButton)
    void onRegisterButtonClick(){

        if (firsNameInput.getText().toString().contentEquals("")){

            return;
        }

        if (lastNameInput.getText().toString().contentEquals("")){

            return;
        }

        if (passwordInput.getText().toString().contentEquals("")){

            return;
        }

        if (emailAddressInput.getText().toString().contentEquals("")){

            return;
        }

        register();

    }

    Call<OauthResponse<String>> registerApi;
    private void register(){
        RegisterUserRequestModel requestModel = new RegisterUserRequestModel();
        requestModel.setEmail(emailAddressInput.getText().toString());
        requestModel.setPassword(passwordInput.getText().toString());
        requestModel.setFirstName(firsNameInput.getText().toString());
        requestModel.setLastName(lastNameInput.getText().toString());
        //requestModel.setCellPhone();
        //requestModel.setGender();
        loadingDialog = DialogHelper.showLoading(getActivity(),R.string.inSendingData);
        registerApi = ApiProvider.getApi().registerNewUser(requestModel);
        registerApi.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                loadingDialog.cancel();
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){

                    }else{

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                loadingDialog.cancel();
            }
        });
    }

}
