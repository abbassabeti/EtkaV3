package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.ProductsRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.saveProduct.SavedProductModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextShoppingListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, ProductsRecyclerAdapter.ProductsRecyclerCallbacks {

    public static void start(Activity activity){
        Intent intent = new Intent(activity,NextShoppingListActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.messageView)
    MessageView messageView;

    private Call<OauthResponse<List<SavedProductModel>>> savedProductsReq;
    private ProductsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_shopping_list);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    private void initViews(){
        showEmptyMessage();
        loadProducts();
        adapter = new ProductsRecyclerAdapter(this,this);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void showEmptyMessage(){
        messageView.show(R.drawable.ic_warning_orange_48dp,R.string.commingSoonMessage,0,null);
    }

    private void loadProducts(){
       savedProductsReq = ApiProvider.getAuthorizedApi().getSavedProducts();
       savedProductsReq.enqueue(new Callback<OauthResponse<List<SavedProductModel>>>() {
           @Override
           public void onResponse(Call<OauthResponse<List<SavedProductModel>>> call, Response<OauthResponse<List<SavedProductModel>>> response) {
               if (response.isSuccessful()){
                   if (response.body().isSuccessful()){

                   }else{

                   }
               }else{
                   onFailure(call,null);
               }
           }

           @Override
           public void onFailure(Call<OauthResponse<List<SavedProductModel>>> call, Throwable throwable) {

           }
       });
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onProductItemClick(ProductModel productModel) {

    }

}