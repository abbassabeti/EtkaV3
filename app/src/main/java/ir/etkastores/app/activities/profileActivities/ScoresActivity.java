package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.data.ProfileManager;

@Obfuscate
public class ScoresActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,ScoresActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.yourRate)
    TextView yourRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.getInstance().isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_scores);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Scores Activity");
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        yourRate.setText(String.format(getResources().getString(R.string.youHaveXScore), ProfileManager.getInstance().getProfile().getTotalPoints()));
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
