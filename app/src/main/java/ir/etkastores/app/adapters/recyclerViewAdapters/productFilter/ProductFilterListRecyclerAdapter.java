package ir.etkastores.app.adapters.recyclerViewAdapters.productFilter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class ProductFilterListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ProductFilterItem> items;

    public ProductFilterListRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        items.add(new ProductFilterItem(ProductFilterItem.HEADER));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("بیل",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کلنگ",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("...",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("خبر",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("اتکا",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("تست",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("سجاد",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("پیام",1, false)));
        items.add(new ProductFilterItem(new CategoryItem("هادی",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("بیل",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کلنگ",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("...",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("خبر",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("اتکا",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("تست",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("سجاد",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("پیام",1, false)));
        items.add(new ProductFilterItem(new CategoryItem("هادی",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
        items.add(new ProductFilterItem(new CategoryItem("کیک",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("بیسکوئیت",1,true)));
        items.add(new ProductFilterItem(new CategoryItem("شکولات",1,false)));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == ProductFilterItem.HEADER){
            return new ProductFilterHeaderViewHolder(inflater.inflate(R.layout.cell_product_filter_header,viewGroup,false));
        }else{
            return new ProductFilterCategoryViewHolder(inflater.inflate(R.layout.cell_product_filter_category,viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder.getItemViewType() == ProductFilterItem.HEADER){
            ((ProductFilterHeaderViewHolder) viewHolder).bind(items.get(i));
        }else{
            ((ProductFilterCategoryViewHolder) viewHolder).bind(items.get(i));
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

}
