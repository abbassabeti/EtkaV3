package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatCouponsAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.HekmatCoupons;
import ir.etkastores.app.models.hekmat.card.HekmatCouponsResponseModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class HekmatCardCouponsProductsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private static final String MODEL = "MODEL";
    private static final String PROVINCE_ID = "PROVINCE_ID";
    private static final String PROVINCE_NAME = "PROVINCE_NAME";

    public static void show(Context context, int provinceId, String provinceName) {
        Intent intent = new Intent(context, HekmatCardCouponsProductsActivity.class);
        intent.putExtra(PROVINCE_NAME, provinceName);
        intent.putExtra(PROVINCE_ID, provinceId);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.contentHolder)
    View contentHolder;

    private HekmatCouponsResponseModel model;
    private HekmatCouponsAdapter adapter;

    private Call<OauthResponse<List<HekmatCoupons>>> req;
    private AlertDialog loadingDialog;

    private int provinceId;
    private String provinceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_hekmat_card_coupons_products);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        model = HekmatCouponsResponseModel.fromJson(getIntent().getStringExtra(MODEL));
        provinceId = getIntent().getIntExtra(PROVINCE_ID, 1);
        provinceName = getIntent().getStringExtra(PROVINCE_NAME);
        adapter = new HekmatCouponsAdapter(this);
        toolbar.setTitle(toolbar.getTitle() + " - " + provinceName);
        recyclerView.setAdapter(adapter);
        initViews();
    }

    private void initViews() {
        if (model != null) {
            adapter.addItems(model.getCoupons());
            checkEmptyState();
        } else {
            loadData();
        }
    }

    private void loadData() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingData);
        req = ApiProvider.getAuthorizedApi().getHekmatCoupons(provinceId);
        req.enqueue(new Callback<OauthResponse<List<HekmatCoupons>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<HekmatCoupons>>> call, Response<OauthResponse<List<HekmatCoupons>>> response) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        model = new HekmatCouponsResponseModel();
                        model.setCoupons(response.body().getData());
                        adapter.addItems(response.body().getData());
                        checkEmptyState();
                    } else {
                        showError(response.body().getMeta().getMessage(), false);
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<HekmatCoupons>>> call, Throwable t) {
                if (isFinishing()) return;
                showError(getResources().getString(R.string.errorInDataReceiving), true);
                loadingDialog.cancel();
            }
        });
    }

    private void checkEmptyState() {
        if (adapter.getItemCount() == 0) {
            contentHolder.setVisibility(View.GONE);
            messageView.show(R.drawable.ic_warning_orange_48dp,
                    getResources().getString(R.string.thereIsNotResultAvailable),
                    null,
                    null);
        } else {
            contentHolder.setVisibility(View.VISIBLE);
            messageView.hide();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MODEL, new Gson().toJson(model));
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Coupons List Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    private void showError(final String message, boolean showRetry) {
        String retryButton = getResources().getString(R.string.retry);
        if (!showRetry) retryButton = null;
        contentHolder.setVisibility(View.GONE);
        messageView.show(R.drawable.ic_warning_orange_48dp,
                message,
                retryButton,
                new MessageView.OnMessageViewButtonClick() {
                    @Override
                    public void onMessageViewButtonClick() {
                        messageView.hide();
                        loadData();
                    }
                });
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
