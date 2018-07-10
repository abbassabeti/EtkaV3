package ir.etkastores.app.fragments.supportFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.LoginWithSMSActivity;
import ir.etkastores.app.activities.profileActivities.NewTicketActivity;
import ir.etkastores.app.activities.profileActivities.TicketConversationActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.TicketsListAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.tickets.TicketItem;
import ir.etkastores.app.models.tickets.TicketResponseModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 10/17/17.
 */

public class ProductRequestTicketsListFragment extends Fragment implements TicketsListAdapter.OnTicketsListCallbacks, SwipeRefreshLayout.OnRefreshListener {

    public static ProductRequestTicketsListFragment newInstance() {
        return new ProductRequestTicketsListFragment();
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private View view;

    private Call<OauthResponse<TicketResponseModel>> ticketReq;

    private TicketsListAdapter adapter;

    private int reqPage = 1;

    private boolean listIsEmpty = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tickets_list, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        if (getUserVisibleHint()) checkToLoadDataViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ProfileManager.isGuest()) {
            swipeRefresh.setEnabled(false);
            messageView.show(R.drawable.ic_warning_orange_48dp, getResources().getString(R.string.loginRequiredForThisSection), null, null);
            hideLoading();
        } else {
            swipeRefresh.setEnabled(true);
            checkToLoadDataViews();
        }
        EtkaApp.getInstance().screenView("Tickets List Fragment");
    }

    private void initViews() {
        adapter = new TicketsListAdapter(getActivity(), TicketsListAdapter.REQUEST_PRODUCT);
        adapter.setOnTicketsListCallbacks(this);
        recyclerView.setAdapter(adapter);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }

    @OnClick(R.id.addNewTicketFab)
    public void onAddNewTicketButtonClick() {
        if (ProfileManager.isGuest()) {
            showNeedToLogin();
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenNewTicket);
            NewTicketActivity.show(getActivity(), NewTicketActivity.REQUEST_PRODUCT_TYPE);
        }
    }

    @Override
    public void onLoadMore() {
        loadTickets();
    }

    @Override
    public void onTicketListItemClick(TicketItem ticketItem) {
        TicketConversationActivity.show(getActivity(),ticketItem, TicketConversationActivity.PRODUCT_REQUEST);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            checkToLoadDataViews();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ticketReq != null) ticketReq.cancel();
    }

    private void loadTickets() {
        if (!isAdded()) return;
        showLoading();
        messageView.hide();
        ticketReq = ApiProvider.getAuthorizedApi().getProductsTicketList(reqPage);

        ticketReq.enqueue(new Callback<OauthResponse<TicketResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<TicketResponseModel>> call, Response<OauthResponse<TicketResponseModel>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        if (response.body().getData().getTotalItemsCount() == 0) {
                            messageView.show(R.drawable.ic_warning_orange_48dp, getResources().getString(R.string.yourTicketsListIsEmpty), null, null);
                            listIsEmpty = true;
                        } else {
                            listIsEmpty = false;
                        }
                        adapter.addItems(response.body().getData().getItems());
                        if (response.body().getData().getTotalItemsCount() > adapter.getItemCount()) {
                            adapter.setLoadMoreEnabled(true);
                            reqPage++;
                        }
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<TicketResponseModel>> call, Throwable throwable) {
                if (!isAdded()) return;
                hideLoading();
                String message = getResources().getString(R.string.errorInReceivingTicketsListData);
                if (adapter.getItemCount() == 0) {
                    showRetryMessageView(message);
                } else {
                    showRetryDialog(message);
                }
            }
        });
    }

    private void showLoading() {
        messageView.hide();
        if (adapter.getItemCount() == 0) circularProgress.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
        linearProgress.setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }

    private void showRetryDialog(String message) {
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error), message);
        messageDialog.show(getChildFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (!isAdded()) return;
                if (button == RIGHT_BUTTON) {
                    loadTickets();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showRetryMessageView(final String message) {
        messageView.show(R.drawable.ic_warning_orange_48dp, message, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                if (!isAdded()) return;
                messageView.hide();
                loadTickets();
            }
        });
    }

    private void checkToLoadDataViews() {
        if (adapter.getItemCount() == 0 && !listIsEmpty && !ProfileManager.isGuest() && (ticketReq == null || ticketReq.isCanceled())) {
            loadTickets();
        }
    }

    @Override
    public void onRefresh() {
        reqPage = 1;
        adapter.clear();
        loadTickets();
    }

    private void showNeedToLogin() {
        final MessageDialog messageDialog = MessageDialog.loginRequired();
        messageDialog.show(getChildFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (!isAdded()) return;
                if (button == RIGHT_BUTTON) {
                    LoginWithSMSActivity.show(getActivity());
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
