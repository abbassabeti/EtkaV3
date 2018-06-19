package ir.etkastores.app.adapters.recyclerViewAdapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.models.tickets.TicketItem;
import ir.etkastores.app.models.tickets.TicketStatus;
import ir.etkastores.app.utils.FontUtils;

/**
 * Created by garshasbi on 4/3/18.
 */

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.TicketViewHolder> {

    public final static int REQUEST_PRODUCT = 1;
    public final static int SUPPORT = 2;

    private Context context;
    private LayoutInflater inflater;
    private List<TicketItem> items;
    private boolean isLoadMoreEnabled = false;
    private OnTicketsListCallbacks onTicketsListCallbacks;
    private int type;

    public TicketsListAdapter(Context context, int type) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = new ArrayList<>();
        this.type = type;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TicketViewHolder(inflater.inflate(R.layout.cell_ticket_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(TicketViewHolder ticketViewHolder, int i) {

        ticketViewHolder.bind(items.get(i));

        if (isLoadMoreEnabled && onTicketsListCallbacks != null && items.size() - 1 == i) {
            isLoadMoreEnabled = false;
            onTicketsListCallbacks.onLoadMore();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<TicketItem> newItems){
        int startPosition = items.size();
        items.addAll(newItems);
        notifyItemRangeInserted(startPosition,newItems.size());
    }

    public void setItems(List<TicketItem> newItems){
        items = newItems;
        notifyDataSetChanged();
    }

    public boolean isLoadMoreEnabled() {
        return isLoadMoreEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        isLoadMoreEnabled = loadMoreEnabled;
    }

    public void setOnTicketsListCallbacks(OnTicketsListCallbacks onTicketsListCallbacks) {
        this.onTicketsListCallbacks = onTicketsListCallbacks;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public interface OnTicketsListCallbacks {
        void onLoadMore();

        void onTicketListItemClick(TicketItem ticketItem);
    }

    public void clear(){
        items = new ArrayList<>();
        notifyDataSetChanged();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ticketSubject)
        TextView subject;

        @BindView(R.id.ticketTitle)
        TextView title;

        @BindView(R.id.ticketDate)
        TextView date;

        @BindView(R.id.ticketStatus)
        TextView status;

        @BindView(R.id.ticketLeftIcon)
        AppCompatImageView leftIcon;

        @BindView(R.id.ticketTitleHolder)
        RelativeLayout ticketTitleHolder;

        @BindView(R.id.ticketStatusHolder)
        LinearLayout ticketStatusHolder;

        public TicketViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTicketsListCallbacks != null)
                        onTicketsListCallbacks.onTicketListItemClick(items.get(getAdapterPosition()));
                }
            });
            title.setTypeface(FontUtils.getBoldTypeFace());
        }

        public void bind(TicketItem ticketItem) {
            title.setText(ticketItem.getTitle());
            date.setText(ticketItem.getDate());
            status.setTextColor(ticketItem.getStatusTextColor());
            status.setText(TicketStatus.getDisplayValueOfStatus(ticketItem.getStatus()));
        }

    }

}
