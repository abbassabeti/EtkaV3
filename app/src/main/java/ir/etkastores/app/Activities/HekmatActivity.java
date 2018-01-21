package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class HekmatActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public  static void show(Activity activity){
        Intent intent = new Intent(activity,HekmatActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
