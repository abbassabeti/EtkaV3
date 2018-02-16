package ir.etkastores.app.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.ProfileActivities.FAQActivity;
import ir.etkastores.app.Activities.ProfileActivities.HekmatActivity;
import ir.etkastores.app.Activities.ProfileActivities.InviteFriendsActivity;
import ir.etkastores.app.Activities.ProfileActivities.NextShoppingListActivity;
import ir.etkastores.app.Activities.ProfileActivities.OtherPagesActivity;
import ir.etkastores.app.Activities.ProfileActivities.ProfileSettingActivity;
import ir.etkastores.app.Activities.ProfileActivities.ScoresActivity;
import ir.etkastores.app.Activities.ProfileActivities.ShoppingHistoryActivity;
import ir.etkastores.app.Activities.ProfileActivities.SupportActivity;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.CustomRowMenuItem;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.BarcodeUtils;
import ir.etkastores.app.data.ProfileManager;

/**
 * Created by Sajad on 9/1/17.
 */

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
        UserProfileModel profile = ProfileManager.getProfile();
        userName.setText(profile.getFirstName() + " " + profile.getLastName());
        scoreMenu.setText(String.format(getResources().getString(R.string.youHaveXScore), profile.getTotalPoints()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BarcodeUtils.generateBarcodeBitmap("00989354018630", BarcodeFormat.CODABAR, userBarcodeIdImage);
    }

    @OnClick(R.id.hekmatMenu)
    public void onHekmatMenuClick() {
        HekmatActivity.show(getActivity());
    }

    @OnClick(R.id.scoreMenu)
    public void onScoreMenuClick() {
        ScoresActivity.start(getActivity());
    }

    @OnClick(R.id.nextShoppingListMenu)
    public void onNextShoppingListMenuClick() {
        NextShoppingListActivity.start(getActivity());
    }

    @OnClick(R.id.shoppingHistoryMenu)
    public void onShoppingHistoryMenuClick() {
        ShoppingHistoryActivity.start(getActivity());
    }

    @OnClick(R.id.inviteFriendsMenu)
    public void onInviteFriendsMenuClick() {
        InviteFriendsActivity.start(getActivity());
    }

    @OnClick(R.id.faqMenu)
    public void onFAQMenuClick() {
        FAQActivity.start(getActivity());
    }

    @OnClick(R.id.supportMenu)
    public void onSupportMenuClick() {
        SupportActivity.start(getActivity());
    }

    @Override
    public void onToolbarBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {
        switch (actionCode) {

            case MORE_BUTTON:
                OtherPagesActivity.start(getActivity());
                break;

            case SETTING_BUTTON:
                ProfileSettingActivity.start(getActivity());
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
