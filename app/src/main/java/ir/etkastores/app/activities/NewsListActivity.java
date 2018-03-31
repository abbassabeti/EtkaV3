package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.news.NewsRequestModel;
import ir.etkastores.app.models.news.NewsResponseModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context) {
        Intent intent = new Intent(context, NewsListActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private Call<OauthResponse<NewsResponseModel>> newsReq;
    private NewsRequestModel requestModel;

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
        newsReq.enqueue(new Callback<OauthResponse<NewsResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<NewsResponseModel>> call, Response<OauthResponse<NewsResponseModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<OauthResponse<NewsResponseModel>> call, Throwable throwable) {
                Log.e("failure",""+throwable.getLocalizedMessage());
            }
        });
    }

}
