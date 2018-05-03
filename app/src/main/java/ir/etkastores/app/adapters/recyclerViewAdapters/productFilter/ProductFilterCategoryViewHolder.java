package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;

/**
 * Created by garshasbi on 4/19/18.
 */

public class ProductFilterCategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;

    public ProductFilterCategoryViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final ProductFilterItem filterItem, final OnCategoryClickListener callback){
        title.setText(filterItem.getCategoryItem().getTitle());
        setState(filterItem.getCategoryItem());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterItem.getCategoryItem().toggle();
                setState(filterItem.getCategoryItem());
                if (callback != null) callback.onCategorySelect(filterItem.getCategoryItem());
            }
        });
    }

    private void setState(CategoryItem categoryItem){
        if (categoryItem.isSelected()){
            title.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.colorPrimary));
            title.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
        }else{
            title.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.white));
            title.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.darkGray));
        }
    }

    public interface OnCategoryClickListener{
        void onCategorySelect(CategoryItem categoryItem);
    }

}
