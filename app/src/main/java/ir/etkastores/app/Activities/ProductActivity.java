package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.CategoryGroupHorizontalView;
import ir.etkastores.app.UI.Views.CategorySliderView;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class ProductActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String MODEL = "MODEL";
    private final static String CODE = "CODE";

    @BindView(R.id.slider)
    CategorySliderView mSlider;
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

    public static void show(Activity activity, String code) {
        Intent intent = new Intent(activity, ProductActivity.class);
        intent.putExtra(CODE, code);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    private ProductModel productModel;
    private String productCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        productModel = ProductModel.fromJson(getIntent().getExtras().getString(MODEL, null));
        productCode = getIntent().getExtras().getString(CODE, null);

        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        if (productModel != null) {
            initFromProduct();
        } else if (TextUtils.isEmpty(productCode)) {
            initFromCode();
        }
    }

    private void initFromProduct() {
        toolbar.setTitle(productModel.getCategoryTitle());
//        mPoint.setText(String.format(EtkaApp.getInstnace().getResources().getString(R.string.Xpoint), productModel.getPoint()));
        mTitle.setText(productModel.getTitle());
//        mPrice.setText(String.format(EtkaApp.getInstnace().getResources().getString(R.string.priceX), String.valueOf(productModel.getOriginalPrice())));
//        mDetail.setText(productModel.getDescription());
    }

    private void initFromCode() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        extrasHolder.post(new Runnable() {
            @Override
            public void run() {
                extrasHolder.addView(new CategoryGroupHorizontalView(ProductActivity.this));
                extrasHolder.addView(new CategoryGroupHorizontalView(ProductActivity.this));
                extrasHolder.addView(new CategoryGroupHorizontalView(ProductActivity.this));
            }
        });
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
