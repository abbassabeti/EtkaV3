package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatTransactionRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.card.InstallmentItem;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HekmatTransactionsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context) {
        Intent intent = new Intent(context, HekmatTransactionsActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    private Call<OauthResponse<List<InstallmentItem>>> req;
    private HekmatTransactionRecyclerAdapter adapter;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat_transactions);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Transactions Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (req != null) req.cancel();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        adapter = new HekmatTransactionRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        messageView.hide();
        loadData();
    }

    private void loadData() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingData);
        req = ApiProvider.getAuthorizedApi().getHekmatTransactions();
        req.enqueue(new Callback<OauthResponse<List<InstallmentItem>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<InstallmentItem>>> call, Response<OauthResponse<List<InstallmentItem>>> response) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        if (response.body().getData() != null && response.body().getData().size()>0){
                            adapter.addItems(response.body().getData());
                        }else{
                            messageView.show(R.drawable.ic_warning_orange_48dp,R.string.youHasNotAnyTransactionInfo,0,null);
                        }
                    } else {
                        showRetry(response.body().getMeta().getMessage(), false);
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<InstallmentItem>>> call, Throwable t) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                showRetry(getResources().getString(R.string.errorInDataReceiving), true);
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

    private void showRetry(String message, boolean showRetry) {
        String retryButton = getResources().getString(R.string.retry);
        if (!showRetry) retryButton = null;
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                getResources().getString(R.string.error),
                message,
                retryButton,
                getResources().getString(R.string.close));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    loadData();
                } else {
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
