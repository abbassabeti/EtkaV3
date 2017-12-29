package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class StoreActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String STORE_KEY = "STORE_KEY";

    public static void show(Activity activity, StoreModel storeModel){
        Intent intent = new Intent(activity,StoreActivity.class);
        intent.putExtra(STORE_KEY,new Gson().toJson(storeModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        storeModel = new Gson().fromJson(getIntent().getStringExtra(STORE_KEY),StoreModel.class);

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
