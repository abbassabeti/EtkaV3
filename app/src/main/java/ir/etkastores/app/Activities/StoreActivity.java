package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class StoreActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Activity activity){
        Intent intent = new Intent(activity,StoreActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
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
