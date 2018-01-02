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
import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.Image.ImageLoader;

/**
 * Created by Sajad on 1/2/18.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> items;
    private LayoutInflater inflater;
    private OnCategoryItemClickListener onCategoryItemClickListener;

    public CategoryRecyclerAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_category_cell,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<CategoryModel> item){
        this.items = item;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.image)
        AppCompatImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final CategoryModel model){
            title.setText(model.getTitle());
            if (!TextUtils.isEmpty(model.getImageUrl())) ImageLoader.load(context,image,model.getImageUrl());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCategoryItemClickListener != null) onCategoryItemClickListener.onCategoryItemClick(model,getAdapterPosition());
                }
            });
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
    }

    public void setOnCategoryItemClickListener(OnCategoryItemClickListener onCategoryItemClickListener) {
        this.onCategoryItemClickListener = onCategoryItemClickListener;
    }

    public interface OnCategoryItemClickListener{
        void onCategoryItemClick(CategoryModel model,int position);
    }

}
