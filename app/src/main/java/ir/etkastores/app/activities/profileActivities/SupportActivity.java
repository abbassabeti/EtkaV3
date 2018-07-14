package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.viewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.adapters.viewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.fragments.supportFragments.ContactUsFragment;
import ir.etkastores.app.fragments.supportFragments.ProductRequestTicketsListFragment;
import ir.etkastores.app.fragments.supportFragments.SupportTicketsListFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.RTLTabLayout;

public class SupportActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private static String PAGE = "PAGE";

    public static final int TICKET_LIST = 1;
    public static final int CONTACT_US = 2;

    public static void show(Activity activity, int page){
        Intent intent = new Intent(activity,SupportActivity.class);
        intent.putExtra(PAGE,page);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    GlobalFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Support Activity");
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        List<FragmentTitleModel> fragments = new ArrayList<>();
        fragments.add(new FragmentTitleModel(SupportTicketsListFragment.newInstance(),R.string.support));
        fragments.add(new FragmentTitleModel(ProductRequestTicketsListFragment.newInstance(),R.string.productRequest));
        fragments.add(new FragmentTitleModel(ContactUsFragment.newInstance(),R.string.contactUs));
        adapter = new GlobalFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
