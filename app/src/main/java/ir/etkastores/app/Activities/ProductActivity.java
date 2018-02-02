package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;

public class ProductActivity extends AppCompatActivity implements EtkaToolbar.EtkaToolbarActionsListener{

    private final static String MODEL = "MODEL";
    private final static String CODE = "CODE";

    public static void show(Activity activity, ProductModel productModel){
        Intent intent = new Intent(activity,ProductActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(productModel));
        activity.startActivity(intent);
    }

    public static void show(Activity activity, String code){
        Intent intent = new Intent(activity,ProductActivity.class);
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

        productModel = ProductModel.fromJson(getIntent().getExtras().getString(MODEL,null));
        productCode = getIntent().getExtras().getString(CODE,null);

        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        if (productModel != null){
            initFromProduct();
        }else if (TextUtils.isEmpty(productCode)){
            initFromCode();
        }
    }

    private void initFromProduct(){
        toolbar.setTitle(productModel.getTitle());
    }

    private void initFromCode(){

    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

}
