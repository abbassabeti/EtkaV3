package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.StorePagerSliderView;
import ir.etkastores.app.Utils.IntentHelper;

import android.os.Handler;

public class StoreActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String STORE_KEY = "STORE_KEY";

    public static void show(Activity activity, StoreModel storeModel) {
        Intent intent = new Intent(activity, StoreActivity.class);
        intent.putExtra(STORE_KEY, new Gson().toJson(storeModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.slider)
    StorePagerSliderView slider;

    @BindView(R.id.storeName)
    TextView storeName;

    @BindView(R.id.storeCategory)
    TextView storeCategory;

    @BindView(R.id.storeAddress)
    TextView storeAddress;

    @BindView(R.id.openingTime)
    TextView openingTime;

    @BindView(R.id.mainHolder)
    LinearLayout mainHolder;

    private StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        storeModel = new Gson().fromJson(getIntent().getStringExtra(STORE_KEY), StoreModel.class);

        toolbar.setActionListeners(this);
        fillViews();
    }

    private void fillViews() {
        if (TextUtils.isEmpty(storeModel.getStoreImage())) {
            slider.setVisibility(View.GONE);
        } else {
            slider.setVisibility(View.VISIBLE);
            slider.setSlides(new ArrayList<String>() {{
                add(storeModel.getStoreImage());
            }});
        }
        storeCategory.setText(storeModel.getRanking());
        storeName.setText(storeModel.getName());
        storeAddress.setText(storeModel.getContactInfo().getAddress());
        openingTime.setText(storeModel.getOpeningHours().getOpeningTime());

        if (storeModel.getContactInfo().getPhoneNumbers() != null && storeModel.getContactInfo().getPhoneNumbers().size() > 0) {
            for (String phone : storeModel.getContactInfo().getPhoneNumbers()) {
                addPhone(phone);
            }
        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.btn_share)
    public void onShareButtonClick() {
        String shareBody = String.format(getResources().getString(R.string.etkaStoreBranchXAddressX), storeModel.getName(), IntentHelper.getGoogleMapLocationAddress(storeModel.getLatitude(), storeModel.getLongitude()));
        IntentHelper.share(this, storeModel.getName(), shareBody);
    }

    @OnClick(R.id.btn_traceRouts)
    public void onTraceRoutesButtonClick() {
        IntentHelper.openWayTracer(this, storeModel.getLatitude(), storeModel.getLongitude());
    }

    @OnClick(R.id.btn_inStoreMode)
    public void onInStoreModeClick() {
        Toaster.show(this, R.string.commingSoonMessage);
    }

    private void addPhone(final String phoneNumber) {
        View view = LayoutInflater.from(this).inflate(R.layout.store_phone_item, null, false);
        TextView tv = (TextView) view.findViewById(R.id.phoneNumber);
        tv.setText(phoneNumber);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper.showDialer(StoreActivity.this,phoneNumber);
            }
        });
        mainHolder.addView(view);
    }

}
