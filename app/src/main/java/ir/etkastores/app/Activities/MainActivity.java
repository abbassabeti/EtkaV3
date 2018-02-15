package ir.etkastores.app.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.Home.HomeFragment;
import ir.etkastores.app.Fragments.MapFragment;
import ir.etkastores.app.Fragments.ProfileFragment;
import ir.etkastores.app.Fragments.SearchFragments.SearchTabFragment;
import ir.etkastores.app.R;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainBottomNavigation)
    BottomNavigationViewEx bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        bottomNavigationView.setCurrentItem(3);
    }

    BottomNavigationViewEx.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.navigation_home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.navigation_map:
                    replaceFragment(new MapFragment());
                    break;

                case R.id.navigation_profile:
                    replaceFragment(new ProfileFragment());
                    break;

                case R.id.navigation_search:
                    replaceFragment(new SearchTabFragment());
                    break;
            }

            return true;
        }
    };

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.homeActivityFragmentsHolder, fragment).commitNowAllowingStateLoss();
    }

}

