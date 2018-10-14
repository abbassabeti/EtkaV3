package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.models.factor.PurchasedProductModel;
import ir.etkastores.app.utils.FontUtils;

/**
 * Created by garshasbi on 4/14/18.
 */

public class FactorListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int HEADER_TYPE = 1;
    private final static int PURCHASE_TYPE = 2;
    private final static int FOOTER_TYPE = 3;

    private Context context;
    private FactorModel factorModel;
    private LayoutInflater inflater;
    private List<FactorListItem> items;

    public FactorListRecyclerAdapter(Context context, FactorModel factorModel) {
        this.context = context;
        this.factorModel = factorModel;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        items.add(new FactorListItem(HEADER_TYPE));
        for (PurchasedProductModel purchasedProductModel : factorModel.getPurchasedProducts()){
            items.add(new FactorListItem(purchasedProductModel));
        }
        items.add(new FactorListItem(FOOTER_TYPE));
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == HEADER_TYPE){
            return new HeaderViewHolder(inflater.inflate(R.layout.cell_factor_list_header,viewGroup,false));
        }else if (i == FOOTER_TYPE){
            return new FooterViewHolder(inflater.inflate(R.layout.cell_factor_list_footer,viewGroup,false));
        }else {
            return new PurchaseViewHolder(inflater.inflate(R.layout.cell_factor_list_purchase_item,viewGroup,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int type = viewHolder.getItemViewType();
        if (type  == HEADER_TYPE){
            ((HeaderViewHolder) viewHolder).bind();
        }else if (type  == FOOTER_TYPE){
            ((FooterViewHolder) viewHolder).bind();
        }else {
            ((PurchaseViewHolder) viewHolder).bind(items.get(i).getPurchasedProductModel());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.code)
        TextView code;

        @BindView(R.id.storeName)
        TextView storeName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(){
            date.setText(String.format(context.getResources().getString(R.string.factorDate),factorModel.getDate()));
            code.setText(String.format(context.getResources().getString(R.string.factorCode),factorModel.getFactorCode()));
            storeName.setText(String.format(context.getResources().getString(R.string.storeX),factorModel.getStoreName()));
        }

    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.totalPrice)
        TextView totalPrice;

        @BindView(R.id.totalDiscount)
        TextView totalDiscount;

        @BindView(R.id.totalDiscountTitle)
        TextView totalDiscountTitle;

        @BindView(R.id.totalTax)
        TextView totalTax;

        @BindView(R.id.totalPayment)
        TextView totalPayment;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            totalDiscountTitle.setTypeface(FontUtils.getBoldTypeFace());
            totalDiscount.setTypeface(FontUtils.getBoldTypeFace());
        }

        public void bind(){
            totalPrice.setText(factorModel.getTotalPrice());
            totalDiscount.setText(factorModel.getTotalDiscount());
            totalTax.setText(factorModel.getTax());
            totalPayment.setText(factorModel.getTotalPaid());
        }

    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.count)
        TextView count;

        @BindView(R.id.discount)
        TextView discount;

        @BindView(R.id.totalPrice)
        TextView totalPrice;

        public PurchaseViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final PurchasedProductModel productModel){
            title.setText(productModel.getTitle());
            price.setText(productModel.getPrice());
            count.setText(String.valueOf(productModel.getCount()));
            discount.setText(productModel.getDiscount());
            totalPrice.setText(productModel.getTotalPrice());
        }

    }

    class FactorListItem {
        private int type;
        private PurchasedProductModel purchasedProductModel;

        public FactorListItem(PurchasedProductModel purchasedProductModel) {
            this.purchasedProductModel = purchasedProductModel;
            this.type = PURCHASE_TYPE;
        }

        public FactorListItem(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public PurchasedProductModel getPurchasedProductModel() {
            return purchasedProductModel;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
