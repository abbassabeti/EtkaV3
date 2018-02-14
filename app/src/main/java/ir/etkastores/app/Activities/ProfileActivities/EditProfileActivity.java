package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.data.ProfileManager;

public class EditProfileActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, EditProfileActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.genderSpinner)
    AppCompatSpinner genderSpinner;

    @BindView(R.id.educationSpinner)
    AppCompatSpinner educationSpinner;

    @BindView(R.id.daySpinner)
    AppCompatSpinner daySpinner;

    @BindView(R.id.monthSpinner)
    AppCompatSpinner monthSpinner;

    @BindView(R.id.yearSpinner)
    AppCompatSpinner yearSpinner;

    @BindView(R.id.lastNameInput)
    AppCompatEditText lastNameEt;

    @BindView(R.id.firstNameInputInput)
    AppCompatEditText firstNameInputEt;

    @BindView(R.id.nationalCodeInput)
    AppCompatEditText nationalCodeEt;

    @BindView(R.id.emailInput)
    AppCompatEditText emailEt;

    @BindView(R.id.mobilePhoneInput)
    AppCompatEditText mobilePhoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        fillSpinners();
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        UserProfileModel profile = ProfileManager.getProfile();
        if (!TextUtils.isEmpty(profile.getFirstName())) firstNameInputEt.setText(profile.getFirstName());
        if (!TextUtils.isEmpty(profile.getLastName())) lastNameEt.setText(profile.getLastName());
        if (!TextUtils.isEmpty(profile.getNationalCode())) nationalCodeEt.setText(profile.getNationalCode().trim());
        if (!TextUtils.isEmpty(profile.getEmail())) emailEt.setText(profile.getEmail());
        if (!TextUtils.isEmpty(profile.getCellPhone())) mobilePhoneEt.setText(profile.getCellPhone());
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void fillSpinners() {
        List<String> items = new ArrayList<>();
        items.add("یک");
        items.add("دو");
        items.add("سه");
        items.add("چهار");
        items.add("پنج");
        items.add("شش");
        items.add("هفت");
        items.add("هشت");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.text_view_withe_text_for_spinner,
                items);

        genderSpinner.setAdapter(adapter);
        educationSpinner.setAdapter(adapter);
        daySpinner.setAdapter(adapter);
        monthSpinner.setAdapter(adapter);
        yearSpinner.setAdapter(adapter);
    }
}
