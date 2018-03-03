package ir.etkastores.app.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;

/**
 * Created by garshasbi on 3/1/18.
 */

public class StoresRecyclerAdapter extends RecyclerView.Adapter<StoresRecyclerAdapter.ViewHolder> {

    private List<StoreModel> originalStores;
    private List<StoreModel> filteredStores;
    private LayoutInflater inflater;

    private OnStoreSelectListener onStoreSelectListener;

    public StoresRecyclerAdapter(Context context) {
        originalStores = new ArrayList<>();
        filteredStores = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.store_suggestion_row_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(filteredStores.get(i));
    }

    @Override
    public int getItemCount() {
        return filteredStores.size();
    }

    public void setStores(List<StoreModel> stores) {
        originalStores = stores;
        filterKeyword(null);
    }

    public void filterKeyword(String keyword) {
        List<StoreModel> result = new ArrayList<>();
        if (TextUtils.isEmpty(keyword)) {
            result = getCopyOfStores();
        } else {
            for (StoreModel store : getCopyOfStores()) {
                if ((store.getProvinceName() + " " + store.getName()).contains(keyword)) {
                    result.add(store);
                }
            }
        }
        filteredStores = result;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        AppCompatImageView icon;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStoreSelectListener != null)
                        onStoreSelectListener.onStoreSelect(filteredStores.get(getAdapterPosition()));
                }
            });
        }

        public void bind(StoreModel store) {
            icon.setImageResource(store.getIcon());
            title.setText(store.getName());
            description.setText(store.getProvinceName());
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public void setOnStoreSelectListener(OnStoreSelectListener onStoreSelectListener) {
        this.onStoreSelectListener = onStoreSelectListener;
    }

    private List<StoreModel> getCopyOfStores() {
        List<StoreModel> copyList = new ArrayList<>();
        for (StoreModel store : originalStores) {
            copyList.add(store.clone());
        }
        return copyList;
    }

    public interface OnStoreSelectListener {
        void onStoreSelect(StoreModel store);
    }

}
