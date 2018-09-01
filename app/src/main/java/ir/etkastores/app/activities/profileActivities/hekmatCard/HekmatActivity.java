package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.HekmatCardLoginModel;
import ir.etkastores.app.models.hekmat.card.HekmatRemainingsModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.dialogs.SelectProvinceDialog;
import ir.etkastores.app.ui.views.CustomRowMenuItem;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HekmatActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String CARD_NUMBER = "CARD_NUMBER";
    private final static String PASSWORD = "PASSWORD";

    public static void show(Activity activity, String cardNumber, String password) {
        Intent intent = new Intent(activity, HekmatActivity.class);
        intent.putExtra(CARD_NUMBER, cardNumber);
        intent.putExtra(PASSWORD, password);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.userCode)
    TextView userCode;

    @BindView(R.id.remainedCreditValue)
    TextView remainedCreditValue;

    @BindView(R.id.remainedEtkaBonCreditValue)
    TextView remainedEtkaBonCreditValue;

    @BindView(R.id.saghfeEtebar)
    TextView saghfeEtebar;

    @BindView(R.id.kalabargHayeElamShodeButton)
    CustomRowMenuItem kalabargHayeElamShodeButton;

    @BindView(R.id.militaryRemainedValue)
    TextView militaryRemainedValue;

    @BindView(R.id.pensionaryRemainedValue)
    TextView pensionaryRemainedValue;

    private String cardNumber;
    private String password;
    private AlertDialog loadingDialog;

    private Call<OauthResponse<HekmatRemainingsModel>> loginReq;

    private HekmatRemainingsModel responseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_hekmat);
        ButterKnife.bind(this);
        cardNumber = getIntent().getStringExtra(CARD_NUMBER);
        password = getIntent().getStringExtra(PASSWORD);
        responseModel = HekmatRemainingsModel.fromJSon(getIntent().getExtras().getString("MODEL", ""));
        initViews();
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenHekmatCard);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("MODEL", new Gson().toJson(responseModel));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        if (responseModel != null) {
            fillViews();
            if (loadingDialog != null) loadingDialog.cancel();
        } else {
            login();
        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void login() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inCheckingHekmatData);
        loginReq = ApiProvider.getAuthorizedApi().hekmatLogin(new HekmatCardLoginModel(cardNumber, password));
        loginReq.enqueue(new Callback<OauthResponse<HekmatRemainingsModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<HekmatRemainingsModel>> call, Response<OauthResponse<HekmatRemainingsModel>> response) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                if (response.isSuccessful()) {
                    responseModel = response.body().getData();
                    if (response.body().isSuccessful()) {
                        fillViews();
                    } else {
                        showRetry(response.body().getMeta().getMessage(), false);
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<HekmatRemainingsModel>> call, Throwable t) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                showRetry(getResources().getString(R.string.errorInLogginHekmatCard), true);
            }
        });
    }

    private void fillViews() {
        userCode.setText(cardNumber);
        remainedCreditValue.setText(responseModel.getRemainCredit());
        remainedEtkaBonCreditValue.setText(responseModel.getRemainDebit());
        saghfeEtebar.setText(responseModel.getMaxCredit());
        militaryRemainedValue.setText(responseModel.getRemainDiscount1());
        pensionaryRemainedValue.setText(responseModel.getRemainDiscount2());
    }

    private void showRetry(String message, boolean hasRetry) {
        String rightButton = getResources().getString(R.string.retry);
        if (!hasRetry) rightButton = null;
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                getResources().getString(R.string.error),
                message,
                rightButton,
                getResources().getString(R.string.exit)
        );
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    login();
                } else {
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @OnClick(R.id.kalabargHayeElamShodeButton)
    public void kalabargHayeElamShodeButtonClick() {
        final SelectProvinceDialog dialog = new SelectProvinceDialog();
        dialog.show(getSupportFragmentManager(), new SelectProvinceDialog.OnButtonClickListener() {
            @Override
            public void onSelectButton(int id, String name) {
                if (isFinishing()) return;
                HekmatCardCouponsProductsActivity.show(HekmatActivity.this, id, name);
                dialog.getDialog().cancel();
            }
        });
    }

    @OnClick(R.id.hekmatTransactionsButton)
    public void onHekmatTransactionClick() {
        HekmatTransactionsActivity.show(this);
    }

    @OnClick(R.id.changePasswordButton)
    public void changePasswordClick() {
        HekmatCardChangePasswordActivity.show(this);
    }

}
