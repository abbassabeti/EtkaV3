package ir.etkastores.app.activities.profileActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.models.factor.FactorModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

public class FactorActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String MODEL = "MODEL";

    public static void show(Context context, FactorModel factorModel) {
        Intent intent = new Intent(context, FactorActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(factorModel));
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private FactorModel factorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor);
        ButterKnife.bind(this);
        factorModel = FactorModel.fromJson(getIntent().getStringExtra(MODEL));
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

}
