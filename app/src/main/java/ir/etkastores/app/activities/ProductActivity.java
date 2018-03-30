package ir.etkastores.app.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.saveProduct.SaveProductRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.ProductImagesSliderView;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.utils.StringUtils;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static int ACTION_LOAD_PRODUCT = 0;

    private final static String MODEL = "MODEL";
    private final static String CODE = "CODE";
    private final static String FORMAT = "FORMAT";

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;
    @BindView(R.id.slider)
    ProductImagesSliderView mSlider;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.point)
    TextView mPoint;
    @BindView(R.id.detail)
    TextView mDetail;
    @BindView(R.id.priceDiscounted)
    TextView mPriceDiscounted;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.extraItemsHolder)
    LinearLayout extrasHolder;
    @BindView(R.id.addToNextShoppingListButton)
    Button addToNextShoppingListButton;
    @BindView(R.id.saveMinusButton)
    AppCompatImageView saveMinusButton;
    @BindView(R.id.saveCountTv)
    TextView saveCountTv;
    @BindView(R.id.savePlusButton)
    AppCompatImageView savePlusButton;

    public static void show(Activity activity, ProductModel productModel) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(productModel));
        activity.startActivity(intent);
    }

    public static void show(Activity activity, String code, String format) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(CODE, code);
        intent.putExtra(FORMAT, format);
        activity.startActivity(intent);
    }

    private ProductModel productModel;
    private String productBarcodeCode;
    private String barcodeFormat;

    private AlertDialog loadingDialog;
    private Call<OauthResponse<ProductModel>> productReq;
    private Call<OauthResponse<Long>> addToNextShoppingListReq;
    private MessageDialog messageDialog;
    private int saveCountValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        productModel = ProductModel.fromJson(getIntent().getExtras().getString(MODEL, null));
        productBarcodeCode = getIntent().getExtras().getString(CODE, null);
        barcodeFormat = getIntent().getExtras().getString(FORMAT, null);
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        if (productModel != null) {
            initFromProduct();
        } else if (!TextUtils.isEmpty(productBarcodeCode)) {
            initFromCode();
        }
    }

    private void initFromProduct() {
        scrollView.setVisibility(View.VISIBLE);
        toolbar.setTitle(productModel.getCategoryTitle());
        mPoint.setText(String.format(EtkaApp.getInstance().getResources().getString(R.string.Xpoint), productModel.getPoint()));
        mTitle.setText(productModel.getTitle());
        mPrice.setText(productModel.getStrikeThruPrice());
        StringUtils.setStrikeThruTextView(mPrice);
        mPriceDiscounted.setText(productModel.getFinalPrice());
        if (!TextUtils.isEmpty(productModel.getDescription())) {
            mDetail.setText(productModel.getDescription());
            mDetail.setGravity(Gravity.RIGHT);
        } else {
            mDetail.setText(R.string.thereIsNoInformationForThisProduct);
            mDetail.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        mSlider.setImages(productModel.getImageUrl());
        updateSaveCountValue();
    }

    private void initFromCode() {
        loadProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Product Activity");
        initViews();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadProduct() {
        loadingDialog = DialogHelper.showLoading(this, R.string.inLoadingProductInfo);
        productReq = ApiProvider.getAuthorizedApi().getProductByBarcode(productBarcodeCode);
        productReq.enqueue(new Callback<OauthResponse<ProductModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<ProductModel>> call, Response<OauthResponse<ProductModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        productModel = response.body().getData();
                        initFromProduct();
                    } else {
                        showProductNotFoundDialog();
                    }
                } else {
                    onFailure(null, null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<ProductModel>> call, Throwable t) {
                loadingDialog.cancel();
                showRetryDialog(ACTION_LOAD_PRODUCT);
            }
        });
    }

    private void showProductNotFoundDialog() {
        messageDialog = MessageDialog.productNotFound();
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                messageDialog.getDialog().cancel();
                finish();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showRetryDialog(int action) {
        String title = getResources().getString(R.string.error);
        String message = getResources().getString(R.string.errorInLoadProduct);
        messageDialog = MessageDialog.warningRetry(title, message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    loadProduct();
                } else {
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @OnClick({R.id.addToNextShoppingListButton, R.id.saveMinusButton, R.id.savePlusButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addToNextShoppingListButton:
                if (saveCountValue == 0) {
                    Toaster.show(this, R.string.setProductCounts);
                    return;
                }
                sendAddToNextShoppingListRequest();
                break;

            case R.id.saveMinusButton:
                if (saveCountValue == 0) return;
                saveCountValue--;
                updateSaveCountValue();
                break;

            case R.id.savePlusButton:
                saveCountValue++;
                updateSaveCountValue();
                break;
        }
    }

    private void updateSaveCountValue() {
        saveCountTv.setText(String.valueOf(saveCountValue));
    }

    private void sendAddToNextShoppingListRequest() {
        if (isFinishing()) return;
        if (ProfileManager.isGuest()) {
            showNedToLoginDialog();
            return;
        }
        loadingDialog = DialogHelper.showLoading(this, R.string.inSavingToNextShoppingList);
        addToNextShoppingListReq = ApiProvider.getAuthorizedApi().saveProduct(new SaveProductRequestModel(productModel.getId(), saveCountValue));
        loadingDialog.show();
        addToNextShoppingListReq.enqueue(new Callback<OauthResponse<Long>>() {
            @Override
            public void onResponse(Call<OauthResponse<Long>> call, Response<OauthResponse<Long>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        Toaster.showLong(ProductActivity.this, R.string.productAddedToNextShoppingListSuccessfully);
                    } else {
                        showAddToNextShoppingListError(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<Long>> call, Throwable throwable) {
                if (isFinishing()) return;
                if (call != null && call.isCanceled()) return;
                loadingDialog.cancel();
                showAddToNextShoppingListError(getResources().getString(R.string.errorInAddingToNextShoppingList));
            }
        });
    }

    private void showNedToLoginDialog() {
        final MessageDialog messageDialog = MessageDialog.loginRequired();
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    LoginRegisterActivity.showLogin(ProductActivity.this);
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showAddToNextShoppingListError(String message) {
        if (isFinishing()) return;
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error), message);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (isFinishing()) return;
                if (button == RIGHT_BUTTON) {
                    sendAddToNextShoppingListRequest();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (addToNextShoppingListReq != null) addToNextShoppingListReq.cancel();
        if (productReq != null) productReq.cancel();
    }
}
