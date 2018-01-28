package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.Models.Factor.FactorRequestModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.FactorItemView;
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

    @BindView(R.id.factorsHolder)
    LinearLayout factorsHolder;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_history);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);

        showLoading();
        FactorRequestModel requestModel = new FactorRequestModel();
        requestModel.setUserId("a9979337-8485-4e5d-9f94-03e5a3b3b440");
        Call<OauthResponse<List<FactorModel>>> factorRequest = ApiProvider.getAuthorizedApi().getFactor(requestModel);
        factorRequest.enqueue(new Callback<OauthResponse<List<FactorModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<FactorModel>>> call, Response<OauthResponse<List<FactorModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        for (FactorModel factor : response.body().getData()) addItem(factor);
                    } else {

                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<FactorModel>>> call, Throwable t) {
                Log.e("factor response failure", "TTTTTTTT");
                Toaster.show(ShoppingHistoryActivity.this,"خطا در دریافت اطلاعات از دوباره تلاش کنید.");
                hideLoading();
            }
        });

    }

    private void addItem(FactorModel factor) {
        factorsHolder.addView(new FactorItemView(this, factor));
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }

}
