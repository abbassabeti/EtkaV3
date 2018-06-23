package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.HekmatCardLoginModel;
import ir.etkastores.app.models.hekmat.card.HekmatRemainingsModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.utils.FontUtils;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HekmatActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String CARD_NUMBER = "CARD_NUMBER";
    private final static String PASSWORD = "PASSWORD";

    public static void show(Activity activity, String cardNumber, String password) {
        Intent intent = new Intent(activity,HekmatActivity.class);
        intent.putExtra(CARD_NUMBER,cardNumber);
        intent.putExtra(PASSWORD,password);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.userProfileName)
    TextView userProfileName;

    @BindView(R.id.userCode)
    TextView userCode;

    @BindView(R.id.remainedCreditValue)
    TextView remainedCreditValue;

    @BindView(R.id.remainedEtkaBonCreditValue)
    TextView remainedEtkaBonCreditValue;

    @BindView(R.id.offerValue)
    TextView offerValue;

    @BindView(R.id.remainedDebitValue)
    TextView remainedDebitValue;

    private String cardNumber;
    private String password;
    private AlertDialog loadingDialog;

    private Call<OauthResponse<HekmatRemainingsModel>> loginReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat);
        ButterKnife.bind(this);
        cardNumber = getIntent().getStringExtra(CARD_NUMBER);
        password = getIntent().getStringExtra(PASSWORD);
        initViews();
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenHekmatCard);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Activity");
        userProfileName.setTypeface(FontUtils.getBoldTypeFace());
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        login();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void login(){
        loadingDialog = DialogHelper.showLoading(this,R.string.inCheckingHekmatData);
        loginReq = ApiProvider.getAuthorizedApi().hekmatLogin(new HekmatCardLoginModel(cardNumber, password));
        loginReq.enqueue(new Callback<OauthResponse<HekmatRemainingsModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<HekmatRemainingsModel>> call, Response<OauthResponse<HekmatRemainingsModel>> response) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        userCode.setText(cardNumber);
//                        userProfileName.setText(ProfileManager.getProfile().getFirstNameAndLastName());
                        remainedCreditValue.setText(response.body().getData().getRemainCredit());
                        remainedDebitValue.setText(response.body().getData().getRemainDebit());
                    }else{

                    }
                }else{
                    onFailure(call,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<HekmatRemainingsModel>> call, Throwable t) {
                if (isFinishing()) return;
                loadingDialog.cancel();
            }
        });
    }

}
