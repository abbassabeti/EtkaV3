package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.Models.Factor.FactorRequestModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingHistoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ShoppingHistoryActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_history);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);

        FactorRequestModel requestModel = new FactorRequestModel();
        requestModel.setUserId("a9979337-8485-4e5d-9f94-03e5a3b3b440");
        Call<OauthResponse<List<FactorModel>>> factorRequest = ApiProvider.getAuthorizedApi().getFactor(requestModel);
        factorRequest.enqueue(new Callback<OauthResponse<List<FactorModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<FactorModel>>> call, Response<OauthResponse<List<FactorModel>>> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<FactorModel>>> call, Throwable t) {
                Log.e("factor response failure",""+t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
