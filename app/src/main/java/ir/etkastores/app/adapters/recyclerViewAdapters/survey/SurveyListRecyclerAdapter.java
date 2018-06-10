package ir.etkastores.app.adapters.recyclerViewAdapters.survey;

import android.content.Context;
import android.support.annotation.NonNull;
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
import ir.etkastores.app.models.survey.SurveyModel;

public class SurveyListRecyclerAdapter extends RecyclerView.Adapter<SurveyListRecyclerAdapter.ViewHolder> {

    private List<SurveyModel> items;
    private Context context;
    private LayoutInflater inflater;
    private OnSurveyClickListener onSurveyClickListener;

    public SurveyListRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.cell_survey_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<SurveyModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnSurveyClickListener(OnSurveyClickListener onSurveyClickListener) {
        this.onSurveyClickListener = onSurveyClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.surveyTitle)
        TextView title;

        @BindView(R.id.surveyId)
        TextView id;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSurveyClickListener != null) onSurveyClickListener.onSurveyClick(items.get(getAdapterPosition()));
                }
            });
        }

        public void bind(SurveyModel item) {
            title.setText(item.getTitle());
            id.setText(String.format(context.getResources().getString(R.string.surveyIdX),item.getId()));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public interface OnSurveyClickListener{
        void onSurveyClick(SurveyModel surveyModel);
    }
}
