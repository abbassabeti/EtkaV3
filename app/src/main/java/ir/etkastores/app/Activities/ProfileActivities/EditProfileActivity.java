package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.procalendar.XCalendar;
import ir.etkastores.app.Utils.procalendar.repositories.CalendarRepoInterface;
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

    private ArrayAdapter<String> genderAdapter;
    private ArrayAdapter<String> educationAdapter;
    private ArrayAdapter<String> dayAdapter;
    private ArrayAdapter<String> monthAdapter;
    private ArrayAdapter<String> yearAdapter;
    private CalendarRepoInterface calendar;

    private int selectedGender;
    private String selectedEducation;
    private int selectedDay;
    private int selectedMonth;
    private int selectedYear;

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

        calendar = new XCalendar().getCalendar(XCalendar.JalaliType);
        initGenderSpinnerAdapter();
        initEducationSpinnerAdapter();
        initYearSpinnerAdapter();
        initMonthSpinnerAdapter();
        initDaySpinnerAdapter();
        firstNameInputEt.requestFocus();

        genderSpinner.setSelection(profile.getGender());

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
    }

    private void initEducationSpinnerAdapter() {
        List<String> educationItems = new ArrayList<>();
        educationItems.add("تحصیلات");
        educationItems.add("زیر دیپلم");
        educationItems.add("دیپلم");
        educationItems.add("فوق دیپلم");
        educationItems.add("لیسانس");
        educationItems.add("فوق لیسانس");
        educationItems.add("دکتری و بالاتر");
        educationAdapter = generateArrayAdapter(educationItems);
        educationSpinner.setAdapter(educationAdapter);
    }

    private void initDaySpinnerAdapter() {
        List<String> dayItems = new ArrayList<>();
        dayItems.add("روز");
        for (int x = 1; x <= calendar.daysOfMonth() ; x++){
            dayItems.add(String.valueOf(x));
        }
        dayAdapter = generateArrayAdapter(dayItems);
        daySpinner.setAdapter(dayAdapter);
    }

    private void initMonthSpinnerAdapter() {
        List<String> monthItems = new ArrayList<>();
        monthItems.add("ماه");
        for (String m : calendar.monthNamesOfYear()){
            if (!TextUtils.isEmpty(m)) monthItems.add(m);
        }
        monthAdapter = generateArrayAdapter(monthItems);
        monthSpinner.setAdapter(monthAdapter);
    }

    private void initYearSpinnerAdapter() {
        List<String> yearItems = new ArrayList<>();
        yearItems.add("سال");
        int currentYear = new XCalendar().getCalendar(XCalendar.JalaliType).getYear();
        int minYear = currentYear - 100;
        for (int x = currentYear ; x>=minYear ; x--){
            yearItems.add(String.valueOf(x));
        }
        yearAdapter = generateArrayAdapter(yearItems);
        yearSpinner.setAdapter(yearAdapter);
    }

    AppCompatSpinner.OnItemSelectedListener daySelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener  monthSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AppCompatSpinner.OnItemSelectedListener yearSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
