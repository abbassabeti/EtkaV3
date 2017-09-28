package ir.etkastores.app.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.HomeFragment;
import ir.etkastores.app.Fragments.MapFragment;
import ir.etkastores.app.Fragments.ProfileFragment;
import ir.etkastores.app.Fragments.SearchFragment;
import ir.etkastores.app.R;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationViewEx bottomNavigationView;

    private Fragment homeFragment , profileFragment, searchFragment, mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        mapFragment = new MapFragment();

        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        bottomNavigationView.setCurrentItem(0);

    }

    BottomNavigationViewEx.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.navigation_home:
                    replaceFragment(homeFragment);
                    break;

                case R.id.navigation_map:
                    replaceFragment(mapFragment);
                    break;

                case R.id.navigation_profile:
                    replaceFragment(profileFragment);
                    break;

                case R.id.navigation_search:
                    replaceFragment(searchFragment);
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.homeActivityFragmentsHolder,fragment).commitNowAllowingStateLoss();
    }

}

