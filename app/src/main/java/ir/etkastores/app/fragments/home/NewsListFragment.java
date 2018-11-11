package ir.etkastores.app.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.NewsActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.NewsListRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.news.NewsItem;
import ir.etkastores.app.models.news.NewsRequestModel;
import ir.etkastores.app.models.news.NewsResponseModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListFragment extends Fragment implements NewsListRecyclerAdapter.OnNewsListCallbackListener {

    public final static int TAB_POSITION_ID = 3;

    public static Fragment newInstance() {
        return new NewsListFragment();
    }

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("News List Fragment");
    }

    private void initViews() {
        requestModel = new NewsRequestModel(null, 1);
        adapter = new NewsListRecyclerAdapter(getActivity());
        adapter.setCallback(this);
        recyclerView.setAdapter(adapter);
        loadNews();
    }

    private void loadNews() {
        newsReq = ApiProvider.getInstance().getAuthorizedApi().getNews(requestModel);
        showLoading();
        newsReq.enqueue(new Callback<OauthResponse<NewsResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<NewsResponseModel>> call, Response<OauthResponse<NewsResponseModel>> response) {
                if (!isAdded()) return;
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
                            showMessageView(response.body().getMeta().getMessage(), false);
                        } else {
                            Toaster.show(getActivity(), response.body().getMeta().getMessage());
                        }
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<NewsResponseModel>> call, Throwable throwable) {
                if (!isAdded()) return;
                hideLoading();
                String message = getResources().getString(R.string.errorInReceivingNews);
                if (adapter.getItemCount() == 0) {
                    showMessageView(message, true);
                } else {
                    Toaster.show(getActivity(), message);
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        loadNews();
    }

    private void showMessageView(String message, boolean hasRetry) {
        messageView.show(R.drawable.ic_warning_orange_48dp, message, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                if (!isAdded()) return;
                loadNews();
            }
        });
    }

    @Override
    public void onNewsClicked(NewsItem item) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenNewsDetail);
        NewsActivity.show(getActivity(), item);
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
