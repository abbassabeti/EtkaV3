package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.ui.views.FactorItemView;

/**
 * Created by garshasbi on 4/6/18.
 */

public class FactorsAdapter extends RecyclerView.Adapter<FactorsAdapter.ViewHolder> {

    private List<FactorModel> factors;
    private Context context;
    private boolean isLoadMoreEnabled = false;
    private OnFactorsAdapterCallbacksListener callbacksListener;

    public FactorsAdapter(Context context) {
        factors = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new FactorItemView(context));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(factors.get(position));
        if (isLoadMoreEnabled && callbacksListener != null){
            isLoadMoreEnabled = false;
            callbacksListener.onLoadMore();
        }
    }

    public void addItems(List<FactorModel> items) {
        int startPosition = factors.size();
        factors.addAll(items);
        notifyItemRangeInserted(startPosition, items.size());
    }

    @Override
    public int getItemCount() {
        return factors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private FactorItemView view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = (FactorItemView) itemView;
        }

        public void bind(FactorModel factor) {
            view.setFactor(factor);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
    }

    public void setCallbacksListener(OnFactorsAdapterCallbacksListener callbacksListener) {
        this.callbacksListener = callbacksListener;
    }

    public interface OnFactorsAdapterCallbacksListener{
        void onLoadMore();
    }

}
