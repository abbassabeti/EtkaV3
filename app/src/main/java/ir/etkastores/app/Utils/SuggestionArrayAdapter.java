package ir.etkastores.app.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/5/18.
 */

public class SuggestionArrayAdapter extends ArrayAdapter {

    private List<SearchViewItem> originalDataList;
    private List<SearchViewItem> dataList;
    private Context context;
    private LayoutInflater inflater;

    public SuggestionArrayAdapter(Context context, List<SearchViewItem> storeDataLst) {
        super(context, R.layout.store_suggestion_row_item, storeDataLst);
        originalDataList = storeDataLst;
        dataList = getCopyFromOriginalList();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (dataList == null) return 0;
        return dataList.size();
    }

    @Nullable
    @Override
    public SearchViewItem getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        SearchViewHolder holder = null;

        if (view == null) {
            view = inflater.inflate(R.layout.store_suggestion_row_item, parent, false);
            holder = new SearchViewHolder();
            holder.icon = (AppCompatImageView) view.findViewById(R.id.icon);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.description = (TextView) view.findViewById(R.id.description);
            view.setTag(holder);
        } else {
            holder = (SearchViewHolder) view.getTag();
        }

        SearchViewItem item = getItem(position);
        holder.bind(item);
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    static class SearchViewHolder {
        AppCompatImageView icon;
        TextView title;
        TextView description;

        void bind(SearchViewItem item) {
            icon.setImageResource(item.getIcon());
            if (TextUtils.isEmpty(item.getDescription())) {
                description.setVisibility(View.GONE);
            } else {
                description.setVisibility(View.VISIBLE);
                description.setText(item.getDescription());
            }
            title.setText(item.getTitle());
        }

    }

    Filter listFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<SearchViewItem> tempList = new ArrayList<SearchViewItem>();
            if (constraint != null && originalDataList != null) {
                for (SearchViewItem item : originalDataList) {
                    if (item.getTitle().startsWith(constraint.toString())) tempList.add(item);
                }
                filterResults.values = tempList;
                filterResults.count = tempList.size();
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            dataList = (ArrayList<SearchViewItem>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    };

    public static class SearchViewItem implements Cloneable {

        String title;
        String description;
        int icon;
        boolean isEmptyStateItem = false;
        StoreModel storeModel;

        public SearchViewItem(int icon, String title, String description) {
            this.title = title;
            this.icon = icon;
            this.description = description;
        }

        public SearchViewItem(StoreModel store){
            this.title = store.getName();
            this.description = store.getProvinceName();
            this.icon = store.getIcon();
            this.storeModel = store;
        }

        public String getTitle() {
            return title;
        }

        public int getIcon() {
            return icon;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public SearchViewItem clone() {
            return new SearchViewItem(icon, title, description);
        }

        @Override
        public String toString() {
            return title;
        }

        public StoreModel getStoreModel(){
            return storeModel;
        }
    }

    private List<SearchViewItem> getCopyFromOriginalList() {
        ArrayList<SearchViewItem> copyList = new ArrayList<>();
        for (SearchViewItem item : originalDataList) {
            copyList.add(item.clone());
        }
        return copyList;
    }

}

