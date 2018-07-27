package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.FAQItemView;

public class FAQActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener{

    public static void show(Activity activity){
        Intent intent = new Intent(activity,FAQActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("FAQ Activity");
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        List<FAQItemView.FAQItem> faqs = new ArrayList<>();

        faqs.add(new FAQItemView.FAQItem(R.string.q1,R.string.a1));
        faqs.add(new FAQItemView.FAQItem(R.string.q2,R.string.a2));
        faqs.add(new FAQItemView.FAQItem(R.string.q3,R.string.a3));
        faqs.add(new FAQItemView.FAQItem(R.string.q4,R.string.a4));
        faqs.add(new FAQItemView.FAQItem(R.string.q5,R.string.a5));
        faqs.add(new FAQItemView.FAQItem(R.string.q6,R.string.a6));
        faqs.add(new FAQItemView.FAQItem(R.string.q7,R.string.a7));
        faqs.add(new FAQItemView.FAQItem(R.string.q8,R.string.a8));
        faqs.add(new FAQItemView.FAQItem(R.string.q9,R.string.a9));
        faqs.add(new FAQItemView.FAQItem(R.string.q10,R.string.a10));
        faqs.add(new FAQItemView.FAQItem(R.string.q11,R.string.a11));
        faqs.add(new FAQItemView.FAQItem(R.string.q12,R.string.a12));
        faqs.add(new FAQItemView.FAQItem(R.string.q13,R.string.a13));
        faqs.add(new FAQItemView.FAQItem(R.string.q14,R.string.a14));
        faqs.add(new FAQItemView.FAQItem(R.string.q15,R.string.a15));
        faqs.add(new FAQItemView.FAQItem(R.string.q16,R.string.a16));
        faqs.add(new FAQItemView.FAQItem(R.string.q17,R.string.a17));
        faqs.add(new FAQItemView.FAQItem(R.string.q18,R.string.a18));
        faqs.add(new FAQItemView.FAQItem(R.string.q19,R.string.a19));
        faqs.add(new FAQItemView.FAQItem(R.string.q20,R.string.a20));
        faqs.add(new FAQItemView.FAQItem(R.string.q21,R.string.a21));
        faqs.add(new FAQItemView.FAQItem(R.string.q22,R.string.a22));
        faqs.add(new FAQItemView.FAQItem(R.string.q23,R.string.a23));
        faqs.add(new FAQItemView.FAQItem(R.string.q24,R.string.a24));

        FAQAdapter adapter = new FAQAdapter(faqs);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ViewHolder> {

        private List<FAQItemView.FAQItem> items;

        public FAQAdapter(List<FAQItemView.FAQItem> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(new FAQItemView(FAQActivity.this));
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

            private FAQItemView faqItemView;

            public ViewHolder(View itemView) {
                super(itemView);
                faqItemView = (FAQItemView) itemView;
            }

            public void bind(FAQItemView.FAQItem item){
                faqItemView.setItem(item);
            }

        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(FAQActivity.this));
        }
    }

}
