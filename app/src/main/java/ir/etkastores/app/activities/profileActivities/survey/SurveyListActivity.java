package ir.etkastores.app.activities.profileActivities.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.survey.SurveyListRecyclerAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.survey.SurveyModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class SurveyListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, SurveyListRecyclerAdapter.OnSurveyClickListener {

    public static void show(Context context) {
        context.startActivity(new Intent(context, SurveyListActivity.class));
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    private Call<OauthResponse<List<SurveyModel>>> req;
    private SurveyListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_survey_list);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Survey List Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        adapter = new SurveyListRecyclerAdapter(this);
        adapter.setOnSurveyClickListener(this);
        recyclerView.setAdapter(adapter);
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
        req = ApiProvider.getAuthorizedApi().getSurveys();
        req.enqueue(new Callback<OauthResponse<List<SurveyModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<SurveyModel>>> call, Response<OauthResponse<List<SurveyModel>>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.setItems(response.body().getData());
                    } else {
                        boolean showRetry = true;
                        if (response.body().getMeta().getStatusCode() == 400) showRetry = false;
                        showError(response.body().getMeta().getMessage(), showRetry);
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<SurveyModel>>> call, Throwable throwable) {
                if (isFinishing()) return;
                showError(getResources().getString(R.string.errorInDataReceiving), true);
                hideLoading();
            }
        });

    }

    private void showError(final String message, boolean showRetry) {
        String retry = getResources().getString(R.string.retry);
        if (!showRetry) retry = null;
        messageView.show(R.drawable.ic_warning_orange_48dp, message, retry, new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                loadData();
                messageView.hide();
            }
        });
    }

    private void showLoading() {
        circularProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSurveyClick(SurveyModel surveyModel) {
        if (isFinishing()) return;
        SurveyActivity.show(this, surveyModel);
    }
}
