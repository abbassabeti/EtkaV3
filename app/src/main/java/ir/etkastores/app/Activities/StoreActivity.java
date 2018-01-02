package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.StorePagerSliderView;
import android.os.Handler;

public class StoreActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String STORE_KEY = "STORE_KEY";

    public static void show(Activity activity, StoreModel storeModel){
        Intent intent = new Intent(activity,StoreActivity.class);
        intent.putExtra(STORE_KEY,new Gson().toJson(storeModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.slider)
    StorePagerSliderView slider;

    @BindView(R.id.storeName)
    TextView storeName;

    @BindView(R.id.storeAddress)
    TextView storeAddress;

    @BindView(R.id.openingTime)
    TextView openingTime;

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    private StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        storeModel = new Gson().fromJson(getIntent().getStringExtra(STORE_KEY),StoreModel.class);

        toolbar.setActionListeners(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fillViews();
            }
        },300);
    }

    private void fillViews(){
        slider.setSlides(new ArrayList<String>(){{add(storeModel.getStoreImage());}});
        storeName.setText(storeModel.getName());
        storeAddress.setText(storeModel.getContactInfo().getAddress());
        openingTime.setText(storeModel.getOpeningHours().getMorningStart());
        if (storeModel.getContactInfo().getPhoneNumbers() != null && storeModel.getContactInfo().getPhoneNumbers().size()>0){
            phoneNumber.setText(storeModel.getContactInfo().getPhoneNumbers().get(0));
        }else{

        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick({R.id.btn_contactToBranch,R.id.btn_phoneNumberRow})
    public void onContactToBranchButtonClick(){

    }

    @OnClick(R.id.btn_share)
    public void onShareButtonClick(){

    }

    @OnClick(R.id.btn_traceRouts)
    public void onTraceRoutesButtonClick(){

    }

}
