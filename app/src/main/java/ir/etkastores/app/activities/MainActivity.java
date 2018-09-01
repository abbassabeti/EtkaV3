package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.profileActivities.EditProfileActivity;
import ir.etkastores.app.activities.profileActivities.FAQActivity;
import ir.etkastores.app.activities.profileActivities.InviteFriendsActivity;
import ir.etkastores.app.activities.profileActivities.NewTicketActivity;
import ir.etkastores.app.activities.profileActivities.NextShoppingListActivity;
import ir.etkastores.app.activities.profileActivities.ProfileSettingActivity;
import ir.etkastores.app.activities.profileActivities.ScoresActivity;
import ir.etkastores.app.activities.profileActivities.ShoppingHistoryActivity;
import ir.etkastores.app.activities.profileActivities.SupportActivity;
import ir.etkastores.app.activities.profileActivities.TextInfoActivity;
import ir.etkastores.app.activities.profileActivities.survey.SurveyListActivity;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.fragments.MapFragment;
import ir.etkastores.app.fragments.ProfileFragment;
import ir.etkastores.app.fragments.home.HekmatWaresSlide;
import ir.etkastores.app.fragments.home.HomeFragment;
import ir.etkastores.app.fragments.searchFragments.SearchTabFragment;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.models.notification.NotificationModel;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.EtkaRemoteConfigManager;

public class MainActivity extends BaseActivity {

    public static void show(Context context, NotificationModel notificationModel) {
        Intent intent = new Intent(context, MainActivity.class);
        if (notificationModel != null) {
            intent.putExtra(NotificationModel.IS_FROM_NOTIFICATION, true);
            intent.putExtra(NotificationModel.NOTIFICATION_OBJECT, notificationModel.toJson());
        }
        context.startActivity(intent);
    }

    private final static String CURRENT_SELECTED_HOME_FRAGMENT_TAG = "CURRENT_SELECTED_HOME_FRAGMENT_TAG";

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationViewEx bottomNavigationView;

    private int homeSelectedTab = -1;

    private NotificationModel notificationModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EtkaRemoteConfigManager.checkRemoteConfigs();

        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(true);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        if (getIntent() != null && getIntent().hasExtra(NotificationModel.NOTIFICATION_OBJECT)) {
            try {
                notificationModel = NotificationModel.fromJson(getIntent().getExtras().getString(NotificationModel.NOTIFICATION_OBJECT));
            } catch (Exception err) {
                notificationModel = null;
            }
        }

        bottomNavigationView.setCurrentItem(3);

        if (notificationModel != null) {
            handleNotificationAction();
        }

        StoresManager.getInstance().fetchStores(null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Main Activity");
    }

    BottomNavigationViewEx.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if (!(getCurrentFragment() instanceof HomeFragment)) {
                        replaceFragment(new HomeFragment());
                        AdjustHelper.sendAdjustEvent(AdjustHelper.HomeTab);
                    }
                    break;

                case R.id.navigation_map:
                    if (!(getCurrentFragment() instanceof MapFragment)) {
                        replaceFragment(new MapFragment());
                        AdjustHelper.sendAdjustEvent(AdjustHelper.MapTab);
                    }
                    break;

                case R.id.navigation_profile:
                    if (!(getCurrentFragment() instanceof ProfileFragment)) {
                        replaceFragment(new ProfileFragment());
                        AdjustHelper.sendAdjustEvent(AdjustHelper.ProfileTab);
                    }
                    break;

                case R.id.navigation_search:
                    if (!(getCurrentFragment() instanceof SearchTabFragment)) {
                        replaceFragment(new SearchTabFragment());
                        AdjustHelper.sendAdjustEvent(AdjustHelper.SearchTab);
                    }
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.homeActivityFragmentsHolder, fragment, CURRENT_SELECTED_HOME_FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(CURRENT_SELECTED_HOME_FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {
        final MessageDialog exitDialog = MessageDialog.sureToExit();
        exitDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    finish();
                }
                exitDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    void handleNotificationAction() {
        switch (notificationModel.getAction()) {

            case NotificationModel.ACTION_OPEN_APP:
                bottomNavigationView.setCurrentItem(3);
                break;

            case NotificationModel.ACTION_OPEN_HEKMAT_PRODUCTS:
                homeSelectedTab = HekmatWaresSlide.TAB_POSITION_ID;
                bottomNavigationView.setCurrentItem(3);
                break;

            case NotificationModel.ACTION_OPEN_SEARCH:
                bottomNavigationView.setCurrentItem(2);
                break;

            case NotificationModel.ACTION_OPEN_MAP:
                bottomNavigationView.setCurrentItem(1);
                break;

            case NotificationModel.ACTION_OPEN_PROFILE:
                bottomNavigationView.setCurrentItem(0);
                break;

            case NotificationModel.ACTION_OPEN_PRODUCT:
                ProductActivity.show(this, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_FAQ:
                FAQActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_EDIT_PROFILE:
                EditProfileActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_CONSUME_YOUR_POINTS:
                ScoresActivity.start(this);
                break;

            case NotificationModel.ACTION_OPEN_NEXT_SHOPPING:
                NextShoppingListActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SHOPPING_HISTORY:
                ShoppingHistoryActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_INVITE_FRIENDS:
                InviteFriendsActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT:
                SupportActivity.show(this, SupportActivity.CONTACT_US, null);
                break;

            case NotificationModel.ACTION_OPEN_NEW_SUPPORT_TICKET:
                NewTicketActivity.show(this, NewTicketActivity.SUPPORT_TYPE);
                break;

            case NotificationModel.ACTION_OPEN_NEW_PRODUCT_REQUEST_TICKET:
                NewTicketActivity.show(this, NewTicketActivity.REQUEST_PRODUCT_TYPE);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT_TICKET:
                SupportActivity.show(this, SupportActivity.SUPPORT_TICKET, null);
                break;

            case NotificationModel.ACTION_OPEN_REQUEST_PRODUCT_TICKET:
                SupportActivity.show(this, SupportActivity.REQUEST_PRODUCT, null);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT_REPLY_TICKET:
                SupportActivity.show(this, SupportActivity.SUPPORT_TICKET, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_PRODUCT_REQUEST_REPLY_TICKET:
                SupportActivity.show(this, SupportActivity.REQUEST_PRODUCT, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_LOGIN:
                LoginWithSMSActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SURVEY:
                SurveyListActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_PROFILE_SETTING:
                ProfileSettingActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_ABOUT_ETKA_STORES:
                TextInfoActivity.showAboutEtkaStores(this);
                break;

            case NotificationModel.ACTION_OPEN_USER_PRIVACY:
                TextInfoActivity.showUserPrivacy(this);
                break;

            case NotificationModel.ACTION_OPEN_TERM_OF_USE:
                TextInfoActivity.showTermAndConditions(this);
                break;

            case NotificationModel.ACTION_OPEN_STORE_PROFILE:
                StoreActivity.show(this, StoreModel.fromJson(notificationModel.getData()));
                break;

            case NotificationModel.ACTION_OPEN_NEWS:
                NewsActivity.show(this, NewsItem.fromJson(notificationModel.getData()));
                break;

            case NotificationModel.ACTION_OPEN_NEWS_LIST:
                NewsListActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_GALLERY:
                GalleryActivity.show(this, GalleryItemsModel.fromJson(notificationModel.getData()));
                break;

            default:
                bottomNavigationView.setCurrentItem(3);
                break;

        }
    }
}

