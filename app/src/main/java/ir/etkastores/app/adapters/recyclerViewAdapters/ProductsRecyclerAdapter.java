package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.utils.StringUtils;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 2/2/18.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder> {

    private List<ProductModel> items;
    private Context context;
    private LayoutInflater inflater;

    private boolean isLoadMoreEnabled;
    private ProductsRecyclerCallbacks productsRecyclerCallbacks;
    private boolean isNextShoppingList = false;

    public ProductsRecyclerAdapter(Context context, ProductsRecyclerCallbacks callbacks) {
        this.context = context;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        productsRecyclerCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.cell_product, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
        if (isLoadMoreEnabled && productsRecyclerCallbacks != null && position == items.size() - 1) {
            isLoadMoreEnabled = false;
            productsRecyclerCallbacks.onLoadMore();
        }
    }

    public List<ProductModel> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public void addItems(List<ProductModel> newItems) {
        int start = items.size();
        items.addAll(newItems);
        notifyItemRangeInserted(start, newItems.size());
    }

    public void deleteItem(int index) {
        items.remove(index);
        notifyItemRemoved(index);
    }

    public void setNextShoppingListMode(boolean isShoppingList) {
        isNextShoppingList = isShoppingList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImage)
        AppCompatImageView image;

        @BindView(R.id.productTitle)
        TextView name;

        @BindView(R.id.productPrice1)
        TextView price1;

        @BindView(R.id.productPrice2)
        TextView price2;

//        @BindView(R.id.productLine3)
//        TextView scorePoint;

        @BindView(R.id.nextShoppingControlsHolder)
        View nextShoppingControlsHolder;

        @BindView(R.id.deleteSavedButton)
        AppCompatImageView deleteButton;

        @BindView(R.id.savedCountTv)
        TextView savedCount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ProductModel model) {
            image.setImageResource(R.drawable.product_place_holder);
            name.setText(model.getTitle());
            price1.setText(model.getStrikeThruPrice());
            StringUtils.setStrikeThruTextView(price1);
            price2.setText(model.getFinalPrice());
//            scorePoint.setText(model.getPoint());
            if (model.getImageUrl().size() > 0) {
                ImageLoader.loadProductImage(context, image, model.getImageUrl());
            }
            savedCount.setText(String.valueOf(model.getSavedCount()));
            if (isNextShoppingList) {
                nextShoppingControlsHolder.setVisibility(View.VISIBLE);
            } else {
                nextShoppingControlsHolder.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productsRecyclerCallbacks != null)
                        productsRecyclerCallbacks.onProductItemClick(model);
                }
            });

            if (BuildConfig.DEBUG) itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toaster.showLong(itemView.getContext(),new Gson().toJson(model));
                    return false;
                }
            });

        }

        @OnClick(R.id.deleteSavedButton)
        public void deleteSavedButton() {
            if (productsRecyclerCallbacks != null)
                productsRecyclerCallbacks.onProductSavedDeleteClick(items.get(getAdapterPosition()));
        }

    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public interface ProductsRecyclerCallbacks {
        void onLoadMore();

        void onProductItemClick(ProductModel productModel);

        void onProductSavedDeleteClick(ProductModel productModel);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

}
