package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.StoresListActivity;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.models.tickets.TicketRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTicketActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static int NOT_SELECTED = 0;
    private final static int REQUEST_PRODUCT_TYPE = 1;
    private final static int SUPPORT_TYPE = 2;

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, NewTicketActivity.class));
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.titleEt)
    EditText titleEt;

    @BindView(R.id.bodyEt)
    EditText bodyEt;

    @BindView(R.id.ticketTypeSelectSpinner)
    AppCompatSpinner typeSpinner;

    @BindView(R.id.selectStoreHolder)
    View selectStoreButton;

    @BindView(R.id.selectedStore)
    TextView selectedStoreTv;

    private TicketRequestModel ticketRequestModel;
    private int type;
    private StoreModel selectedStore;
    private AlertDialog loadingDialog;
    private Call<OauthResponse<Long>> sendTicketReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("New Ticket Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        initTicketTypeSpinner();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.submitButton)
    public void onSubmitButtonClicked() {
        if (type == NOT_SELECTED) {
            Toaster.showLong(this, R.string.selectTicketType);
            return;
        }

        if (TextUtils.isEmpty(titleEt.getText().toString())) {
            Toaster.showLong(this, R.string.titleCantBeEmpty);
            return;
        }

        if (type == REQUEST_PRODUCT_TYPE && selectedStore == null) {
            Toaster.showLong(this, R.string.pleaseSelectYourTargetStore);
            return;
        }

        if (TextUtils.isEmpty(bodyEt.getText().toString())) {
            Toaster.showLong(this, R.string.textBodyCantBeEmpty);
            return;
        }

        ticketRequestModel = new TicketRequestModel();
        ticketRequestModel.setTitle(titleEt.getText().toString());
        if (type == REQUEST_PRODUCT_TYPE){
            ticketRequestModel.setStoreRef(selectedStore.getId());
        }
        ticketRequestModel.setMessage(bodyEt.getText().toString());
        String ticketType = TicketRequestModel.TicketType.ProductRequest;
        if (type == SUPPORT_TYPE) ticketType = TicketRequestModel.TicketType.Support;
        ticketRequestModel.setTicketType(ticketType);
        submitRequest();
    }

    private void submitRequest() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inSendingRequest);
        sendTicketReq = ApiProvider.getAuthorizedApi().sendTicket(ticketRequestModel);
        sendTicketReq.enqueue(new Callback<OauthResponse<Long>>() {
            @Override
            public void onResponse(Call<OauthResponse<Long>> call, Response<OauthResponse<Long>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.showLong(NewTicketActivity.this,R.string.ticketSendSuccessfully);
                        finish();
                    } else {
                        showErrorDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<Long>> call, Throwable throwable) {
                if (isFinishing() || sendTicketReq.isCanceled()) return;
                loadingDialog.cancel();
                showErrorDialog(getResources().getString(R.string.errorInSendingRequest));
            }
        });
    }

    private void showErrorDialog(final String message) {
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error),message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    submitRequest();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void initTicketTypeSpinner() {
        List<String> items = new ArrayList<>();
        items.add(getResources().getString(R.string.ticketType));
        items.add(getResources().getString(R.string.productRequest));
        items.add(getResources().getString(R.string.support));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_view_dark_text_for_spinner, items);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                if (type == REQUEST_PRODUCT_TYPE) {
                    selectStoreButton.setVisibility(View.VISIBLE);
                } else {
                    selectStoreButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.selectStoreHolder)
    public void onSelectStore() {
        StoresListActivity.showForSelectStore(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == StoresListActivity.SELECT_STORE_REQ_CODE) {
            selectedStore = StoreModel.fromJson(data.getStringExtra(StoresListActivity.SELECTED_STORE));
            if (selectedStore != null) {
                selectedStoreTv.setText(selectedStore.getName());
            }
        }
    }

}
