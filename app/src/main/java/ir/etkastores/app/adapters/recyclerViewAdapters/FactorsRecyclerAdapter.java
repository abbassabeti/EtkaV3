package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.ui.views.FactorItemView;

/**
 * Created by Sajad on 1/27/18.
 */

public class FactorsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FactorModel> items;
    private Context context;
    LayoutInflater inflater;

    public FactorsRecyclerAdapter(Context context,List<FactorModel> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FactorViewHolder(new FactorItemView(context,items.get(viewType)));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FactorViewHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class FactorViewHolder extends RecyclerView.ViewHolder{

        FactorItemView factorItemView;

        public FactorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            factorItemView = (FactorItemView) itemView;
        }

        public void bind(FactorModel factorModel){
            factorItemView.setFactor(factorModel);
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
