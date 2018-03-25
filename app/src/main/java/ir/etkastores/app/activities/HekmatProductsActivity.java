package ir.etkastores.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatProductSecondLevelRecyclerAdapter;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.models.hekmat.HekmatProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class HekmatProductsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, HekmatProductSecondLevelRecyclerAdapter.OnHekmatProductClickListener {

    private final static String HEKMAT_PRODUCT = "HEKMAT_PRODUCT";

    public static void show(Activity activity, HekmatModel hekmatModel){
        Intent intent = new Intent(activity,HekmatProductsActivity.class);
        intent.putExtra(HEKMAT_PRODUCT,new Gson().toJson(hekmatModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HekmatModel hekmatModel;
    private HekmatProductSecondLevelRecyclerAdapter adapter;

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
        adapter = new HekmatProductSecondLevelRecyclerAdapter(hekmatModel.getProducts(),this);
        adapter.setOnHekmatProductClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    public void onHekmatProductClick(HekmatProductModel productModel) {
        StoresListActivity.showForOpenStore(this,productModel);
    }

}
