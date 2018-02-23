package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class NewTicketActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, NewTicketActivity.class));
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.titleEt)
    EditText titleEt;

    @BindView(R.id.bodyEt)
    EditText bodyEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
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

    }
}
