package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.R;
import ir.etkastores.app.utils.image.ImageLoader;

/**
 * Created by Sajad on 2/2/18.
 */

public class HekmatRecyclerAdapter extends RecyclerView.Adapter<HekmatRecyclerAdapter.ViewHolder> {

    private List<HekmatModel> items;
    private Context context;
    private LayoutInflater inflater;

    private OnHekmatItemClickListener onHekmatItemClickListener;

    public HekmatRecyclerAdapter(Context context, OnHekmatItemClickListener onHekmatItemClickListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        this.onHekmatItemClickListener = onHekmatItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.cell_hekmat_product,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<HekmatModel> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImage)
        AppCompatImageView image;

        @BindView(R.id.productTitle)
        TextView name;

        @BindView(R.id.productPrice1)
        TextView endDate;

        @BindView(R.id.productLine3)
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final HekmatModel model){
            image.setImageResource(R.drawable.etka_logo_wide);
            name.setText(model.getTitle());
            endDate.setText(context.getResources().getString(R.string.validUntil)+model.getEndDate());
            count.setText(String.format(EtkaApp.getInstance().getResources().getString(R.string.XProducts),model.getProducts().size()));
            ImageLoader.loadProductImage(context,image,model.getImageUrl());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHekmatItemClickListener != null) onHekmatItemClickListener.onHekmatItemClick(model);
                }
            });
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
    }

    public interface OnHekmatItemClickListener{
        void onHekmatItemClick(HekmatModel hekmatModel);
    }

}
