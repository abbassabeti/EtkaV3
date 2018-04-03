package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.news.NewsItem;

/**
 * Created by garshasbi on 4/2/18.
 */

public class NewsListRecyclerAdapter extends RecyclerView.Adapter<NewsListRecyclerAdapter.ViewHolder> {

    private List<NewsItem> items;
    private Context context;
    private boolean isLoadMoreEnabled = false;
    private OnNewsListCallbackListener callbackListener;
    private LayoutInflater inflater;

    public NewsListRecyclerAdapter(Context context) {
        items = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.cell_news_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
        if (isLoadMoreEnabled && items.size() > 0 && items.size() - 1 == i && callbackListener != null) {
            isLoadMoreEnabled = false;
            callbackListener.onLoadMore();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.newsTitle)
        TextView title;

        @BindView(R.id.newsDate)
        TextView newsDate;

        @BindView(R.id.newsId)
        TextView newsId;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callbackListener != null)
                        callbackListener.onNewsClicked(items.get(getAdapterPosition()));
                }
            });
        }

        public void bind(NewsItem newsItem) {
            title.setText(newsItem.getTitle());
            newsDate.setText(newsItem.getDate());
            newsId.setText(String.format(context.getResources().getString(R.string.newsIdX),newsItem.getId()));
        }

    }

    public void addItems(List<NewsItem> news){
        int startPosition = items.size();
        items.addAll(news);
        notifyItemRangeInserted(startPosition,news.size());
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setCallback(OnNewsListCallbackListener callback) {
        this.callbackListener = callback;
    }

    public interface OnNewsListCallbackListener {
        void onLoadMore();

        void onNewsClicked(NewsItem item);
    }

}
