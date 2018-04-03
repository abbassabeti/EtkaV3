package ir.etkastores.app.activities.profileActivities;

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
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.models.factor.FactorRequestModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.FactorItemView;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingHistoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, MessageView.OnMessageViewButtonClick {

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, ShoppingHistoryActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

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

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Shopping History Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        adapter = new FactorsAdapter();
        recyclerView.setAdapter(adapter);
        requestModel = new FactorRequestModel();
        requestModel.setUserId(ProfileManager.getProfile().getCrmUserId());
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
        messageView.hide();
        factorRequest = ApiProvider.getAuthorizedApi().getFactor(requestModel);
        factorRequest.enqueue(new Callback<OauthResponse<List<FactorModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<FactorModel>>> call, Response<OauthResponse<List<FactorModel>>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.addItems(response.body().getData());
                        if (adapter.getItemCount() == 0) {
                            messageView.show(R.drawable.ic_warning_orange_48dp, getResources().getString(R.string.youHaveNotAnyShoppingHistory), getResources().getString(R.string.back), new MessageView.OnMessageViewButtonClick() {
                                @Override
                                public void onMessageViewButtonClick() {
                                    if (isFinishing()) return;
                                    onBackPressed();
                                }
                            });
                        }
                    } else {
                        messageView.show(R.drawable.ic_warning_orange_48dp,
                                response.body().getMeta().getMessage(),
                                getResources().getString(R.string.retry),
                                ShoppingHistoryActivity.this);
                    }

                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<FactorModel>>> call, Throwable t) {
                if (isFinishing()) return;
                messageView.show(R.drawable.ic_warning_orange_48dp,
                        getResources().getString(R.string.errorHappendInReceivingData),
                        getResources().getString(R.string.retry),
                        ShoppingHistoryActivity.this);
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

    @Override
    public void onMessageViewButtonClick() {
        loadData();
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
