package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.R;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ProductFilterCategoryViewHolder.OnCategoryClickListener {

    private Context context;
    private LayoutInflater inflater;
    private List<ProductFilterItem> items;
    private List<CategoryItem> originalCategoryItems;
    private FilterCallback callback;

    private ProductFilterItem headerItem;

    public ProductFilterListRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        headerItem = new ProductFilterItem(ProductFilterItem.HEADER);
        items.add(headerItem);
        originalCategoryItems = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == ProductFilterItem.HEADER) {
            return new ProductFilterHeaderViewHolder(inflater.inflate(R.layout.cell_product_filter_header, viewGroup, false)) {
                @Override
                void onSearched(String searchedKeyword) {
                    filterCategory(searchedKeyword);
                }
            };
        } else {
            return new ProductFilterCategoryViewHolder(inflater.inflate(R.layout.cell_product_filter_category, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == ProductFilterItem.HEADER) {
            ((ProductFilterHeaderViewHolder) viewHolder).bind(items.get(i), callback);
        } else {
            ((ProductFilterCategoryViewHolder) viewHolder).bind(items.get(i), this);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setCategories(List<CategoryItem> categories) {
        originalCategoryItems = categories;
        items.clear();
        items.add(headerItem);
        for (CategoryItem i : getCopyOfCategories()) {
            items.add(new ProductFilterItem(i));
        }
        notifyItemChanged(1, categories.size());
    }

    public void setFilterCallback(FilterCallback filterCallback) {
        this.callback = filterCallback;
    }

    private void filterCategory(String s) {
        List<CategoryItem> newList;
        items.clear();
        items.add(headerItem);
        if (TextUtils.isEmpty(s)) {
            for (CategoryItem i : getCopyOfCategories()) {
                items.add(new ProductFilterItem(i));
            }
            notifyItemRangeChanged(1,originalCategoryItems.size()+1);
            return;
        }
        newList = new ArrayList<>();
        for (CategoryItem c : getCopyOfCategories()) {
            if (c.getTitle().contains(s)) newList.add(c);
        }
        for (CategoryItem i : newList) {
            items.add(new ProductFilterItem(i));
        }
        notifyItemRangeChanged(1, originalCategoryItems.size()+1);
    }

    @Override
    public void onCategorySelect(CategoryItem categoryItem) {
        List<CategoryItem> categoryItems = new ArrayList<>();
        for (ProductFilterItem i : items) {
            if (i.getType() == ProductFilterItem.CATEGORY_ITEM && i.getCategoryItem().isSelected())
                categoryItems.add(i.getCategoryItem());
        }
        if (callback != null) callback.onSelectCategory(categoryItems);
    }

    public interface FilterCallback {
        int TOP_OFFER_SORT = 1;
        int TOP_RATE_SORT = 2;
        int TOP_SALE_SORT = 3;
        int NEWEST_SORT = 4;

        void onSelectSort(int sort);

        void onSelectCategory(List<CategoryItem> categories);
    }

    private List<CategoryItem> getCopyOfCategories() {
        List<CategoryItem> items = new ArrayList<>();
        for (CategoryItem categoryItem : originalCategoryItems) {
            items.add(categoryItem.getCopy());
        }
        return items;
    }

}
