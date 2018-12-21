package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.IntentHelper;
import ir.etkastores.app.utils.StringUtils;

@Obfuscate
public class InviteFriendsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, InviteFriendsActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.userInvitationCode)
    TextView invitationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.getInstance().isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        invitationCode.setText(StringUtils.toEnglishDigit(ProfileManager.getInstance().getProfile().getInvitationCode()));
        invitationCode.setTypeface(Typeface.DEFAULT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Invite Friends Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.shareButton)
    public void onShareButtonClick() {
        String message = String.format(getResources().getString(R.string.shareInvitationCodeMessage), ProfileManager.getInstance().getProfile().getInvitationCode()) +
                "\n" + getResources().getString(R.string.cafebazaarAppUrl);
        IntentHelper.share(this, getResources().getString(R.string.etkaStoreAppInvitation), message);
    }

}
