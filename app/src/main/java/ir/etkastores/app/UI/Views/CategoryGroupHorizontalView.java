package ir.etkastores.app.UI.Views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.DummyProvider;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 11/25/17.
 */

public class CategoryGroupHorizontalView extends RelativeLayout {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public CategoryGroupHorizontalView(Context context) {
        super(context);
        init(null);
    }

    public CategoryGroupHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CategoryGroupHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        View.inflate(getContext(),R.layout.view_category_group_horizontal,this);
        ButterKnife.bind(this);

        List<String> items = new ArrayList<>();
        items.add("یک");
        items.add("دو");
        items.add("سه");
        items.add("چهار");
        items.add("پنج");
        items.add("شش");
        items.add("هفت");
        items.add("هشت");
        items.add("نه");
        items.add("ده");
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(getContext(),items);
        recyclerView.setAdapter(adapter);

        if (attrs != null){

        }

    }

    class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>{

        private List<String> items;
        private LayoutInflater inflater;

        public CategoryRecyclerAdapter(Context context, List<String> items) {
            this.inflater = LayoutInflater.from(context);
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.cell_product,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.productImage)
            AppCompatImageView image;

            @BindView(R.id.productName)
            TextView name;

            @BindView(R.id.productPrice)
            TextView price;

            @BindView(R.id.scoreValue)
            TextView scoreValue;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }

            public void bind(){
                image.setImageResource(R.drawable.etka_logo_wide);
                name.setText("نام کالا");
                price.setText("قیمت کالا");
                image.setImageResource(DummyProvider.getRandomImgId());
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        }
    }

}
