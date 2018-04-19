package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.profileActivities.EditProfileActivity;
import ir.etkastores.app.ui.views.FilterSortView;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterHeaderViewHolder extends RecyclerView.ViewHolder {

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


    public ProductFilterHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        topRateSortItem.setCategoryItem(new CategoryItem(EtkaApp.getInstance().getResources().getString(R.string.topRate),1,true));
        topOfferSortItem.setCategoryItem(new CategoryItem(EtkaApp.getInstance().getResources().getString(R.string.topOffer),2));
        topSaleSortItem.setCategoryItem(new CategoryItem(EtkaApp.getInstance().getResources().getString(R.string.topSales),3));
        newestSortItem.setCategoryItem(new CategoryItem(EtkaApp.getInstance().getResources().getString(R.string.newest),4));

    }

    public void bind(ProductFilterItem filterItem) {

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
                break;

            case R.id.topOfferSortItem:
                topOfferSortItem.setSelect(true);
                break;

            case R.id.topSaleSortItem:
                topSaleSortItem.setSelect(true);
                break;

            case R.id.newestSortItem:
                newestSortItem.setSelect(true);
                break;
        }
    }

}
