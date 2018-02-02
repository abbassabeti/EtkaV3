package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.ProductsRecyclerAdapter;
import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.Models.SearchProductRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoryRecyclerAdapter.OnCategoryItemClickListener, ProductsRecyclerAdapter.ProductsRecyclerCallbacks {

    private final static String MODEL = "MODEL";

    public static void show(Activity activity, CategoryModel model) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(MODEL, new Gson().toJson(model));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.categoryRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    private CategoryModel categoryModel;

    private CategoryRecyclerAdapter categoryAdapter;
    private ProductsRecyclerAdapter productsAdapter;

    private Call<OauthResponse<List<CategoryModel>>> categoryRequest;
    private Call<OauthResponse<List<ProductModel>>> productRequest;
    private SearchProductRequestModel searchRequestModel;

    private final int MAX_PRODUCT_NEEDED = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        categoryModel = new Gson().fromJson(getIntent().getExtras().getString(MODEL), CategoryModel.class);
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        toolbar.setTitle(categoryModel.getTitle());
        if (categoryModel.hasChild()) {
            initForCategories();
        } else {
            initForProducts();
        }
    }

    private void initForCategories() {
        categoryAdapter = new CategoryRecyclerAdapter(this);
        categoryAdapter.setOnCategoryItemClickListener(this);
        recyclerView.setAdapter(categoryAdapter);
        loadCategory();
    }

    private void initForProducts() {
        productsAdapter = new ProductsRecyclerAdapter(this, this);
        searchRequestModel = new SearchProductRequestModel();
        searchRequestModel.setCategoryId(categoryModel.getId());
        loadProducts();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadProducts() {
        showLoading();
        productRequest = ApiProvider.getAuthorizedApi().searchProduct(searchRequestModel);
        productRequest.enqueue(new Callback<OauthResponse<List<ProductModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<ProductModel>>> call, Response<OauthResponse<List<ProductModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        productsAdapter.addItems(response.body().getData());
                        if (response.body().getData().size() == MAX_PRODUCT_NEEDED) {
                            productsAdapter.setLoadMoreEnabled(true);
                            searchRequestModel.setPage(searchRequestModel.getPage()+1);
                        }
                    } else {

                    }
                } else {
                    onFailure(null,null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<ProductModel>>> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void loadCategory() {
        showLoading();
        categoryRequest = ApiProvider.getAuthorizedApi().getCategory(categoryModel.getId());
        categoryRequest.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        categoryAdapter.setData(response.body().getData());
                    } else {

                    }
                } else {
                    onFailure(null,null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {
                hideLoading();
            }
        });
    }

    @Override
    public void onCategoryItemClick(CategoryModel model, int position) {
        CategoryActivity.show(this, model);
    }

    private void showLoading() {
        circularProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore() {
        loadProducts();
    }

    @Override
    public void onProductItemClick(ProductModel productModel) {

    }

}
