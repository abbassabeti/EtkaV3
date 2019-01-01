package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
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
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.fragments.MapFragment;
import ir.etkastores.app.fragments.ProfileFragment;
import ir.etkastores.app.fragments.home.HekmatWaresSlide;
import ir.etkastores.app.fragments.home.HomeFragment;
import ir.etkastores.app.fragments.home.NewsListFragment;
import ir.etkastores.app.fragments.home.SpecialOffersSlide;
import ir.etkastores.app.fragments.searchFragments.SearchTabFragment;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.models.notification.NotificationModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.EtkaRemoteConfigManager;
import ir.etkastores.app.utils.FontUtils;

@Obfuscate
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
        bottomNavigationView.setTextVisibility(true);
        bottomNavigationView.setLabelVisibilityMode(1);
        bottomNavigationView.setItemHorizontalTranslationEnabled(false);
        bottomNavigationView.setTypeface(FontUtils.getCommonTypeFace());
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        notificationModel = getNotificationModel();

        if (notificationModel != null) {
            handleNotificationAction();
        } else {
            setHomeTabDefault();
        }

        StoresManager.getInstance().fetchStores(null);

    }

    private NotificationModel getNotificationModel() {
        if (getIntent() != null && getIntent().hasExtra(NotificationModel.NOTIFICATION_OBJECT)) {
            try {
                return NotificationModel.fromJson(getIntent().getExtras().getString(NotificationModel.NOTIFICATION_OBJECT));
            } catch (Exception err) {
                return null;
            }
        } else {
            return null;
        }
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
                        if (getNotificationModel() == null)
                            homeSelectedTab = SpecialOffersSlide.TAB_POSITION_ID;
                        replaceFragment(HomeFragment.newInstance(homeSelectedTab));
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

            case NotificationModel.ACTION_OPEN_APP: // TESTED
                setHomeTabDefault();
                break;

            case NotificationModel.ACTION_OPEN_HEKMAT_PRODUCTS: // TESTED
                homeSelectedTab = HekmatWaresSlide.TAB_POSITION_ID;
                bottomNavigationView.setCurrentItem(3);
                break;

            case NotificationModel.ACTION_OPEN_SEARCH: // TESTED
                SearchProductRequestModel searchProductRequestModel = SearchProductRequestModel.fromJson(notificationModel.getData());
                if (searchProductRequestModel == null) {
                    bottomNavigationView.setCurrentItem(2);
                } else {
                    CategoriesFilterActivity.show(this, searchProductRequestModel);
                }
                break;

            case NotificationModel.ACTION_OPEN_MAP: // TESTED
                bottomNavigationView.setCurrentItem(1);
                break;

            case NotificationModel.ACTION_OPEN_PROFILE: // TESTED
                bottomNavigationView.setCurrentItem(0);
                break;

            case NotificationModel.ACTION_OPEN_PRODUCT: // TESTED
                setHomeTabDefault();
                ProductActivity.show(this, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_FAQ: // TESTED
                setHomeTabDefault();
                FAQActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_EDIT_PROFILE: // TESTED
                setHomeTabDefault();
                EditProfileActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_CONSUME_YOUR_POINTS: // NOT_IMPLEMENTED
                setHomeTabDefault();
                ScoresActivity.start(this);
                break;

            case NotificationModel.ACTION_OPEN_NEXT_SHOPPING: // TESTED
                setHomeTabDefault();
                NextShoppingListActivity.Companion.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SHOPPING_HISTORY: // TESTED
                setHomeTabDefault();
                ShoppingHistoryActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_INVITE_FRIENDS: // TESTED
                setHomeTabDefault();
                InviteFriendsActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT: // TESTED
                setHomeTabDefault();
                SupportActivity.show(this, SupportActivity.CONTACT_US, null);
                break;

            case NotificationModel.ACTION_OPEN_NEW_SUPPORT_TICKET: // TESTED
                setHomeTabDefault();
                NewTicketActivity.show(this, NewTicketActivity.SUPPORT_TYPE);
                break;

            case NotificationModel.ACTION_OPEN_NEW_PRODUCT_REQUEST_TICKET: // TESTED
                setHomeTabDefault();
                NewTicketActivity.show(this, NewTicketActivity.REQUEST_PRODUCT_TYPE);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT_TICKET: // TESTED
                setHomeTabDefault();
                SupportActivity.show(this, SupportActivity.SUPPORT_TICKET, null);
                break;

            case NotificationModel.ACTION_OPEN_REQUEST_PRODUCT_TICKET: // TESTED
                setHomeTabDefault();
                SupportActivity.show(this, SupportActivity.REQUEST_PRODUCT, null);
                break;

            case NotificationModel.ACTION_OPEN_SUPPORT_REPLY_TICKET:
                setHomeTabDefault();
                SupportActivity.show(this, SupportActivity.SUPPORT_TICKET, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_PRODUCT_REQUEST_REPLY_TICKET:
                setHomeTabDefault();
                SupportActivity.show(this, SupportActivity.REQUEST_PRODUCT, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_LOGIN: // TEST
                setHomeTabDefault();
                if (ProfileManager.getInstance().isGuest()) LoginWithSMSActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_SURVEY: // TESTED
                setHomeTabDefault();
                SurveyListActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_PROFILE_SETTING: // TESTED
                setHomeTabDefault();
                ProfileSettingActivity.show(this);
                break;

            case NotificationModel.ACTION_OPEN_ABOUT_ETKA_STORES: // TESTED
                setHomeTabDefault();
                TextInfoActivity.showAboutEtkaStores(this);
                break;

            case NotificationModel.ACTION_OPEN_USER_PRIVACY: // TESTED
                setHomeTabDefault();
                TextInfoActivity.showUserPrivacy(this);
                break;

            case NotificationModel.ACTION_OPEN_TERM_OF_USE: // TESTED
                setHomeTabDefault();
                TextInfoActivity.showTermAndConditions(this);
                break;

            case NotificationModel.ACTION_OPEN_STORE_PROFILE: // TESTED
                setHomeTabDefault();
                StoreActivity.show(this, notificationModel.getData());
                break;

            case NotificationModel.ACTION_OPEN_NEWS: // TESTED
                setHomeTabDefault();
                if (!TextUtils.isEmpty(notificationModel.getData())) {
                    long newsId = Long.parseLong(notificationModel.getData());
                    NewsActivity.show(this, newsId);
                }
                break;

            case NotificationModel.ACTION_OPEN_NEWS_LIST: // TESTED
                homeSelectedTab = NewsListFragment.TAB_POSITION_ID;
                bottomNavigationView.setCurrentItem(3);
                break;

            case NotificationModel.ACTION_OPEN_GALLERY:
                setHomeTabDefault();
                GalleryActivity.show(this, GalleryItemsModel.fromJson(notificationModel.getData()));
                break;

            default:
                setHomeTabDefault();
                break;

        }
        getIntent().removeExtra(NotificationModel.NOTIFICATION_OBJECT);
    }

    private void setHomeTabDefault() {
        homeSelectedTab = SpecialOffersSlide.TAB_POSITION_ID;
        bottomNavigationView.setCurrentItem(3);
    }
}

