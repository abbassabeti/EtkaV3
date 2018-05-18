package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.ui.dialogs.HekmatCardLoginDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.FontUtils;

public class HekmatActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, HekmatActivity.class));
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

    @BindView(R.id.remainedLoanValue)
    TextView remainedLoanValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Activity");
        userProfileName.setTypeface(FontUtils.getBoldTypeFace());
        userProfileName.setText("سجاد گرشاسبی");
        userCode.setText("12301293109");
        remainedCreditValue.setText("۶۵۰ هزار تومان");
        remainedEtkaBonCreditValue.setText("۶۵۰ هزار تومان");
        offerValue.setText("۶۵۰ هزار تومان");
        remainedLoanValue.setText("۶۵۰ هزار تومان");
        HekmatCardLoginDialog.newInstance().show(getSupportFragmentManager(), new HekmatCardLoginDialog.OnHekmatCardCallbackListener() {

            @Override
            public void onHekmatCardLoginDialogSubmitButton(String cardNumber, String password) {

            }
        });
    }

    private void initViews() {
        toolbar.setActionListeners(this);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
