package ir.etkastores.app.activities;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.profileActivities.EditProfileActivity;
import ir.etkastores.app.activities.profileActivities.FAQActivity;
import ir.etkastores.app.activities.profileActivities.HekmatActivity;
import ir.etkastores.app.activities.profileActivities.InviteFriendsActivity;
import ir.etkastores.app.activities.profileActivities.NextShoppingListActivity;
import ir.etkastores.app.activities.profileActivities.ProfileSettingActivity;
import ir.etkastores.app.activities.profileActivities.ScoresActivity;
import ir.etkastores.app.activities.profileActivities.ShoppingHistoryActivity;
import ir.etkastores.app.activities.profileActivities.SupportActivity;
import ir.etkastores.app.activities.profileActivities.TextInfoActivity;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.fragments.home.HekmatWaresSlide;
import ir.etkastores.app.fragments.home.HomeFragment;
import ir.etkastores.app.fragments.MapFragment;
import ir.etkastores.app.fragments.ProfileFragment;
import ir.etkastores.app.fragments.searchFragments.SearchTabFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.models.notification.NotificationModel;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.services.StoresGeofenceTransitionsIntentService;
import ir.etkastores.app.ui.dialogs.MessageDialog;

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
                    }
                    break;

                case R.id.navigation_map:
                    if (!(getCurrentFragment() instanceof MapFragment)) {
                        replaceFragment(new MapFragment());
                    }
                    break;

                case R.id.navigation_profile:
                    if (!(getCurrentFragment() instanceof ProfileFragment)) {
                        replaceFragment(new ProfileFragment());
                    }
                    break;

                case R.id.navigation_search:
                    if (!(getCurrentFragment() instanceof SearchTabFragment)) {
                        replaceFragment(new SearchTabFragment());
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
                if (button == RIGHT_BUTTON) {
                    MainActivity.super.onBackPressed();
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

            case NotificationModel.ACTION_OPEN_HEKMAT_ACCOUNT:
                HekmatActivity.show(this);
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
                SupportActivity.show(this, SupportActivity.TICKET_LIST);
                break;

            case NotificationModel.ACTION_OPEN_TICKET:

                break;

            case NotificationModel.ACTION_OPEN_NEW_TICKET:

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

//    private void initStoresGeoFencing(){
//        GeofencingClient geofencingClient = LocationServices.getGeofencingClient(this);
//        Geofence geofence = new Geofence.Builder()
//                .build();
//        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
//                .addGeofence(null)
//                .build();
//
//        geofencingClient.addGeofences(geofencingRequest,getGeofencePendingIntent());
//    }
//
//    PendingIntent geofenceTransitionsIntentService;
//
//    private PendingIntent getGeofencePendingIntent() {
//        // Reuse the PendingIntent if we already have it.
//        if (geofenceTransitionsIntentService != null) {
//            return geofenceTransitionsIntentService;
//        }
//        Intent intent = new Intent(this, StoresGeofenceTransitionsIntentService.class);
//        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
//        // calling addGeofences() and removeGeofences().
//        geofenceTransitionsIntentService = PendingIntent.getService(this, 0, intent, PendingIntent.
//                FLAG_UPDATE_CURRENT);
//        return geofenceTransitionsIntentService;
//
//    }

}

