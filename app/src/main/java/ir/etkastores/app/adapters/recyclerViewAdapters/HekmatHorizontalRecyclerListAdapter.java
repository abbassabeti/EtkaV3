package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by garshasbi on 4/18/18.
 */

public class HekmatHorizontalRecyclerListAdapter extends RecyclerView.Adapter<HekmatHorizontalRecyclerListAdapter.ViewHolder>{

    private List<HekmatModel> items;
    private Context context;
    private LayoutInflater inflater;

    private OnItemClickListener callback;

    public HekmatHorizontalRecyclerListAdapter(Context context,OnItemClickListener callback) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.cell_hekmat_horizontal_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<HekmatModel> newItems){
        items.addAll(newItems);
        notifyItemInserted(items.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        AppCompatImageView image;

        @BindView(R.id.title)
        TextView title;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) callback.OnHekmatItemClick(items.get(getAdapterPosition()));
                }
            });
        }

        public void bind(HekmatModel model){
            ImageLoader.loadProductImage(context,image,model.getImageUrl());
            title.setText(model.getTitle());
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true));
    }

    public interface OnItemClickListener{
        void OnHekmatItemClick(HekmatModel hekmatModel);
    }
}
