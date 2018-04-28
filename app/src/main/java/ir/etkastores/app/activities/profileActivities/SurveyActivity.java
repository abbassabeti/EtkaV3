package ir.etkastores.app.activities.profileActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.survey.SurveyListRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.survey.SurveyModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context) {
        Intent intent = new Intent(context, SurveyActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.submitButton)
    Button submitButton;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    private SurveyListRecyclerAdapter adapter;
    private Call<OauthResponse<SurveyModel>> req;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Survey Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void initViews() {
        toolbar.setActionListeners(this);
        adapter = new SurveyListRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    @OnClick(R.id.submitButton)
    public void onSubmitClicked() {

    }

    private void loadData() {
        showLoading();
        req = ApiProvider.getAuthorizedApi().getSurveys();
        req.enqueue(new Callback<OauthResponse<SurveyModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<SurveyModel>> call, Response<OauthResponse<SurveyModel>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        adapter.addItems(response.body().getData().getQuestions());
                    }else{
                        boolean showRetry = true;
                        if (response.body().getMeta().getStatusCode() == 400) showRetry = false;
                        showError(response.body().getMeta().getMessage(),showRetry);
                    }
                }else{
                    onFailure(call,null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<SurveyModel>> call, Throwable throwable) {
                if (isFinishing()) return;
                showError(getResources().getString(R.string.errorInDataReceiving),true);
                hideLoading();
            }
        });

    }

    private void showError(final String message,boolean showRetry) {
        String retry = getResources().getString(R.string.retry);
        if (!showRetry) retry = null;
        messageView.show(R.drawable.ic_warning_orange_48dp, message, retry , new MessageView.OnMessageViewButtonClick() {
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

}
