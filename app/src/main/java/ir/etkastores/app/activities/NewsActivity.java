package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.image.ImageLoader;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class NewsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String NEWS = "NEWS";
    private final static String NEWS_ID = "NEWS_ID";

    public static void show(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(NEWS, new Gson().toJson(newsItem));
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(NEWS_ID, id);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.newsBody)
    TextView newsBody;

    @BindView(R.id.newsDate)
    TextView newsDate;

    @BindView(R.id.newsId)
    TextView newsId;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.newsImage)
    AppCompatImageView newsImage;

    private NewsItem newsItem;
    private Long requestedNewId;
    private Call<OauthResponse<NewsItem>> req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);

        newsItem = NewsItem.fromJson(getIntent().getExtras().getString(NEWS));
        if (newsItem != null) {
            fillViews();
        } else {
            requestedNewId = getIntent().getExtras().getLong(NEWS_ID, -1);
            loadNews();
        }

    }

    private void fillViews() {
        messageView.hide();
        hideLoading();
        toolbar.setTitle(newsItem.getTitle());
        newsDate.setText(newsItem.getDate());
        newsId.setText(String.format(getResources().getString(R.string.newsIdX), newsItem.getId()));
        newsBody.setText(newsItem.getContent());
        if (TextUtils.isEmpty(newsItem.getImageUrl())) {
            newsImage.setVisibility(View.GONE);
        } else {
            newsImage.setVisibility(View.VISIBLE);
            ImageLoader.loadImage(this, newsImage, newsItem.getImageUrl());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("News Activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (req != null) req.cancel();
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadNews() {
        showLoading();
        messageView.hide();
        req = ApiProvider.getAuthorizedApi().getNews(requestedNewId);
        req.enqueue(new Callback<OauthResponse<NewsItem>>() {
            @Override
            public void onResponse(Call<OauthResponse<NewsItem>> call, Response<OauthResponse<NewsItem>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        newsItem = response.body().getData();
                        fillViews();
                    } else {
                        showRetryMessage(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<NewsItem>> call, Throwable throwable) {
                if (isFinishing()) return;
                showRetryMessage(getResources().getString(R.string.errorInDataReceiving));
                hideLoading();
            }
        });
    }

    private void showRetryMessage(final String message) {
        messageView.show(R.drawable.ic_warning_orange_48dp, message, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                if (isFinishing()) return;
                loadNews();
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
