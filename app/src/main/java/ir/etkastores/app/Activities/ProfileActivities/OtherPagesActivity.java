package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

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
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.termAndConditionsButton)
    void onTermAndConditionsButtonClick(){
        TextInfoActivity.startTermAndConditions(this);
    }

    @OnClick(R.id.userPrivacyButton)
    void onUserPrivacyButtonClick(){
        TextInfoActivity.startUserPrivacy(this);
    }

    @OnClick(R.id.aboutEtkaStoresButton)
    void onAboutEtkaStoresbuttonClick(){
        TextInfoActivity.startAboutEtkaStores(this);
    }


}
