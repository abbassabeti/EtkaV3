package ir.etkastores.app.Activities.ProfileActivities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.profile.ChangePasswordRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.Utils.DiskDataHelper;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(BaseActivity activity) {
        activity.startActivity(new Intent(activity, ChangePasswordActivity.class));
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.currentPasswordInput)
    AppCompatEditText currentPasswordInput;

    @BindView(R.id.newPasswordInput)
    AppCompatEditText newPasswordInput;

    @BindView(R.id.confirmPasswordInput)
    AppCompatEditText confirmPasswordInput;

    private ChangePasswordRequestModel requestModel;
    private Call<OauthResponse<String>> req;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
        newPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
        confirmPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.changePasswordButton)
    public void onChangePasswordButtonClick(){

        if (TextUtils.isEmpty(currentPasswordInput.getText().toString())){
            Toaster.show(this,R.string.fillCurrentPassword);
            return;
        }

        if (TextUtils.isEmpty(newPasswordInput.getText().toString())){
            Toaster.show(this,R.string.fillNewPassword);
            return;
        }

        if (TextUtils.isEmpty(confirmPasswordInput.getText().toString())){
            Toaster.show(this,R.string.fillConfirmPassword);
            return;
        }

        if (!newPasswordInput.getText().toString().contentEquals(confirmPasswordInput.getText().toString())){
            Toaster.show(this,R.string.confirmPasswordIdNotValid);
            return;
        }

        requestModel = new ChangePasswordRequestModel();
        requestModel.setNewPassword(newPasswordInput.getText().toString());
        requestModel.setConfirmPassword(confirmPasswordInput.getText().toString());
        requestModel.setNewPassword(newPasswordInput.getText().toString());
        requestModel.setUserId(DiskDataHelper.getLastToken().getUserId());
        sendRequest();

    }

    void sendRequest(){
        loadingDialog = DialogHelper.showLoading(this,R.string.inSendingChangePassword);
        req = ApiProvider.getAuthorizedApi().changePassword(requestModel);
        req.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (req == null || req.isCanceled()) return;
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){

                    }else{

                    }
                }else{
                    onFailure(call,null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable throwable) {
                if (req == null || req.isCanceled()) return;
                loadingDialog.cancel();
                showRetryDialog(getResources().getString(R.string.errorInChangePassword));
            }
        });
    }

    private void showRetryDialog(String message){
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error),message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    messageDialog.getDialog().cancel();
                    sendRequest();
                }else{
                    messageDialog.getDialog().cancel();
                }
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }


}
