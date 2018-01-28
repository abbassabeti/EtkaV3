package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.FactorsRecyclerAdapter;
import ir.etkastores.app.DummyProvider;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.Models.Factor.PurchasedProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Widgets.SquareImageView;
import ir.etkastores.app.Utils.Image.ImageLoader;

/**
 * Created by Sajad on 1/27/18.
 */

public class FactorItemView extends CardView implements View.OnClickListener {

    @BindView(R.id.rowIcon)
    AppCompatImageView rowIcon;

    @BindView(R.id.expandableLayout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.factorDate)
    TextView factorDate;

    @BindView(R.id.factorCode)
    TextView factorCode;

    @BindView(R.id.totalPrice)
    TextView factorPrice;

    @BindView(R.id.discountPrice)
    TextView discountPrice;

    @BindView(R.id.purchasesRecyclerView)
    RecyclerView recyclerView;

    TextView totalScore;

    private FactorModel factor;

    public FactorItemView(@NonNull Context context, FactorModel factor) {
        super(context);
        this.factor = factor;
        init();
    }

    public FactorItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FactorItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_factor_item,this,true);
        ButterKnife.bind(this,this);
        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent));
        this.setOnClickListener(this);
        if (factor != null) setFactor(factor);
    }

    public void setFactor(FactorModel factor){
        this.factor = factor;
        recyclerView.setAdapter(new PurchaseAdapter(factor.getPurchasedProducts()));
        if (TextUtils.isEmpty(factor.getDate())){
            factorDate.setText(String.format(getResources().getString(R.string.factorDate),DummyProvider.getRandomDate()));
        }else{
            factorDate.setText(String.format(getResources().getString(R.string.factorDate),factor.getDate()));
        }
        if (factor.getFactorCode()==0){
            factorCode.setText(String.format(getResources().getString(R.string.factorCode),12332122));
        }else{
            factorCode.setText(String.format(getResources().getString(R.string.factorCode),factor.getFactorCode()));
        }
        factorPrice.setText(String.valueOf(factor.getTotalPrice()));
    }

    @Override
    public void onClick(View v) {
        expandableLayout.toggle();
        if (expandableLayout.isExpanded()){
            rowIcon.setImageResource(R.drawable.ic_arrow_up_black_24dp);
        }else{
            rowIcon.setImageResource(R.drawable.ic_arrow_down_black_24dp);
        }
    }

    public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder>{

        List<PurchasedProductModel> items;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        public PurchaseAdapter(List<PurchasedProductModel> items) {
            this.items = items;
            if (this.items == null) this.items = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.cell_factor_purchase,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.price)
            TextView price;

            @BindView(R.id.image)
            SquareImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            public void bind(PurchasedProductModel purchase){
                price.setText(purchase.getTitle());
                if (!TextUtils.isEmpty(purchase.getImageUrl())){
                    ImageLoader.load(getContext(),image,purchase.getImageUrl());
                }else{
                    image.setImageResource(DummyProvider.getRandomImgId());
                }
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        }

    }

}
