package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.Factor.FactorModel;
import ir.etkastores.app.R;

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
        factor = getDummyFactor();
        if (factor != null) setFactor(factor);
    }

    public void setFactor(FactorModel factor){
        this.factor = factor;
        factorDate.setText(factor.getDate());
        factorCode.setText(String.valueOf(factor.getFactorCode()));
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

    public FactorModel getDummyFactor(){
        FactorModel factorModel = new FactorModel();
        factorModel.setDate("۷ بهمن ۱۳۹۶");
        factorModel.setFactorCode(123456789);
        factorModel.setTotalDiscount(1233455467);
        factorModel.setTotalPrice(123123123);
        return factorModel;
    }

}
