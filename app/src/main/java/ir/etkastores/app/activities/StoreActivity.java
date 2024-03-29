package ir.etkastores.app.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.profileActivities.NextShoppingListActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatHorizontalRecyclerListAdapter;
import ir.etkastores.app.data.HekmatProductsManager;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.data.StaticsData;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.models.GalleryItemsModel;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.models.store.FeatureModel;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.StorePagerSliderView;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.utils.FontUtils;
import ir.etkastores.app.utils.IntentHelper;
import ir.etkastores.app.utils.image.ImageLoader;

@Obfuscate
public class StoreActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, StoresManager.StoresCallback, StorePagerSliderView.OnStoreImageClickListener {

    private final static String STORE_KEY = "STORE_KEY";
    private final static String STORE_CODE_KEY = "STORE_CODE_KEY";

    public static void show(Activity activity, StoreModel storeModel) {
        Intent intent = new Intent(activity, StoreActivity.class);
        intent.putExtra(STORE_KEY, new Gson().toJson(storeModel));
        activity.startActivity(intent);
    }

    public static void show(Activity activity, String storeCode) {
        Intent intent = new Intent(activity, StoreActivity.class);
        intent.putExtra(STORE_CODE_KEY, storeCode);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.slider)
    StorePagerSliderView slider;

    @BindView(R.id.managerPhoto)
    AppCompatImageView managerPhoto;

    @BindView(R.id.managerName)
    TextView managerName;

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

    @BindView(R.id.managerInfoHolder)
    View managerInfoHolder;

    @BindView(R.id.hekmatRecyclerView)
    RecyclerView hekmatRecyclerView;

    @BindView(R.id.hekmatItemsHolder)
    View hekmatItemsHolder;

    @BindView(R.id.extraItemHolder)
    LinearLayout extraItemHolder;

    @BindView(R.id.inStoreModeButton)
    View inStoreModeButton;

    private StoreModel storeModel;

    private String storeCode = null;

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        storeModel = new Gson().fromJson(getIntent().getStringExtra(STORE_KEY), StoreModel.class);

        if (storeModel == null) {
            storeCode = getIntent().getStringExtra(STORE_CODE_KEY);
        }

        toolbar.setActionListeners(this);

        String url = null;
        try {
            Uri uri = this.getIntent().getData();
            url = uri.toString();
        } catch (Exception err) {
            err.printStackTrace();
        }

        if (storeModel != null) {
            fillViews();
        } else if (!TextUtils.isEmpty(url)) {
            url = url.replace(StaticsData.etkaStoreScheme, "");
            url = url.trim();
            try {
                storeCode = url;
                loadStore();
            } catch (Exception err) {
                finish();
            }
        } else if (!TextUtils.isEmpty(storeCode)) {
            loadStore();
        } else {
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Store Activity");
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

        if (storeModel.isHasInStoreOffer() || storeModel.isHasInStoreSurvey() || BuildConfig.DEBUG) {
            inStoreModeButton.setVisibility(View.VISIBLE);
        } else {
            inStoreModeButton.setVisibility(View.GONE);
        }

        storeCategory.setText(storeModel.getRanking());
        storeName.setText(storeModel.getName());
        storeAddress.setText(storeModel.getContactInfo().getAddress());

        openingTime.setText(storeModel.getOpeningHours().getOpeningTime());

        if (!TextUtils.isEmpty(storeModel.getManagerName())) {
            managerName.setText(String.format(getResources().getString(R.string.managementX), storeModel.getManagerName()));
        }

        if (!TextUtils.isEmpty(storeModel.getManagerImage())) {
            ImageLoader.loadImage(this, managerPhoto, storeModel.getManagerImage());
        } else {
            managerPhoto.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(storeModel.getManagerImage()) && TextUtils.isEmpty(storeModel.getManagerName())) {
            managerInfoHolder.setVisibility(View.GONE);
        }

        if (storeModel.getContactInfo().getPhoneNumbers() != null && storeModel.getContactInfo().getPhoneNumbers().size() > 0) {
            for (String phone : storeModel.getContactInfo().getPhoneNumbers()) {
                addPhone(phone);
            }
        }

        if (storeModel.getFeatures() != null && storeModel.getFeatures().size() > 0) {
            TextView tv = new TextView(this);
            tv.setText(R.string.featuresList);
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.RIGHT);
            int sidePadding = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
            int topBottomPadding = (int) (8 * Resources.getSystem().getDisplayMetrics().density);
            tv.setPadding(sidePadding, topBottomPadding, sidePadding, topBottomPadding);
            tv.setTextColor(ContextCompat.getColor(this, R.color.darkGray));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            tv.setTypeface(FontUtils.getBoldTypeFace());
            extraItemHolder.addView(tv);
            for (FeatureModel featureModel : storeModel.getFeatures()) {
                addFeature(featureModel);
            }
        }

        HekmatProductsManager.getInstance().getProducts(new HekmatProductsManager.OnHekmatCallbacksListener() {

            @Override
            public void onProductsLoaded(List<HekmatModel> products) {
                if (isFinishing()) return;
                if (products == null || products.size() == 0) return;
                hekmatItemsHolder.setVisibility(View.VISIBLE);
                HekmatHorizontalRecyclerListAdapter adapter = new HekmatHorizontalRecyclerListAdapter(StoreActivity.this, new HekmatHorizontalRecyclerListAdapter.OnItemClickListener() {
                    @Override
                    public void OnHekmatItemClick(HekmatModel hekmatModel) {
                        if (isFinishing()) return;
                        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenHekmatFromStore);
                        HekmatProductsActivity.show(StoreActivity.this, hekmatModel);
                    }
                });
                adapter.addItems(products);
                hekmatRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onLoadFailure() {

            }

        }, Long.valueOf(storeModel.getId()));

        slider.setOnStoreImageClickListener(this);

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
        AdjustHelper.sendAdjustEvent(AdjustHelper.ShareStore);
        String shareBody = String.format(getResources().getString(R.string.etkaStoreBranchXAddressX), storeModel.getName(), IntentHelper.getGoogleMapLocationAddress(storeModel.getLatitude(), storeModel.getLongitude()));
        IntentHelper.share(this, storeModel.getName(), shareBody);
    }

    @OnClick(R.id.btn_traceRouts)
    public void onTraceRoutesButtonClick() {
        AdjustHelper.sendAdjustEvent(AdjustHelper.TraceRoutesStores);
        IntentHelper.openWayTracer(this, storeModel.getLatitude(), storeModel.getLongitude(), storeModel.getName());
    }

    @OnClick(R.id.btn_inStoreMap)
    public void onInStoreMapClick() {
        if (storeModel.isHasInStoreMap()) {
            AdjustHelper.sendAdjustEvent(AdjustHelper.InStoreMode);
            InStoreMapActivity.show(this, storeModel);
        } else {
            Toaster.show(this, R.string.thisStoreNotSupportInStoreModeYet);
        }
    }

    @OnClick(R.id.inStoreModeButton)
    public void onInStoreModeButtonClick() {
        if (storeModel.isHasInStoreSurvey() && !storeModel.isHasInStoreMap() && !storeModel.isHasInStoreOffer()) {
            LoginWithSMSActivity.show(this);
            return;
        }
        InStoreModeActivity.show(this, storeModel);
    }

    @OnClick(R.id.nextShoppingListButton)
    public void onNextShoppingListButtonClick() {
        if (ProfileManager.getInstance().isGuest()) {
            LoginWithSMSActivity.show(this);
            return;
        }
        NextShoppingListActivity.show(this);
    }

    private void addPhone(final String phoneNumber) {
        View view = LayoutInflater.from(this).inflate(R.layout.store_phone_item, null, false);
        TextView tv = (TextView) view.findViewById(R.id.phoneNumber);
        tv.setText(phoneNumber);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdjustHelper.sendAdjustEvent(AdjustHelper.CallPhoneStore);
                IntentHelper.showDialer(StoreActivity.this, phoneNumber);
            }
        });
        extraItemHolder.addView(view);
    }

    private void addFeature(FeatureModel featureModel) {
        View view = LayoutInflater.from(this).inflate(R.layout.store_feature_item, null, false);
        TextView tv = (TextView) view.findViewById(R.id.featureTitle);
        tv.setText(featureModel.getName() + " : " + featureModel.getValue());
        extraItemHolder.addView(view);
    }

    private void loadStore() {
        showLoading();
        StoresManager.getInstance().fetchStores(this);
    }

    @Override
    public void onStoresFetchSuccess(List<StoreModel> stores) {
        if (isFinishing()) return;
        hideLoading();
        boolean storeIsAvailable = false;
        for (StoreModel store : stores) {
            if (store.getCode().contentEquals(storeCode)) {
                storeModel = store;
                storeIsAvailable = true;
                break;
            }
        }
        if (storeIsAvailable) {
            fillViews();
        } else {
            showError(getResources().getString(R.string.storeIsNotValid), false);
        }
    }

    @Override
    public void onStoresFetchError() {
        if (isFinishing()) return;
        showError(getResources().getString(R.string.errorInDataReceiving), true);
    }

    private void showLoading() {
        if (loadingDialog != null) loadingDialog.cancel();
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingStoreInfo);
    }

    private void hideLoading() {
        if (isFinishing()) return;
        if (loadingDialog != null) loadingDialog.cancel();
    }

    private void showError(final String message, boolean showRetry) {
        String rightButton = null;
        if (showRetry) rightButton = getResources().getString(R.string.retry);
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                getResources().getString(R.string.error),
                message,
                rightButton,
                getResources().getString(R.string.close));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                messageDialog.getDialog().cancel();
                if (button == RIGHT_BUTTON) {
                    loadStore();
                } else {
                    finish();
                }
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @OnClick(R.id.managerPhoto)
    public void onManagerPhotoClick() {
        GalleryItemsModel galleryItemsModel = new GalleryItemsModel(storeModel.getManagerName(), storeModel.getManagerImage(), 0);
        GalleryActivity.show(this, galleryItemsModel);
    }

    @Override
    public void onStoreImageClick(int position, String img) {
        GalleryItemsModel galleryItemsModel = new GalleryItemsModel(storeModel.getName(), img, position);
        GalleryActivity.show(this, galleryItemsModel);
    }

}
