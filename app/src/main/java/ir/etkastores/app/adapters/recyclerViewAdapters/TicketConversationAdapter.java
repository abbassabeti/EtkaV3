package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.tickets.TicketItem;

public class TicketConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TicketItem> items;
    private Context context;
    private LayoutInflater inflater;

    public TicketConversationAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversationViewHolder(inflater.inflate(R.layout.cell_ticket_conversation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ConversationViewHolder) holder).bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<TicketItem> newItems) {
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.senderName)
        TextView senderName;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.body)
        TextView body;

        @BindView(R.id.holder)
        RelativeLayout holder;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TicketItem item) {
            if (item.isUserTicket()) {
                senderName.setText(itemView.getResources().getString(R.string.sendByYou));
                holder.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            } else {
                senderName.setText(itemView.getResources().getString(R.string.etkaReply));
                holder.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.lightYellow));
            }
            date.setText(item.getDate());
            if (item.getAnswer() != null) {
                body.setText(item.getAnswer().getMessage().trim());
            }else if (!TextUtils.isEmpty(item.getMessage())){
                body.setText(item.getMessage().trim());
            }
        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

}
