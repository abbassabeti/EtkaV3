package ir.etkastores.app.UI.Views;

import android.content.Context;
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
import ir.etkastores.app.R;

/**
 * Created by Sajad on 1/26/18.
 */

public class FAQItemView extends CardView implements View.OnClickListener {

    @BindView(R.id.faqLayout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.rowIcon)
    AppCompatImageView rowIcon;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.description)
    TextView description;

    private FAQItem item;

    public FAQItemView(Context context, FAQItem item) {
        super(context);
        this.item = item;
        init(null);
    }

    public FAQItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FAQItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext()).inflate(R.layout.view_faq_item,this,true);
        ButterKnife.bind(this,this);
        setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent));
        this.setOnClickListener(this);
        if (item != null) update();
    }

    public void setItem(FAQItem item){
        this.item = item;
        update();
    }

    private void update(){
        try {
            title.setText(item.getTitle());
            description.setText(item.getDescription());
        }catch (Exception err){
            err.printStackTrace();
        }
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

    public static class FAQItem {

        String title;
        String description;

        public FAQItem(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

    }

}