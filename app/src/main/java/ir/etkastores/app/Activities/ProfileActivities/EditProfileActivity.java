package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.Utils.DiskDataHelper;
import ir.etkastores.app.Utils.procalendar.XCalendar;
import ir.etkastores.app.Utils.procalendar.repositories.CalendarRepoInterface;
import ir.etkastores.app.WebService.ApiProvider;
import ir.etkastores.app.WebService.ApiStatics;
import ir.etkastores.app.data.ProfileManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> educationAdapter;
    private ArrayAdapter<String> dayAdapter;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> yearAdapter;
    private CalendarRepoInterface calendar;

    private final int NOT_SET = -1;

    private int selectedGender;
    private String selectedEducation;

    private int selectedDay = NOT_SET;
    private int selectedMonth = NOT_SET;
    private int selectedYear = NOT_SET;

    private AlertDialog loadingDialog;
    private Call<OauthResponse<String>> updateReq;
    private UserProfileModel newProfileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        UserProfileModel profile = ProfileManager.getProfile();
        if (!TextUtils.isEmpty(profile.getFirstName()))
            firstNameInputEt.setText(profile.getFirstName());
        if (!TextUtils.isEmpty(profile.getLastName())) lastNameEt.setText(profile.getLastName());
        if (!TextUtils.isEmpty(profile.getNationalCode()))
            nationalCodeEt.setText(profile.getNationalCode().trim());
        if (!TextUtils.isEmpty(profile.getEmail())) emailEt.setText(profile.getEmail());
        if (!TextUtils.isEmpty(profile.getCellPhone()))
            mobilePhoneEt.setText(profile.getCellPhone());

        XCalendar birthDayXCalendar = ProfileManager.getProfile().getBirthDateXCalendar();
        if (birthDayXCalendar != null) {
            calendar = birthDayXCalendar.getCalendar(XCalendar.JalaliType);
            selectedDay = calendar.getDay();
            selectedMonth = calendar.getMonth();
            selectedYear = calendar.getYear();
        } else {
            calendar = new XCalendar().getCalendar(XCalendar.JalaliType);
        }

        initGenderSpinnerAdapter();
        initEducationSpinnerAdapter();
        initYearSpinner();
        initMonthSpinner();
        initDaySpinner();
        firstNameInputEt.requestFocus();
        genderSpinner.setSelection(profile.getGender());
        selectedEducation = profile.getEducation();

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private ArrayAdapter<String> generateArrayAdapter(List<String> items) {
        return new ArrayAdapter<String>(this,
                R.layout.text_view_withe_text_for_spinner,
                items);
    }

    private void initGenderSpinnerAdapter() {
        List<String> genderItems = new ArrayList<>();
        genderItems.add(getResources().getString(R.string.male));
        genderItems.add(getResources().getString(R.string.female));
        genderAdapter = generateArrayAdapter(genderItems);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(genderSelectListener);
    }

    private void initEducationSpinnerAdapter() {
        List<String> educationItems = new ArrayList<>();
        educationItems.add("تحصیلات");
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.Illiterate));
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.Diploma));
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.AssociateDegree));
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.Bachelor));
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.Master));
        educationItems.add(UserProfileModel.translateEducation(UserProfileModel.EducationItems.PHD));
        educationAdapter = generateArrayAdapter(educationItems);
        educationSpinner.setAdapter(educationAdapter);
        if (!TextUtils.isEmpty(selectedEducation)){
            switch (selectedEducation){

                case UserProfileModel.EducationItems.Illiterate:
                    educationSpinner.setSelection(1);
                    break;

                case UserProfileModel.EducationItems.Diploma:
                    educationSpinner.setSelection(2);
                    break;

                case UserProfileModel.EducationItems.AssociateDegree:
                    educationSpinner.setSelection(3);
                    break;

                case UserProfileModel.EducationItems.Bachelor:
                    educationSpinner.setSelection(4);
                    break;

                case UserProfileModel.EducationItems.Master:
                    educationSpinner.setSelection(5);
                    break;

                case UserProfileModel.EducationItems.PHD:
                    educationSpinner.setSelection(6);
                    break;
            }
        }
        educationSpinner.setOnItemSelectedListener(educationSelectListener);
    }

    private void initDaySpinner() {
        List<String> dayItems = new ArrayList<>();
        dayItems.add("روز");

        if (selectedMonth != NOT_SET && selectedYear != NOT_SET) {
            calendar = XCalendar.fromJalali(selectedYear, selectedMonth, 1).getCalendar(XCalendar.JalaliType);
        }

        for (int x = 1; x <= calendar.daysOfMonth(); x++) {
            dayItems.add(String.valueOf(x));
        }
        dayAdapter = generateArrayAdapter(dayItems);
        daySpinner.setAdapter(dayAdapter);
        if (selectedDay != NOT_SET) {
            String d = String.valueOf(selectedDay);
            for (String s : dayItems) {
                if (s.contentEquals(d.trim())) {
                    daySpinner.setSelection(dayItems.indexOf(s));
                }
            }
        }
        daySpinner.setOnItemSelectedListener(daySelectListener);
    }

    private void initMonthSpinner() {
        List<String> monthItems = new ArrayList<>();
        monthItems.add("ماه");
        for (String m : calendar.monthNamesOfYear()) {
            if (!TextUtils.isEmpty(m)) monthItems.add(m);
        }
        monthAdapter = generateArrayAdapter(monthItems);
        monthSpinner.setAdapter(monthAdapter);
        if (selectedMonth != NOT_SET) {
            monthSpinner.setSelection(selectedMonth + 1);
        }
        monthSpinner.setOnItemSelectedListener(monthSelectListener);
    }

    private void initYearSpinner() {
        List<String> yearItems = new ArrayList<>();
        yearItems.add("سال");
        int currentYear = new XCalendar().getCalendar(XCalendar.JalaliType).getYear();
        int minYear = currentYear - 100;
        for (int x = currentYear; x >= minYear; x--) {
            yearItems.add(String.valueOf(x));
        }
        yearAdapter = generateArrayAdapter(yearItems);
        yearSpinner.setAdapter(yearAdapter);
        if (selectedYear != NOT_SET) {
            String y = String.valueOf(selectedYear);
            for (String s : yearItems) {
                if (s.contains(y)) {
                    yearSpinner.setSelection(yearItems.indexOf(s));
                }
            }
        }
        yearSpinner.setOnItemSelectedListener(yearSelectListener);
    }

    AppCompatSpinner.OnItemSelectedListener daySelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                selectedDay = Integer.parseInt(dayAdapter.getItem(position));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener monthSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                selectedMonth = position - 1;
                initDaySpinner();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener yearSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                selectedYear = Integer.parseInt(yearAdapter.getItem(position));
                initMonthSpinner();
                initDaySpinner();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener genderSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedGender = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener educationSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 1:
                    selectedEducation = UserProfileModel.EducationItems.Illiterate;
                    break;

                case 2:
                    selectedEducation = UserProfileModel.EducationItems.Diploma;
                    break;

                case 3:
                    selectedEducation = UserProfileModel.EducationItems.AssociateDegree;
                    break;

                case 4:
                    selectedEducation = UserProfileModel.EducationItems.Bachelor;
                    break;

                case 5:
                    selectedEducation = UserProfileModel.EducationItems.Master;
                    break;

                case 6:
                    selectedEducation = UserProfileModel.EducationItems.PHD;
                    break;

                default:
                    selectedEducation = null;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @OnClick(R.id.saveChangesButton)
    public void onSaveChangesClick() {
        UserProfileModel profileModel = new UserProfileModel();
        profileModel.setFirstName(firstNameInputEt.getText().toString());
        profileModel.setLastName(lastNameEt.getText().toString());
        profileModel.setNationalCode(nationalCodeEt.getText().toString());
        profileModel.setEmail(emailEt.getText().toString());
        profileModel.setCellPhone(mobilePhoneEt.getText().toString());
        profileModel.setGender(selectedGender);
        profileModel.setEducation(selectedEducation);
        String selectedBirthDate = null;
        if (selectedYear != NOT_SET && selectedMonth != NOT_SET && selectedDay != NOT_SET){
            XCalendar selectedDateCalendar = XCalendar.fromJalali(selectedYear,selectedMonth,selectedDay);
            selectedBirthDate = selectedDateCalendar.getCalendar(XCalendar.GregorianType).getYYYYMMDD("-");
        }
        profileModel.setBirthDate(selectedBirthDate);
        newProfileInfo = profileModel;
        newProfileInfo.setId(DiskDataHelper.getLastToken().getUserId());
        sendUpdateRequest();
    }

    private void sendUpdateRequest(){
        loadingDialog = DialogHelper.showLoading(this,R.string.inUpdatingUserProfile);
        updateReq = ApiProvider.getAuthorizedApi().editUserProfile(newProfileInfo);
        updateReq.enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (loadingDialog == null) return;
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        ProfileManager.saveProfile(newProfileInfo);
                        Toaster.show(EditProfileActivity.this,R.string.profileUpdatedSuccessfully);
                        onBackPressed();
                    }else{
                        showUpdateProfileError(response.body().getMeta().getMessage());
                    }
                }else{
                    onFailure(call,null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable throwable) {
                if (call.isCanceled()) return;
                if (loadingDialog == null) return;
                loadingDialog.cancel();
                showUpdateProfileError(getResources().getString(R.string.errorInUpdatingProfile));
            }
        });
    }

    private void showUpdateProfileError(String message){
        final MessageDialog retryDialog = MessageDialog.warningRetry(getResources().getString(R.string.error),message);
        retryDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                retryDialog.getDialog().cancel();
                if (button == RIGHT_BUTTON){
                    sendUpdateRequest();
                }
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });

    }

}
