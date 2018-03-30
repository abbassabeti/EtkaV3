package ir.etkastores.app.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.fragments.home.HomeFragment;
import ir.etkastores.app.fragments.MapFragment;
import ir.etkastores.app.fragments.ProfileFragment;
import ir.etkastores.app.fragments.searchFragments.SearchTabFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.dialogs.MessageDialog;

public class MainActivity extends BaseActivity {

    private final static String CURRENT_SELECTED_HOME_FRAGMENT_TAG = "CURRENT_SELECTED_HOME_FRAGMENT_TAG";

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationViewEx bottomNavigationView;

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

        bottomNavigationView.setCurrentItem(3);
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
                    if (!(getCurrentFragment() instanceof  HomeFragment)){
                        replaceFragment(new HomeFragment());
                    }
                    break;

                case R.id.navigation_map:
                    if (!(getCurrentFragment() instanceof  MapFragment)){
                        replaceFragment(new MapFragment());
                    }
                    break;

                case R.id.navigation_profile:
                    if (!(getCurrentFragment() instanceof ProfileFragment)){
                        replaceFragment(new ProfileFragment());
                    }
                    break;

                case R.id.navigation_search:
                    if (!(getCurrentFragment() instanceof SearchTabFragment)){
                        replaceFragment(new SearchTabFragment());
                    }
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.homeActivityFragmentsHolder, fragment,CURRENT_SELECTED_HOME_FRAGMENT_TAG).commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment(){
        return getSupportFragmentManager().findFragmentByTag(CURRENT_SELECTED_HOME_FRAGMENT_TAG);
    }

    @Override
    public void onBackPressed() {
        final MessageDialog exitDialog = MessageDialog.sureToExit();
        exitDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    MainActivity.super.onBackPressed();
                }
                exitDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }
}

