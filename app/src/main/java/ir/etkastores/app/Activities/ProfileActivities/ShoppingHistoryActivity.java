package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Models.factor.FactorModel;
import ir.etkastores.app.Models.factor.FactorRequestModel;
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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FactorsAdapter adapter;
    private FactorRequestModel requestModel;
    private Call<OauthResponse<List<FactorModel>>> factorRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_history);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        adapter = new FactorsAdapter();
        recyclerView.setAdapter(adapter);
        requestModel = new FactorRequestModel();
        requestModel.setUserId("a9979337-8485-4e5d-9f94-03e5a3b3b440");
        loadData();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadData() {
        showLoading();
        factorRequest = ApiProvider.getAuthorizedApi().getFactor(requestModel);
        factorRequest.enqueue(new Callback<OauthResponse<List<FactorModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<FactorModel>>> call, Response<OauthResponse<List<FactorModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.addItems(response.body().getData());
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
                Toaster.show(ShoppingHistoryActivity.this, "خطا در دریافت اطلاعات از دوباره تلاش کنید.");
                hideLoading();
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    class FactorsAdapter extends RecyclerView.Adapter<FactorsAdapter.ViewHolder> {

        private List<FactorModel> factors;

        public FactorsAdapter() {
            factors = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new FactorItemView(ShoppingHistoryActivity.this));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(factors.get(position));
        }

        private void addItems(List<FactorModel> items) {
            int startPosition = factors.size();
            factors.addAll(items);
            notifyItemRangeInserted(startPosition, items.size());
        }

        @Override
        public int getItemCount() {
            return factors.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private FactorItemView view;

            public ViewHolder(View itemView) {
                super(itemView);
                view = (FactorItemView) itemView;
            }

            public void bind(FactorModel factor) {
                view.setFactor(factor);
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(ShoppingHistoryActivity.this));
        }

    }

}
