package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.hekmat.HekmatModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class HekmatProductsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String HEKMAT_PRODUCT = "HEKMAT_PRODUCT";

    public  static void show(Activity activity, HekmatModel hekmatModel){
        Intent intent = new Intent(activity,HekmatProductsActivity.class);
        intent.putExtra(HEKMAT_PRODUCT,new Gson().toJson(hekmatModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private HekmatModel hekmatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat_product);
        ButterKnife.bind(this);
        hekmatModel = new Gson().fromJson(getIntent().getExtras().getString(HEKMAT_PRODUCT),HekmatModel.class);
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        toolbar.setTitle(hekmatModel.getTitle());
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
