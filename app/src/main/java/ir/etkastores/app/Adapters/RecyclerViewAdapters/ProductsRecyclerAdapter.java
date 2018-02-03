package ir.etkastores.app.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
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
import ir.etkastores.app.DummyProvider;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.Image.ImageLoader;

/**
 * Created by Sajad on 2/2/18.
 */

public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder> {

    private List<ProductModel> items;
    private Context context;
    private LayoutInflater inflater;

    private boolean isLoadMoreEnabled;
    private ProductsRecyclerCallbacks productsRecyclerCallbacks;

    public ProductsRecyclerAdapter(Context context, ProductsRecyclerCallbacks callbacks) {
        this.context = context;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        productsRecyclerCallbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.cell_product,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
        if (isLoadMoreEnabled && productsRecyclerCallbacks != null && position == items.size()-1){
            isLoadMoreEnabled = true;
            productsRecyclerCallbacks.onLoadMore();
        }
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

    public void addItems(List<ProductModel> newItems){
        int start = items.size();
        items.addAll(newItems);
        notifyItemRangeInserted(start,newItems.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImage)
        AppCompatImageView image;

        @BindView(R.id.productLine1)
        TextView name;

        @BindView(R.id.productLine2)
        TextView price;

        @BindView(R.id.productLine3)
        TextView scorePoint;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final ProductModel model) {
            image.setImageResource(R.drawable.etka_logo_wide);
            name.setText(model.getTitle());
            price.setText(model.getOriginalPrice());
            scorePoint.setText(String.valueOf(model.getPoint()));

            if (TextUtils.isEmpty(model.getImageUrl())){
                image.setImageResource(DummyProvider.getRandomImgId());
            }else{
                ImageLoader.load(context,image,model.getImageUrl());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (productsRecyclerCallbacks != null) productsRecyclerCallbacks.onProductItemClick(model);
                }
            });

        }

    }

    public interface ProductsRecyclerCallbacks{
        void onLoadMore();
        void onProductItemClick(ProductModel productModel);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
    }

}
