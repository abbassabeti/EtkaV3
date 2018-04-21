package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.models.tickets.TicketRequestModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class NewTicketActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static int REQUEST_PRODUCT_TYPE = 0;
    private final static int SUPPORT_TYPE = 1;

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

    private TicketRequestModel ticketRequestModel;

    private int type;

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
        ticketRequestModel = new TicketRequestModel();
        ticketRequestModel.setTitle(titleEt.getText().toString());
    }

    private void submitRequest(){

    }

    private void initTicketTypeSpinner(){
        List<String> items = new ArrayList<>();
        items.add(getResources().getString(R.string.productRequest));
        items.add(getResources().getString(R.string.support));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_view_dark_text_for_spinner, items);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
