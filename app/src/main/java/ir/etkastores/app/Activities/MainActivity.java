package ir.etkastores.app.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Fragments.Home.HomeFragment;
import ir.etkastores.app.Fragments.MapFragment;
import ir.etkastores.app.Fragments.ProfileFragment;
import ir.etkastores.app.Fragments.SearchFragments.SearchTabFragment;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.profile.RegisterUserRequestModel;
import ir.etkastores.app.Models.profile.UserGender;
import ir.etkastores.app.R;
import ir.etkastores.app.WebService.AccessToken;
import ir.etkastores.app.WebService.ApiProvider;
import ir.etkastores.app.WebService.ApiStatics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //testRegister();
//        testLogin();

    }

    BottomNavigationViewEx.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

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

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.homeActivityFragmentsHolder,fragment).commitNowAllowingStateLoss();
    }

    private void testRegister(){
        RegisterUserRequestModel requestModel = new RegisterUserRequestModel();
        requestModel.setFirstName("سجاد");
        requestModel.setLastName("گرشاسبی");
        requestModel.setGender(UserGender.MALE);
        requestModel.setCellPhone("09354018630");
        requestModel.setPassword("abcd1234#");
        requestModel.setEmail("sajadgarshasbi@gmail.com");
        Log.e("requestModel",""+new Gson().toJson(requestModel));
        ApiProvider.getApi().registerNewUser(requestModel).enqueue(new Callback<OauthResponse<String>>() {
            @Override
            public void onResponse(Call<OauthResponse<String>> call, Response<OauthResponse<String>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        Log.e("registered User ID",""+response.body().getData());
                    }else{
                        Log.e("registered User error",""+response.body().getMeta().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<String>> call, Throwable t) {
                Log.e("registered failed",""+t.getLocalizedMessage());
            }
        });
    }

    private void testLogin(){
        ApiProvider.getApi().getToken(ApiStatics.GRAND_TYPE_PASSWORD,"sajadgarshasbi@gmail.com","#abcE1234#", ApiStatics.CLIENT_ID,ApiStatics.CLIENT_SECRET,"").enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()){
                    Log.e("login...","success");
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e("login...","failed");
            }
        });
    }

    private void testEditProfile(){

    }

    private void testChangePassword(){

    }

}

