package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class NewsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void show(Context context){
        Intent intent = new Intent(context,NewsActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("News Activity");
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
