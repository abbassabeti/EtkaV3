package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.ui.views.FilterSortView;

/**
 * Created by garshasbi on 4/19/18.
 */

public abstract class ProductFilterHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.topRatedSortItem)
    FilterSortView topRateSortItem;

    @BindView(R.id.topOfferSortItem)
    FilterSortView topOfferSortItem;

    @BindView(R.id.topSaleSortItem)
    FilterSortView topSaleSortItem;

    @BindView(R.id.newestSortItem)
    FilterSortView newestSortItem;

    @BindView(R.id.categorySearchEt)
    EditText categorySearchEt;

    private ProductFilterListRecyclerAdapter.FilterCallback callback;


    public ProductFilterHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        topRateSortItem.setCategoryItem(new CategoryModel(EtkaApp.getInstance().getResources().getString(R.string.topRate),1,true));
        topOfferSortItem.setCategoryItem(new CategoryModel(EtkaApp.getInstance().getResources().getString(R.string.topOffer),2));
        topSaleSortItem.setCategoryItem(new CategoryModel(EtkaApp.getInstance().getResources().getString(R.string.topSales),3));
        newestSortItem.setCategoryItem(new CategoryModel(EtkaApp.getInstance().getResources().getString(R.string.newest),4));

    }

    public void bind(ProductFilterItem filterItem, ProductFilterListRecyclerAdapter.FilterCallback callback) {
        this.callback = callback;
    }

    @OnClick({R.id.topRatedSortItem, R.id.topOfferSortItem, R.id.topSaleSortItem, R.id.newestSortItem})
    public void onSortItemClicked(View view) {

        topOfferSortItem.setSelect(false);
        topRateSortItem.setSelect(false);
        topSaleSortItem.setSelect(false);
        newestSortItem.setSelect(false);

        switch (view.getId()) {
            case R.id.topRatedSortItem:
                topRateSortItem.setSelect(true);
                if (callback != null) callback.onSelectSort(ProductFilterListRecyclerAdapter.FilterCallback.TOP_RATE_SORT);
                break;

            case R.id.topOfferSortItem:
                topOfferSortItem.setSelect(true);
                if (callback != null) callback.onSelectSort(ProductFilterListRecyclerAdapter.FilterCallback.TOP_OFFER_SORT);
                break;

            case R.id.topSaleSortItem:
                topSaleSortItem.setSelect(true);
                if (callback != null) callback.onSelectSort(ProductFilterListRecyclerAdapter.FilterCallback.TOP_SALE_SORT);
                break;

            case R.id.newestSortItem:
                newestSortItem.setSelect(true);
                if (callback != null) callback.onSelectSort(ProductFilterListRecyclerAdapter.FilterCallback.NEWEST_SORT);
                break;
        }

    }

    @OnTextChanged(R.id.categorySearchEt)
    public void OnSearchText(CharSequence s){
        onSearched(s.toString());
    }

    abstract void onSearched(String searchedKeyword);

}
