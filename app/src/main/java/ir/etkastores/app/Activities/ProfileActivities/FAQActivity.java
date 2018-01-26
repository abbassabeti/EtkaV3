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
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("مقدار کسری این پروژه چقدر است؟","۲۱ ماه!")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("چه کسانی با این پروژه معاف می شوند؟","سجاد گرشاسبی، هادی محمدی، آرمین رصدی، پیام شکیبافر، هژیر مفاخری، ....")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("کالای حکمت چیست؟","چیز خوبیه!")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("امتیاز باشگاه مشتریان چیست؟","اونم چیز خوبیه ؛)")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("آیا امکان سفارش آنلاین وجود دارد؟","نه")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("آیا تبدیل امتیاز به اعتبار آنی است؟","تقریبا")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("لیست خرید بعدی چیست؟","چیز خوبیه...")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("تخفیف ها شامل چه کالاهایی می شود؟","همه چیز")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("اتکا چند تا فروشگاه داره؟","زیاد")));
        faqHolder.addView(new FAQItemView(this,new FAQItemView.FAQItem("نقشه درون فروشگاه چیست؟","نمی دونم!")));
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
