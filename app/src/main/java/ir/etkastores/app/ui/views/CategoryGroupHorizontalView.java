package ir.etkastores.app.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.CategoriesFilterActivity;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.utils.FontUtils;
import ir.etkastores.app.utils.StringUtils;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 11/25/17.
 */

public class CategoryGroupHorizontalView extends RelativeLayout {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.showAll)
    TextView mShowAllButton;

    private List<ProductModel> productModels;
    private String title;
    private SearchProductRequestModel moreItemSearchModel;
    private OnProductClickListener onProductClickListener;

    public CategoryGroupHorizontalView(Context context, String title, List<ProductModel> productModels, SearchProductRequestModel moreItemModel) {
        super(context);
        this.productModels = productModels;
        this.title = title;
        this.moreItemSearchModel = moreItemModel;
        init(null);
    }

    public CategoryGroupHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CategoryGroupHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.view_category_group_horizontal, this);
        ButterKnife.bind(this, this);

        if (productModels == null) return;

        mTitle.setText(title);

        if (moreItemSearchModel != null) {
            mShowAllButton.setVisibility(VISIBLE);
        } else {
            mShowAllButton.setVisibility(GONE);
        }

        mShowAllButton.setTypeface(FontUtils.getBoldTypeFace());

        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(getContext());
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.showAll)
    public void showAllButton() {
        CategoriesFilterActivity.show(getContext(), moreItemSearchModel, title);
    }

    class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

        private LayoutInflater inflater;

        public CategoryRecyclerAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.cell_product_horizontal_row, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(productModels.get(position));
        }

        @Override
        public int getItemCount() {
            return productModels.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.productImage)
            AppCompatImageView image;

            @BindView(R.id.productName)
            TextView name;

            @BindView(R.id.productPrice)
            TextView price;

            @BindView(R.id.productPrice2)
            TextView price2;

//            @BindView(R.id.scoreValue)
//            TextView scoreValue;

            public ViewHolder(final View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onProductClickListener != null)
                            onProductClickListener.onProductClick(productModels.get(getAdapterPosition()));
                    }
                });

                price2.setTypeface(FontUtils.getBoldTypeFace());
            }

            public void bind(final ProductModel model) {
                image.setImageResource(R.drawable.product_place_holder);
                name.setText(model.getTitle());
                price.setText(model.getStrikeThruPrice().trim());
                StringUtils.setStrikeThruTextView(price);
                price2.setText(model.getFinalPrice());
//                if (!TextUtils.isEmpty(model.getPoint())){
//                    scoreValue.setText(String.format(getResources().getString(R.string.Xpoint),model.getPoint()));
//                }else{
//                    scoreValue.setText("");
//                }
                if (model.getImageUrl() != null && model.getImageUrl().size() > 0) {
                    ImageLoader.loadProductImage(getContext(), image, model.getImageUrl());
                }
                if (BuildConfig.DEBUG)
                    itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toaster.showLong(itemView.getContext(), new Gson().toJson(model));
                            return false;
                        }
                    });
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        }
    }

    public interface OnProductClickListener {
        void onProductClick(ProductModel productModel);
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }

}
