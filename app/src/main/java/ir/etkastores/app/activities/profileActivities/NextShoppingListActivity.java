package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;

public class NextShoppingListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,NextShoppingListActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.messageView)
    MessageView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_shopping_list);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    private void initViews(){
        showEmptyMessage();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void showEmptyMessage(){
        messageView.show(R.drawable.ic_warning_orange_48dp,R.string.commingSoonMessage,0,null);
    }

}
