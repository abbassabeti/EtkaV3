package ir.etkastores.app.Fragments.IntroFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

/**
 * Created by Sajad on 9/29/17.
 */

public class LoginFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener,
        AppCompatCheckBox.OnCheckedChangeListener {

    public static final String TAG = "LOGIN_FRAGMENT_TAG";

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.clubCardCheckBox)
    AppCompatCheckBox clubCardCheckBox;

    @BindView(R.id.emailAddressInputHolder)
    View emailAddressInputHolder;
    @BindView(R.id.passwordInputHolder)
    View passwordInputHolder;
    @BindView(R.id.clubCardNumberInputHolder)
    View clubCardNumberInputHolder;
    @BindView(R.id.clubCardPasswordInputHolder)
    View clubCardPasswordInputHolder;

    @BindView(R.id.emailAddressInput)
    AppCompatEditText emailAddressInput;
    @BindView(R.id.passwordInput)
    AppCompatEditText passwordInput;
    @BindView(R.id.clubCardNumberInput)
    AppCompatEditText clubCardNumberInput;
    @BindView(R.id.clubCardPasswordInput)
    AppCompatEditText clubCardPasswordInput;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clubCardCheckBox.setOnCheckedChangeListener(this);

        showManualLoginControl();

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.enter);
        toolbar.setActionListeners(this);
    }

    @OnClick(R.id.clubCardCheckBoxHolder)
    public void onClubCardCheckBoxHolderClick() {
        clubCardCheckBox.performClick();
    }

    @Override
    public void onToolbarBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean hasCard) {
        if (hasCard)
            showClubCardLoginControl();
        else
            showManualLoginControl();
    }

    private void showManualLoginControl() {
        emailAddressInputHolder.setVisibility(View.VISIBLE);
        passwordInputHolder.setVisibility(View.VISIBLE);
        clubCardNumberInputHolder.setVisibility(View.GONE);
        clubCardPasswordInputHolder.setVisibility(View.GONE);
    }

    private void showClubCardLoginControl() {
        emailAddressInputHolder.setVisibility(View.GONE);
        passwordInputHolder.setVisibility(View.GONE);
        clubCardNumberInputHolder.setVisibility(View.VISIBLE);
        clubCardPasswordInputHolder.setVisibility(View.VISIBLE);
    }

}
