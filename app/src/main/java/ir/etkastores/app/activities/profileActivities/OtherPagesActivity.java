package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;

@Obfuscate
public class OtherPagesActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,OtherPagesActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_pages);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Other Pages Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.termAndConditionsButton)
    void onTermAndConditionsButtonClick(){
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenRules);
        TextInfoActivity.showTermAndConditions(this);
    }

    @OnClick(R.id.userPrivacyButton)
    void onUserPrivacyButtonClick(){
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenUserPrivacy);
        TextInfoActivity.showUserPrivacy(this);
    }

    @OnClick(R.id.aboutEtkaStoresButton)
    void onAboutEtkaStoresbuttonClick(){
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenAboutEtka);
        TextInfoActivity.showAboutEtkaStores(this);
    }


}
