package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.adapters.viewPagerAdapters.FragmentTitleModel;
import ir.etkastores.app.adapters.viewPagerAdapters.GlobalFragmentPagerAdapter;
import ir.etkastores.app.fragments.supportFragments.ContactUsFragment;
import ir.etkastores.app.fragments.supportFragments.ProductRequestTicketsListFragment;
import ir.etkastores.app.fragments.supportFragments.SupportTicketsListFragment;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.RTLTabLayout;

@Obfuscate
public class SupportActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private static String PAGE = "PAGE";
    private static String TICKET_CODE = "TICKET_CODE";

    public static final int SUPPORT_TICKET = 2;
    public static final int REQUEST_PRODUCT = 1;
    public static final int CONTACT_US = 0;

    public static void show(Activity activity, int page, String ticketCode) {
        Intent intent = new Intent(activity, SupportActivity.class);
        intent.putExtra(PAGE, page);
        intent.putExtra(TICKET_CODE, ticketCode);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tabs)
    RTLTabLayout tabLayout;

    GlobalFragmentPagerAdapter adapter;

    private int selectedPage;
    private String selectedTicketCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ButterKnife.bind(this);
        selectedPage = getIntent().getExtras().getInt(PAGE);
        selectedTicketCode = getIntent().getExtras().getString(TICKET_CODE);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Support Activity");
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        List<FragmentTitleModel> fragments = new ArrayList<>();
        fragments.add(new FragmentTitleModel(SupportTicketsListFragment.newInstance(selectedTicketCode), R.string.ticket));
        fragments.add(new FragmentTitleModel(ProductRequestTicketsListFragment.newInstance(selectedTicketCode), R.string.productRequest));
        fragments.add(new FragmentTitleModel(ContactUsFragment.newInstance(), R.string.contactUs));
        adapter = new GlobalFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(fragments.size());
        tabLayout.setupWithViewPager(pager);
        pager.setCurrentItem(selectedPage);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
