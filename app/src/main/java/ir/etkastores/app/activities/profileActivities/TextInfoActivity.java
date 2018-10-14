package ir.etkastores.app.activities.profileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;

@Obfuscate
public class TextInfoActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String TYPE_KEY = "TYPE_KEY";

    private final static int ABOUT_ETKA_STORES = 1;
    private final static int USER_PRIVACY = 2;
    private final static int TERM_AND_CONDITIONS = 3;

    public static void showAboutEtkaStores(AppCompatActivity activity){
        show(activity,ABOUT_ETKA_STORES);
    }

    public static void showUserPrivacy(AppCompatActivity activity){
        show(activity,USER_PRIVACY);
    }

    public static void showTermAndConditions(AppCompatActivity activity){
        show(activity,TERM_AND_CONDITIONS);
    }

    private static void show(AppCompatActivity activity, int type){
        Intent intent = new Intent(activity,TextInfoActivity.class);
        intent.putExtra(TYPE_KEY,type);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;


    @BindView(R.id.textInfo)
    TextView textView;

    private int type;
    private String text;
    private String title;
    private String screenName = "Unknown Info Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_info);
        ButterKnife.bind(this);
        type = getIntent().getExtras().getInt(TYPE_KEY);

        switch (type){

            case ABOUT_ETKA_STORES:
                title = getString(R.string.aboutEtkaStores);
                text = getString(R.string.aboutEtkaStoresText);
                screenName = "About Etka Stores Activity";
                break;

            case USER_PRIVACY:
                title = getString(R.string.userPrivacy);
                text = getString(R.string.userPrivacyText);
                screenName = "User Privacy Activity";
                break;

            case TERM_AND_CONDITIONS:
                title = getString(R.string.termAndConditions);
                text = getString(R.string.termAndConditionsText);
                screenName = "Rules Activity";
                break;

        }

        toolbar.setActionListeners(this);
        toolbar.setTitle(title);
        textView.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView(screenName);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
