package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.hekmat.card.HekmatCoupons;

public class HekmatCouponsAdapter extends RecyclerView.Adapter<HekmatCouponsAdapter.ViewHolder> {

    private Context context;
    private List<HekmatCoupons> items;
    private LayoutInflater inflater;

    public HekmatCouponsAdapter(Context context, List<HekmatCoupons> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.cell_hekmat_coupons, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.startDate)
        TextView startDate;

        @BindView(R.id.expireDate)
        TextView expireDate;

        @BindView(R.id.number)
        TextView number;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(HekmatCoupons item) {
            title.setText(item.getCouponTitle());
            number.setText(String.valueOf(item.getCouponNumber()));
            expireDate.setText(item.getFormattedEnd());
            startDate.setText(item.getFormattedStart());
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
