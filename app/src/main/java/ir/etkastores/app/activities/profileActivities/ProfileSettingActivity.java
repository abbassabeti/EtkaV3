package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.CustomRowMenuItem;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.EtkaPushNotificationConfig;
import ir.etkastores.app.utils.procalendar.XCalendar;

@Obfuscate
public class ProfileSettingActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Activity activity) {
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
        if (ProfileManager.getInstance().isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Profile Setting Activity");
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        UserProfileModel profile = ProfileManager.getInstance().getProfile();
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

        gender.setLeftText(profile.getGenderValue());

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
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenEditProfile);
        EditProfileActivity.show(this);
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutButtonClick() {
        showSureToLogoutDialog();
    }

    @OnClick({R.id.specialOfferNotificationButton})
    public void onSpecialOfferNotificationButtonClick() {
        specialOfferSwitch.performClick();
    }

    @OnClick(R.id.hekmatNotificationButton)
    public void onHekmatNotificationButtonClick() {
        hekmatSwitch.performClick();
    }

    SwitchCompat.OnCheckedChangeListener hekmatChangeListener = new SwitchCompat.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (EtkaPushNotificationConfig.isHekmatSubscribed()) {
                AdjustHelper.sendAdjustEvent(AdjustHelper.DisableHekmatNotifications);
                EtkaPushNotificationConfig.unSubscribeHekmat();
            } else {
                AdjustHelper.sendAdjustEvent(AdjustHelper.EnableHekmatNotifications);
                EtkaPushNotificationConfig.subscribeHekmat();
            }
        }

    };

    private void showSureToLogoutDialog() {
        final MessageDialog messageDialog = MessageDialog.sureToLogout();
        messageDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    AdjustHelper.sendAdjustEvent(AdjustHelper.Logout);
                    ProfileManager.getInstance().logOut();
                    Toaster.show(ProfileSettingActivity.this, R.string.logOutSuccessFully);
                    onBackPressed();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
