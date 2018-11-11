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
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.activities.StoresListActivity;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.data.TicketsDepartmentsManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.models.tickets.DepartmentModel;
import ir.etkastores.app.models.tickets.TicketRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class NewTicketActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String TYPE = "TYPE";
    public final static int REQUEST_PRODUCT_TYPE = 1;
    public final static int SUPPORT_TYPE = 2;

    public static void show(Activity activity, int type) {
        Intent intent = new Intent(activity, NewTicketActivity.class);
        intent.putExtra(TYPE, type);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.titleEt)
    EditText titleEt;

    @BindView(R.id.bodyEt)
    EditText bodyEt;

    @BindView(R.id.ticketDepartmentSelectSpinner)
    AppCompatSpinner departmentSpinner;

    @BindView(R.id.selectStoreHolder)
    View selectStoreButton;

    @BindView(R.id.selectDepartmentHolder)
    View departmentHolder;

    @BindView(R.id.selectedStore)
    TextView selectedStoreTv;

    private TicketRequestModel reqModel;
    private int type;
    private StoreModel selectedStore;
    private AlertDialog loadingDialog;
    private Call<OauthResponse<Long>> req;
    private DepartmentModel selectedDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.getInstance().isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_new_ticket);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getInt(TYPE);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("New Ticket Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        if (type == REQUEST_PRODUCT_TYPE) {
            initForProductRequest();
        } else {
            initForSupport();
        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void initForProductRequest() {
        toolbar.setTitle(R.string.productRequest);
        selectStoreButton.setVisibility(View.VISIBLE);
        departmentHolder.setVisibility(View.GONE);

    }

    private void initForSupport() {
        toolbar.setTitle(R.string.support);
        selectStoreButton.setVisibility(View.GONE);
        departmentHolder.setVisibility(View.VISIBLE);
        initDepartments();

    }

    @OnClick(R.id.submitButton)
    public void onSubmitButtonClicked() {

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

        if (type == SUPPORT_TYPE && selectedDepartment == null) {
            Toaster.showLong(this, R.string.pleaseSelectDepartment);
            return;
        }

        reqModel = new TicketRequestModel();

        if (type == SUPPORT_TYPE) {
            reqModel = new TicketRequestModel(titleEt.getText().toString(), bodyEt.getText().toString(), selectedDepartment.getId());
        } else {
            reqModel = new TicketRequestModel(titleEt.getText().toString(), bodyEt.getText().toString(), selectedStore.getId());
        }

        if (type == REQUEST_PRODUCT_TYPE) {
            sendRequestProductTicket();
        } else {
            sendSupportTicket();
        }
    }

    private void showErrorDialog(final String message, boolean showRetry) {
        String retry = getResources().getString(R.string.retry);
        if (!showRetry) retry = null;
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                getResources().getString(R.string.error),
                message,
                retry,
                getResources().getString(R.string.close));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    if (type == REQUEST_PRODUCT_TYPE) {
                        sendRequestProductTicket();
                    } else {
                        sendSupportTicket();
                    }
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void initDepartmentSpinner(final List<DepartmentModel> departments) {
        List<String> items = new ArrayList<>();
        items.add(getResources().getString(R.string.selectDepartment));
        for (DepartmentModel d : departments) {
            items.add(d.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_view_dark_text_for_spinner, items);
        departmentSpinner.setAdapter(adapter);
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedDepartment = null;
                } else {
                    selectedDepartment = departments.get(position - 1);
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
                selectedStoreTv.setText(getResources().getString(R.string.store) + ": " + selectedStore.getName());
            }
        }
    }

    private void initDepartments() {
        TicketsDepartmentsManager.getInstance().fetchDepartments(new TicketsDepartmentsManager.OnDepartmentCallback() {
            @Override
            public void onDepartmentsFetched(List<DepartmentModel> departments) {
                if (isFinishing()) return;
                initDepartmentSpinner(departments);
            }

            @Override
            public void onDepartmentsFailure(String message) {
                if (isFinishing()) return;
            }
        });
    }

    private void sendRequestProductTicket() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inSendingRequest);
        req = ApiProvider.getInstance().getAuthorizedApi().sendRequestProduct(reqModel);
        req.enqueue(reqCallback);
    }

    private void sendSupportTicket() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inSendingRequest);
        req = ApiProvider.getInstance().getAuthorizedApi().sendSupportTicket(reqModel);
        req.enqueue(reqCallback);
    }

    Callback<OauthResponse<Long>> reqCallback = new Callback<OauthResponse<Long>>() {
        @Override
        public void onResponse(Call<OauthResponse<Long>> call, Response<OauthResponse<Long>> response) {
            if (isFinishing()) return;
            if (response.isSuccessful()) {
                if (response.body().isSuccessful()) {
                    Toaster.show(NewTicketActivity.this, R.string.ticketSendSuccessfully);
                    finish();
                } else {
                    showErrorDialog(response.body().getMeta().getMessage(), false);
                }
            } else {
                onFailure(call, null);
            }
            loadingDialog.cancel();
        }

        @Override
        public void onFailure(Call<OauthResponse<Long>> call, Throwable t) {
            if (isFinishing()) return;
            loadingDialog.cancel();
            showErrorDialog(getResources().getString(R.string.errorInSendingRequest), true);
        }
    };

}
