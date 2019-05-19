package ir.etkastores.app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.LoginWithSMSActivity;
import ir.etkastores.app.activities.profileActivities.FAQActivity;
import ir.etkastores.app.activities.profileActivities.InviteFriendsActivity;
import ir.etkastores.app.activities.profileActivities.NextShoppingListActivity;
import ir.etkastores.app.activities.profileActivities.OtherPagesActivity;
import ir.etkastores.app.activities.profileActivities.ProfileSettingActivity;
import ir.etkastores.app.activities.profileActivities.ShoppingHistoryActivity;
import ir.etkastores.app.activities.profileActivities.SupportActivity;
import ir.etkastores.app.activities.profileActivities.hekmatCard.HekmatActivity;
import ir.etkastores.app.activities.profileActivities.hekmatCard.HekmatCardRegisterActivity;
import ir.etkastores.app.activities.profileActivities.survey.SurveyListActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.profile.UserProfileModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.HekmatCardLoginDialog;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.CustomRowMenuItem;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DiskDataHelper;
import ir.etkastores.app.webServices.ApiProvider;
import ir.etkastores.app.webServices.ApiStatics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/1/17.
 */

@Obfuscate
public class ProfileFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.userName)
    TextView userName;

    @BindView(R.id.scoreMenu)
    CustomRowMenuItem scoreMenu;

    @BindView(R.id.nextShoppingListMenu)
    CustomRowMenuItem nextShoppingListMenu;

    @BindView(R.id.userBarcodeId)
    AppCompatImageView userBarcodeIdImage;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.barcodeHolder)
    View barcodeHolder;

    @BindView(R.id.appVersionTv)
    TextView appVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_profile, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        return view;
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        appVersion.setText(getResources().getString(R.string.applicationVersionComma) + " " + BuildConfig.VERSION_NAME);
        loadProfile();
    }

    @Override
    public void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Profile Fragment");
        if (ProfileManager.getInstance().isGuest()) {
            initGuestUser();
        } else {
            initLoginUser();
        }
    }

    private void initLoginUser() {
        UserProfileModel profile = ProfileManager.getInstance().getProfile();
        userName.setText(profile.getFirstName() + " " + profile.getLastName());
        scoreMenu.setText(String.format(getResources().getString(R.string.youHaveXScore), profile.getTotalPoints()));
//        barcodeHolder.setVisibility(View.VISIBLE);
        userName.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (!isAdded()) return;
//                if (!ProfileManager.getInstance().isGuest()) {
//                    BarcodeUtils.generateBarcodeBitmap(ProfileManager.getInstance().getProfile().getBarCode(), BarcodeFormat.CODABAR, userBarcodeIdImage);
//                }
//            }
//        }, 500);
    }

    private void initGuestUser() {
        userName.setText(R.string.guestUser);
        scoreMenu.setText(getResources().getString(R.string.yourScore));
//        barcodeHolder.setVisibility(View.GONE);
        userName.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.hekmatMenu)
    public void onHekmatMenuClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            final HekmatCardLoginDialog dialog = HekmatCardLoginDialog.newInstance();
            dialog.show(getChildFragmentManager(), new HekmatCardLoginDialog.OnHekmatCardCallbackListener() {
                @Override
                public void onHekmatCardLoginDialogSubmitButton(String cardNumber, String password) {
                    DiskDataHelper.setLastHekmatCardNumber(cardNumber);
                    if (BuildConfig.DEBUG && (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(password))) {
                        HekmatActivity.show(getActivity(), "0892090119536318", "123!@#qweQWE");
                    } else {
                        if (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(password)) {
                            Toaster.show(getActivity(), R.string.pleaseFillCardNumberAndPassword);
                            return;
                        } else {
                            HekmatActivity.show(getActivity(), cardNumber, password);
                        }
                    }
                    dialog.getDialog().cancel();
                }

                @Override
                public void onHekmatRegisterButton(String cardNumber, String password) {
                    HekmatCardRegisterActivity.show(getActivity(), cardNumber, password);
                    dialog.getDialog().cancel();
                }
            });
        }
    }

    @OnClick(R.id.scoreMenu)
    public void onScoreMenuClick() {
//        if (ProfileManager.isGuest()) {
//            showLoginRequiredDialog();
//        } else {
//            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenPoints);
//            ScoresActivity.start(getActivity());
//        }
        Toaster.show(getContext(), R.string.commingSoonMessage);
    }

    @OnClick(R.id.nextShoppingListMenu)
    public void onNextShoppingListMenuClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenNextShoppingList);
            try {
                Intent intent = new Intent(getActivity(), NextShoppingListActivity.class);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.shoppingHistoryMenu)
    public void onShoppingHistoryMenuClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenFactors);
            ShoppingHistoryActivity.show(getActivity());
        }
    }

    @OnClick(R.id.inviteFriendsMenu)
    public void onInviteFriendsMenuClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenInviteFriend);
            InviteFriendsActivity.show(getActivity());
        }
    }

    @OnClick(R.id.faqMenu)
    public void onFAQMenuClick() {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenFAQ);
        FAQActivity.show(getActivity());
    }

    @OnClick(R.id.supportMenu)
    public void onSupportMenuClick() {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenSupport);
        SupportActivity.show(getActivity(), SupportActivity.SUPPORT_TICKET, null);
    }

    @OnClick(R.id.surveyMenu)
    public void onSurveyClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenSurvey);
            SurveyListActivity.show(getActivity());
        }
    }

    @Override
    public void onToolbarBackClick() {
        if (ProfileManager.getInstance().isGuest()) {
            showLoginRequiredDialog();
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onActionClick(int actionCode) {
        switch (actionCode) {

            case MORE_BUTTON:
                AdjustHelper.sendAdjustEvent(AdjustHelper.OpenProfileOtherPages);
                OtherPagesActivity.start(getActivity());
                break;

            case SETTING_BUTTON:
                if (ProfileManager.getInstance().isGuest()) {
                    showLoginRequiredDialog();
                } else {
                    AdjustHelper.sendAdjustEvent(AdjustHelper.OpenProfileSetting);
                    ProfileSettingActivity.show(getActivity());
                }
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void showLoginRequiredDialog() {
        final MessageDialog messageDialog = MessageDialog.loginRequired();
        messageDialog.show(getChildFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    LoginWithSMSActivity.show(getActivity());
//                    LoginRegisterActivity.showLogin(getActivity());
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @OnClick(R.id.userBarcodeId)
    public void onUserIdBarcodeClick() {

    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClick() {
        LoginWithSMSActivity.show(getActivity());
    }

    private void loadProfile() {
        if (ProfileManager.getInstance().isGuest()) return;
        ApiProvider.getInstance().getAuthorizedApi().getUserProfile(ApiStatics.getLastToken().getUserId()).enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {
                if (response.isSuccessful() && response.body().isSuccessful()) {
                    ProfileManager.getInstance().saveProfile(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {

            }
        });
    }

}
