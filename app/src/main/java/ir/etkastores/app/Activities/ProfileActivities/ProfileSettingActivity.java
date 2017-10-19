package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class ProfileSettingActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,ProfileSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.specialOfferNotificationSwitch)
    SwitchCompat specialOfferSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
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

    @OnClick(R.id.editButton)
    public void onEditClick(){
        EditProfileActivity.start(this);
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutButtonClick(){

    }

    @OnClick(R.id.changePasswordButton)
    public void onChangePasswordButtonClick(){

    }

    @OnClick(R.id.specialOfferNotificationButton)
    public void onSpecialOfferNotificationButtonClick(){
        specialOfferSwitch.performClick();
    }



}
