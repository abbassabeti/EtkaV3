package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Fragments.IntroFragments.LoginFragment;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.CustomRowMenuItem;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.EtkaPushNotificationConfig;
import ir.etkastores.app.Utils.procalendar.XCalendar;
import ir.etkastores.app.data.ProfileManager;

public class ProfileSettingActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ProfileSettingActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.specialOfferNotificationSwitch)
    SwitchCompat specialOfferSwitch;

    @BindView(R.id.hekmatNotificationSwitch)
    SwitchCompat hekmatSwitch;

    @BindView(R.id.firstNameAndLastName)
    CustomRowMenuItem firstNameAndLastName;

    @BindView(R.id.nationalCode)
    CustomRowMenuItem nationalCode;

    @BindView(R.id.email)
    CustomRowMenuItem email;

    @BindView(R.id.mobilePhone)
    CustomRowMenuItem mobilePhone;

    @BindView(R.id.gender)
    CustomRowMenuItem gender;

    @BindView(R.id.education)
    CustomRowMenuItem education;

    @BindView(R.id.birthDate)
    CustomRowMenuItem birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        UserProfileModel profile = ProfileManager.getProfile();
        if (profile == null) return;
        if (TextUtils.isEmpty(profile.getFirstNameAndLastName())) {
            firstNameAndLastName.setLeftText("-");
        } else {
            firstNameAndLastName.setLeftText(profile.getFirstNameAndLastName());
        }

        if (profile.getNationalCode() == null || TextUtils.isEmpty(profile.getNationalCode().trim())) {
            nationalCode.setLeftText("-");
        } else {
            nationalCode.setLeftText(profile.getNationalCode());
        }

        if (TextUtils.isEmpty(profile.getEmail())) {
            email.setLeftText("-");
        } else {
            email.setLeftText(profile.getEmail());
        }

        if (TextUtils.isEmpty(profile.getCellPhone())) {
            mobilePhone.setLeftText("-");
        } else {
            mobilePhone.setLeftText(profile.getCellPhone());
        }

        gender.setLeftText(profile.getGenderValue() + "");

        if (TextUtils.isEmpty(profile.getEducation())) {
            education.setLeftText("-");
        } else {
            education.setLeftText(profile.translateEducation(profile.getEducation()));
        }

        if (profile.getBirthDateXCalendar() == null) {
            birthDate.setLeftText("-");
        } else {
            birthDate.setLeftText(profile.getBirthDateXCalendar().getCalendar(XCalendar.JalaliType).getDateByMonthName());
        }

        hekmatSwitch.setChecked(EtkaPushNotificationConfig.isHekmatSubscribed());
        hekmatSwitch.setOnCheckedChangeListener(hekmatChangeListener);

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.editButton)
    public void onEditClick() {
        EditProfileActivity.start(this);
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutButtonClick() {

    }

    @OnClick(R.id.changePasswordButton)
    public void onChangePasswordButtonClick() {
        ChangePasswordActivity.show(this);
    }

    @OnClick({R.id.specialOfferNotificationButton})
    public void onSpecialOfferNotificationButtonClick() {
        specialOfferSwitch.performClick();
    }

    @OnClick(R.id.hekmatNotificationButton)
    public void onHekmatNotificationButtonClick() {
        hekmatSwitch.performClick();
    }

    @OnClick({R.id.editButton, R.id.changePasswordButton, R.id.logoutButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editButton:
                break;
            case R.id.changePasswordButton:
                break;
            case R.id.logoutButton:
                break;
        }
    }

    SwitchCompat.OnCheckedChangeListener hekmatChangeListener = new SwitchCompat.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (EtkaPushNotificationConfig.isHekmatSubscribed()){
                EtkaPushNotificationConfig.unregisterHekmat();
            }else{
                EtkaPushNotificationConfig.registerHekmat();
            }
        }

    };

}
