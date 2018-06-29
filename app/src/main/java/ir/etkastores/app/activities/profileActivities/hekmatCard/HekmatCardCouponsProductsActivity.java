package ir.etkastores.app.activities.profileActivities.hekmatCard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatCouponsAdapter;
import ir.etkastores.app.models.hekmat.card.HekmatRemainingsModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class HekmatCardCouponsProductsActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private static final String MODEL = "MODEL";

    public static void show(Context context, HekmatRemainingsModel model) {
        Intent intent = new Intent(context, HekmatCardCouponsProductsActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(model));
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HekmatRemainingsModel model;
    private HekmatCouponsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hekmat_card_coupons_products);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        model = HekmatRemainingsModel.fromJSon(getIntent().getStringExtra(MODEL));
        adapter = new HekmatCouponsAdapter(this, model.getCoupons());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Hekmat Card Coupons List Activity");
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
