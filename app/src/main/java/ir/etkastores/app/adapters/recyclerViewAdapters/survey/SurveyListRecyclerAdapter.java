package ir.etkastores.app.adapters.recyclerViewAdapters.survey;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.survey.QuestionModel;

/**
 * Created by garshasbi on 4/27/18.
 */

public class SurveyListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QuestionModel> items;
    private Context context;
    private LayoutInflater inflater;

    public SurveyListRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new QuestionViewHolder(inflater.inflate(R.layout.cell_survey_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((QuestionViewHolder) viewHolder).bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<QuestionModel> items) {
        this.items.addAll(items);
        notifyItemInserted(items.size());
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.questionTv)
        TextView question;

        @BindView(R.id.answersHolder)
        LinearLayout answersHolder;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(QuestionModel q) {
            question.setText(q.getText());
        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
