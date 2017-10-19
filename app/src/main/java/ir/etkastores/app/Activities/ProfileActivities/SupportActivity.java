package ir.etkastores.app.Activities.ProfileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.BaseActivity;
import ir.etkastores.app.Adapters.ViewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.Adapters.ViewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.Fragments.SupportFragments.ContactUsFramgment;
import ir.etkastores.app.Fragments.SupportFragments.RequestsFragment;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.RTLTabLayout;

public class SupportActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,SupportActivity.class);
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
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        List<FragmentTitleModel> fragments = new ArrayList<>();
        fragments.add(new FragmentTitleModel(RequestsFragment.newInstance(),R.string.requests));
        fragments.add(new FragmentTitleModel(ContactUsFramgment.newInstance(),R.string.contactUs));
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
