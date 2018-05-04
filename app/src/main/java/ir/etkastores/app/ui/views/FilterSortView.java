package ir.etkastores.app.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.CategoryModel;

/**
 * Created by garshasbi on 4/19/18.
 */

public class FilterSortView extends CardView {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.icon)
    AppCompatImageView icon;

    @BindView(R.id.holder)
    RelativeLayout holder;

    private CategoryModel categoryItem;

    public FilterSortView(@NonNull Context context,CategoryModel categoryItem) {
        super(context);
        this.categoryItem = categoryItem;
        init();
    }

    public FilterSortView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterSortView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.filter_sort_view, this, true);
        ButterKnife.bind(this, this);
        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent));
    }

    public void setSelect(boolean select) {
        categoryItem.setSelected(select);
        if (select){
            holder.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.lightColorPrimary));
            title.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            icon.setImageResource(R.drawable.ic_radio_button_checked_orange_24dp);
        }else{
            holder.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
            title.setTextColor(ContextCompat.getColor(getContext(),R.color.darkGray));
            icon.setImageResource(R.drawable.ic_radio_button_unchecked_orange_24dp);
        }
    }

    public CategoryModel getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(CategoryModel categoryItem) {
        this.categoryItem = categoryItem;
        title.setText(categoryItem.getTitle());
        setSelect(categoryItem.isSelected());
    }

}
