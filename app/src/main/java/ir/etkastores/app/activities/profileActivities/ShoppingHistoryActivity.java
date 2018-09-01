package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.FactorsAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.models.factor.FactorRequestModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingHistoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, MessageView.OnMessageViewButtonClick, FactorsAdapter.OnFactorsAdapterCallbacksListener {

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, ShoppingHistoryActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    private FactorsAdapter adapter;
    private FactorRequestModel requestModel;
    private Call<OauthResponse<List<FactorModel>>> factorRequest;

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
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
        adapter = new FactorsAdapter(this);
        adapter.setCallbacksListener(this);
        recyclerView.setAdapter(adapter);
        requestModel = new FactorRequestModel();
        requestModel.setTake(10);
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
        requestModel.setSkip(page);
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
                        if (response.body().getData().size() == requestModel.getTake()) {
                            adapter.setLoadMoreEnabled(true);
                            page++;
                        }
                    } else {
                        showErrorMessageView(response.body().getMeta().getMessage(),true);
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<FactorModel>>> call, Throwable t) {
                if (isFinishing()) return;
                if (adapter.getItemCount() == 0){
                    showErrorMessageView(getResources().getString(R.string.errorHappendInReceivingData),true);
                }else{
                    showErrorMessageDialog();
                }
                hideLoading();
            }
        });
    }

    private void showErrorMessageView(String message, boolean showRetry){
        String retry = getResources().getString(R.string.retry);
        if (!showRetry) retry = null;
        messageView.show(R.drawable.ic_warning_orange_48dp, message, retry, this);
    }

    private void showErrorMessageDialog(){
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error),getResources().getString(R.string.errorHappendInReceivingData));
        messageDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON){
                    loadData();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showLoading() {
        if (adapter.getItemCount() == 0) progressBar.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        linearProgress.setVisibility(View.GONE);
    }

    @Override
    public void onMessageViewButtonClick() {
        if (isFinishing()) return;
        loadData();
    }

    @Override
    public void onLoadMore() {
        loadData();
    }

}
