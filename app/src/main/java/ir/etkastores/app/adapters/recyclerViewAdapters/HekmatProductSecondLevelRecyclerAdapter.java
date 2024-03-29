package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.hekmat.HekmatProductModel;

/**
 * Created by garshasbi on 3/3/18.
 */

public class HekmatProductSecondLevelRecyclerAdapter extends RecyclerView.Adapter<HekmatProductSecondLevelRecyclerAdapter.ViewHolder> {

    List<HekmatProductModel> items = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    private OnHekmatProductClickListener onHekmatProductClickListener;

    public HekmatProductSecondLevelRecyclerAdapter(List<HekmatProductModel> items, Context context) {
        this.items = items;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(inflater.inflate(R.layout.cell_hekmat_second_level, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public void setOnHekmatProductClickListener(OnHekmatProductClickListener onHekmatProductClickListener) {
        this.onHekmatProductClickListener = onHekmatProductClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.distribution)
        TextView distribution;

        @BindView(R.id.discountedPrice)
        TextView discountedPrice;

        @BindView(R.id.share)
        TextView share;

        @BindView(R.id.subject)
        TextView subject;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onHekmatProductClickListener != null)
                        onHekmatProductClickListener.onHekmatProductClick(items.get(getAdapterPosition()));
                }
            });
        }

        public void bind(HekmatProductModel model) {
            title.setText(model.getTitle());
            distribution.setText(String.format(context.getResources().getString(R.string.distributionX), model.getDistribution()));
            discountedPrice.setText(String.format(context.getResources().getString(R.string.unitPriceX), model.getDiscountedPrice()));
            share.setText(model.getShare());
            subject.setText(context.getResources().getString(R.string.sujectsComma) + model.getSubject());
        }

    }


    public interface OnHekmatProductClickListener {
        void onHekmatProductClick(HekmatProductModel productModel);
    }


}
