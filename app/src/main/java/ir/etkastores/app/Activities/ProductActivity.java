package ir.etkastores.app.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Views.CategorySliderView;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.ProductImagesSliderView;
import ir.etkastores.app.Utils.DialogHelper;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static int ACTION_LOAD_PRODUCT = 0;

    private final static String MODEL = "MODEL";
    private final static String CODE = "CODE";
    private final static String FORMAT = "FORMAT";

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
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.extraItemsHolder)
    LinearLayout extrasHolder;

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

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private ProductModel productModel;
    private String productBarcodeCode;
    private String barcodeFormat;

    private AlertDialog loadingDialog;
    private Call<OauthResponse<ProductModel>> productReq;
    private MessageDialog messageDialog;

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
        mPoint.setText(String.format(EtkaApp.getInstnace().getResources().getString(R.string.Xpoint), productModel.getPoint()));
        mTitle.setText(productModel.getTitle());
        mPrice.setText(String.format(EtkaApp.getInstnace().getResources().getString(R.string.priceX), String.valueOf(productModel.getOriginalPrice())));
        if (!TextUtils.isEmpty(productModel.getDescription())) {
            mDetail.setText(productModel.getDescription());
            mDetail.setGravity(Gravity.RIGHT);
        } else {
            mDetail.setText(R.string.thereIsNoInformationForThisProduct);
            mDetail.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        mSlider.setImages(productModel.getImageUrl());
    }

    private void initFromCode() {
        loadProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

}
