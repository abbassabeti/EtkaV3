package ir.etkastores.app.activities.profileActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.FactorListRecyclerAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

@Obfuscate
public class FactorActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String MODEL = "MODEL";

    public static void show(Context context, FactorModel factorModel) {
        Intent intent = new Intent(context, FactorActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(factorModel));
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private FactorModel factorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_factor);
        ButterKnife.bind(this);
        factorModel = FactorModel.fromJson(getIntent().getStringExtra(MODEL));
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Factor List Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        FactorListRecyclerAdapter adapter = new FactorListRecyclerAdapter(this, factorModel);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
