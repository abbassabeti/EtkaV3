package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.NewsListRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.models.news.NewsRequestModel;
import ir.etkastores.app.models.news.NewsResponseModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, NewsListRecyclerAdapter.OnNewsListCallbackListener {

    public static void show(Context context) {
        Intent intent = new Intent(context, NewsListActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    @BindView(R.id.messageView)
    MessageView messageView;

    private Call<OauthResponse<NewsResponseModel>> newsReq;
    private NewsRequestModel requestModel;
    private NewsListRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("News List Activity");
    }

    private void initViews() {
        requestModel = new NewsRequestModel(null, 1);
        adapter = new NewsListRecyclerAdapter(this);
        adapter.setCallback(this);
        recyclerView.setAdapter(adapter);
        loadNews();
    }


    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadNews() {
        newsReq = ApiProvider.getAuthorizedApi().getNews(requestModel);
        showLoading();
        newsReq.enqueue(new Callback<OauthResponse<NewsResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<NewsResponseModel>> call, Response<OauthResponse<NewsResponseModel>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.addItems(response.body().getData().getItems());
                        if (response.body().getData().getTotalItemsCount() > adapter.getItemCount()) {
                            adapter.setLoadMoreEnabled(true);
                            requestModel.setPage(requestModel.getPage() + 1);
                        }
                        if (adapter.getItemCount() == 0) {
                            messageView.show(R.drawable.ic_warning_orange_48dp, getResources().getString(R.string.newsListIsEmpty), null, null);
                        }
                    } else {
                        if (adapter.getItemCount() == 0) {
                            showRetryMessageView(response.body().getMeta().getMessage());
                        } else {
                            showRetryDialog(response.body().getMeta().getMessage());
                        }
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<NewsResponseModel>> call, Throwable throwable) {
                if (isFinishing()) return;
                hideLoading();
                String message = getResources().getString(R.string.errorInReceivingNews);
                if (adapter.getItemCount() == 0) {
                    showRetryMessageView(message);
                } else {
                    showRetryDialog(message);
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        loadNews();
    }

    private void showRetryDialog(String message) {
        messageView.show(R.drawable.ic_warning_orange_48dp, message, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                if (isFinishing()) return;
                loadNews();
            }
        });
    }

    private void showRetryMessageView(String message) {
        final MessageDialog messageDialog = MessageDialog.errorRetry(message);
        messageDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    loadNews();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @Override
    public void onNewsClicked(NewsItem item) {
        NewsActivity.show(this, item);
    }

    private void showLoading() {
        messageView.hide();
        if (adapter.getItemCount() == 0) circularProgress.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
        linearProgress.setVisibility(View.GONE);
    }

}
