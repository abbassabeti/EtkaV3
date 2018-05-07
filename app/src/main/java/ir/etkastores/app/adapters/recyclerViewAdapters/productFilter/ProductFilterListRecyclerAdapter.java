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
import ir.etkastores.app.models.CategoryModel;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ProductFilterCategoryViewHolder.OnCategoryClickListener {

    private Context context;
    private LayoutInflater inflater;
    private List<ProductFilterItem> items;
    private List<CategoryModel> originalCategoryItems;
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
        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setCategories(List<CategoryModel> categories) {
        originalCategoryItems = categories;
        items.clear();
        items.add(headerItem);
        for (CategoryModel i : getCopyOfCategories()) {
            items.add(new ProductFilterItem(i));
        }
        notifyItemChanged(1, categories.size());
    }

    public void setFilterCallback(FilterCallback filterCallback) {
        this.callback = filterCallback;
    }

    private void filterCategory(String s) {
        List<CategoryModel> newList;
        items.clear();
        items.add(headerItem);
        if (TextUtils.isEmpty(s)) {
            for (CategoryModel i : getCopyOfCategories()) {
                items.add(new ProductFilterItem(i));
            }
            notifyItemRangeChanged(1,originalCategoryItems.size()+1);
            return;
        }
        newList = new ArrayList<>();
        for (CategoryModel c : getCopyOfCategories()) {
            if (c.getTitle().contains(s)) newList.add(c);
        }
        for (CategoryModel i : newList) {
            items.add(new ProductFilterItem(i));
        }
        notifyItemRangeChanged(1, originalCategoryItems.size()+1);
    }

    @Override
    public void onCategorySelect(CategoryModel categoryItem) {
        List<CategoryModel> categoryItems = new ArrayList<>();
        for (ProductFilterItem i : items) {
            if (i.getType() == ProductFilterItem.CATEGORY_ITEM){
                if (i.getCategoryItem().isSelected()) categoryItems.add(i.getCategoryItem());
                for (CategoryModel c : originalCategoryItems){
                    if (c.getId() == i.getCategoryItem().getId()) c.setSelected(i.getCategoryItem().isSelected());
                }
            }
        }
        if (callback != null) callback.onSelectCategory(categoryItems);
    }

    public interface FilterCallback {
        int TOP_OFFER_SORT = 1;
        int TOP_RATE_SORT = 2;
        int TOP_SALE_SORT = 3;
        int NEWEST_SORT = 4;

        void onSelectSort(int sort);

        void onSelectCategory(List<CategoryModel> categories);
    }

    private List<CategoryModel> getCopyOfCategories() {
        List<CategoryModel> items = new ArrayList<>();
        for (CategoryModel categoryItem : originalCategoryItems) {
            items.add(categoryItem.getCopy());
        }
        return items;
    }

}
