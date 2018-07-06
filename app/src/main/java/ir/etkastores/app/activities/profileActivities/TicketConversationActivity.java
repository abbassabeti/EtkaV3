package ir.etkastores.app.activities.profileActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.models.tickets.TicketItem;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class TicketConversationActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context){
        Intent intent = new Intent(context,TicketConversationActivity.class);
        context.startActivity(intent);
    }

    public static void show(Context context, TicketItem ticketItem){
        Intent intent = new Intent(context,TicketConversationActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_conversation);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
