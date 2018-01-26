package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.FAQItemView;

public class FAQActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener{

    public static void start(Activity activity){
        Intent intent = new Intent(activity,FAQActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.faqHolder)
    LinearLayout faqHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);

        for (int x=0 ; x< 10 ; x++){
            faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("عنوان سوال؟!"+x,"متن جواب سوال، متن جواب سوال، متن جواب سوال، متن جواب سوال، متن جواب سوال، متن جواب سوال، متن جواب سوال")));
        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
