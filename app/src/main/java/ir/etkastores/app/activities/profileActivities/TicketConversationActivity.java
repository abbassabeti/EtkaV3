package ir.etkastores.app.activities.profileActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.TicketConversationAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.tickets.TicketItem;
import ir.etkastores.app.models.tickets.TicketRequestModel;
import ir.etkastores.app.ui.dialogs.TicketResponseDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketConversationActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public final static String PRODUCT_REQUEST = "PRODUCT_REQUEST";
    public final static String SUPPORT_REQUEST = "SUPPORT_REQUEST";

    private final static String TICKET_ITEM = "TICKET_ITEM";
    private final static String TYPE = "TYPE";

    public static void show(Context context, TicketItem ticketItem, String type) {
        Intent intent = new Intent(context, TicketConversationActivity.class);
        intent.putExtra(TICKET_ITEM, new Gson().toJson(ticketItem));
        intent.putExtra(TYPE, type);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.replyButton)
    Button replyButton;

    @BindView(R.id.requestDate)
    TextView requestDate;

    private TicketConversationAdapter adapter;

    private TicketItem ticketItem;
    private String type;

    private TicketRequestModel reqModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_conversation);
        ButterKnife.bind(this);
        ticketItem = TicketItem.fromJson(getIntent().getExtras().getString(TICKET_ITEM, ""));
        type = getIntent().getExtras().getString(TYPE);
        toolbar.setActionListeners(this);
        adapter = new TicketConversationAdapter(this);
        recyclerView.setAdapter(adapter);
        if (type.contentEquals(PRODUCT_REQUEST)) {
            initProductRequestList();
        } else if (type.contentEquals(SUPPORT_REQUEST)) {
            initSupportList();
        }
    }

    private void initProductRequestList() {
        toolbar.setTitle(String.format(getResources().getString(R.string.requestCodeX), ticketItem.getId()));
        requestDate.setText(String.format(getResources().getString(R.string.yourRequestInX), ticketItem.getDate()));
        title.setText(ticketItem.getTitle());
        List<TicketItem> items = new ArrayList<>();
        items.add(ticketItem);
        adapter.addItems(items);
        replyButton.setVisibility(View.GONE);
    }

    private void initSupportList() {
        toolbar.setTitle(String.format(getResources().getString(R.string.requestCodeX), ticketItem.getId()));
        title.setText(ticketItem.getTitle());
        Call<OauthResponse<List<TicketItem>>> req = ApiProvider.getAuthorizedApi().getConversation(ticketItem.getTicketCode());
        req.enqueue(new Callback<OauthResponse<List<TicketItem>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<TicketItem>>> call, Response<OauthResponse<List<TicketItem>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {

                    } else {

                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<TicketItem>>> call, Throwable t) {
                if (isFinishing()) return;
            }
        });
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.replyButton)
    public void onReplyButtonClick() {
        reqModel = new TicketRequestModel();
        reqModel.setParentId(ticketItem.getId());
        reqModel.setDepartmentRef(ticketItem.getDepartmentRef());
        reqModel.setTitle(ticketItem.getTitle());
        new TicketResponseDialog().show(getSupportFragmentManager(), new TicketResponseDialog.TicketResponseCallback() {
            @Override
            public void onSendButtonClick(String message) {
                reqModel.setMessage(message);
                sendReply();
            }
        });
    }

    private void sendReply(){
        ApiProvider.getAuthorizedApi().sendSupportTicket(reqModel).enqueue(new Callback<OauthResponse<Long>>() {
            @Override
            public void onResponse(Call<OauthResponse<Long>> call, Response<OauthResponse<Long>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<OauthResponse<Long>> call, Throwable t) {
                if (isFinishing()) return;
            }
        });
    }

}
