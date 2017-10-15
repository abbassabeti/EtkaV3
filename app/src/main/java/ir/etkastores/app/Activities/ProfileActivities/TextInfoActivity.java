package ir.etkastores.app.Activities.ProfileActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class TextInfoActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String TYPE_KEY = "TYPE_KEY";

    private final static int ABOUT_ETKA_STORES = 1;
    private final static int USER_PRIVACY = 2;
    private final static int TERM_AND_CONDITIONS = 3;

    public static void startAboutEtkaStores(AppCompatActivity activity){
        start(activity,ABOUT_ETKA_STORES);
    }

    public static void startUserPrivacy(AppCompatActivity activity){
        start(activity,USER_PRIVACY);
    }

    public static void startTermAndConditions(AppCompatActivity activity){
        start(activity,TERM_AND_CONDITIONS);
    }

    private static void start(AppCompatActivity activity,int type){
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
                break;

            case USER_PRIVACY:
                title = getString(R.string.userPrivacy);
                text = getString(R.string.userPrivacyText);
                break;

            case TERM_AND_CONDITIONS:
                title = getString(R.string.termAndConditions);
                text = getString(R.string.termAndConditionsText);
                break;

        }

        toolbar.setActionListeners(this);
        toolbar.setTitle(title);
        textView.setText(text);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
