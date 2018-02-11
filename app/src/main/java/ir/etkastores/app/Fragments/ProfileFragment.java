package ir.etkastores.app.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.UserProfileModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/1/17.
 */

public class ProfileFragment extends Fragment implements EtkaToolbar.EtkaToolbarActionsListener {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private AlertDialog loadingDialog;
    Call<OauthResponse<UserProfileModel>> profileReq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_profile,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    private void initViews(){
        toolbar.setActionListeners(this);
    }

    @OnClick(R.id.hekmatMenu)
    public void onHekmatMenuClick(){
        HekmatActivity.show(getActivity());
    }

    @OnClick(R.id.scoreMenu)
    public void onScoreMenuClick(){
        ScoresActivity.start(getActivity());
    }

    @OnClick(R.id.nextShoppingListMenu)
    public void onNextShoppingListMenuClick(){
        NextShoppingListActivity.start(getActivity());
    }

    @OnClick(R.id.shoppingHistoryMenu)
    public void onShoppingHistoryMenuClick(){
        ShoppingHistoryActivity.start(getActivity());
    }

    @OnClick(R.id.inviteFriendsMenu)
    public void onInviteFriendsMenuClick(){
        InviteFriendsActivity.start(getActivity());
    }

    @OnClick(R.id.faqMenu)
    public void onFAQMenuClick(){
        FAQActivity.start(getActivity());
    }

    @OnClick(R.id.supportMenu)
    public void onSupportMenuClick(){
        SupportActivity.start(getActivity());
    }

    @Override
    public void onToolbarBackClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {
        switch (actionCode){

            case MORE_BUTTON:
                OtherPagesActivity.start(getActivity());
                break;

            case SETTING_BUTTON:
                ProfileSettingActivity.start(getActivity());
                break;

        }
    }

    private void loadProfile(){
        loadingDialog = DialogHelper.showLoading(getActivity(),R.string.inLoadingUserProfile);
        profileReq = ApiProvider.getAuthorizedApi().getUserProfile();
        profileReq.enqueue(new Callback<OauthResponse<UserProfileModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<UserProfileModel>> call, Response<OauthResponse<UserProfileModel>> response) {

            }

            @Override
            public void onFailure(Call<OauthResponse<UserProfileModel>> call, Throwable t) {

            }
        });
    }

    private void showErrorDialog(String message){
        MessageDialog messageDialog = MessageDialog.warningRetry("","");
    }

}
