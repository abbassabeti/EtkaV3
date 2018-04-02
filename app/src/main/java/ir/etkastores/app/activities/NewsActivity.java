package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class NewsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String NEWS = "NEWS";

    public static void show(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(NEWS, new Gson().toJson(newsItem));
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

    private NewsItem newsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        newsItem = NewsItem.fromJson(getIntent().getExtras().getString(NEWS));
        toolbar.setActionListeners(this);
        toolbar.setTitle(newsItem.getTitle());
        newsDate.setText(newsItem.getDate());
        newsId.setText(String.format(getResources().getString(R.string.newsIdX),newsItem.getId()));
        newsBody.setText(newsItem.getContent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("News Activity");
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
