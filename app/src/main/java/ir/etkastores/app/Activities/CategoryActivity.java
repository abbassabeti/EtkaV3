package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import ir.etkastores.app.Models.ProductSearchResponseModel;
import ir.etkastores.app.Models.SearchProductRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Dialogs.MessageDialog;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.UI.Views.MessageView;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoryRecyclerAdapter.OnCategoryItemClickListener, ProductsRecyclerAdapter.ProductsRecyclerCallbacks {

    private final static String CATEGORY_DATA_MODEL = "CATEGORY_DATA_MODEL";
    private final static String SEARCH_DATA_MODEL = "SEARCH_DATA_MODEL";

    public static void show(Activity activity, CategoryModel model) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(CATEGORY_DATA_MODEL, new Gson().toJson(model));
        activity.startActivity(intent);
    }

    public static void show(Activity activity, SearchProductRequestModel searchRequestModel) {
        Intent intent = new Intent(activity, CategoryActivity.class);
        intent.putExtra(SEARCH_DATA_MODEL, new Gson().toJson(searchRequestModel));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.categoryRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.messageView)
    MessageView messageView;

    private CategoryModel categoryModel;

    private CategoryRecyclerAdapter categoryAdapter;
    private ProductsRecyclerAdapter productsAdapter;

    private Call<OauthResponse<List<CategoryModel>>> categoryRequest;
    private Call<OauthResponse<ProductSearchResponseModel>> productRequest;
    private SearchProductRequestModel searchRequestModel;

    private boolean isFromSearch = false;

    private final int MAX_PRODUCT_NEEDED = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        String categoryExtraModel = getIntent().getExtras().getString(CATEGORY_DATA_MODEL, null);
        String searchExtraModel = getIntent().getExtras().getString(SEARCH_DATA_MODEL, null);
        if (!TextUtils.isEmpty(categoryExtraModel))
            categoryModel = CategoryModel.fromJson(categoryExtraModel);
        if (!TextUtils.isEmpty(searchExtraModel)) {
            searchRequestModel = SearchProductRequestModel.fromJson(searchExtraModel);
            isFromSearch = true;
        }
        initViews();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        if (categoryModel != null && categoryModel.hasChild()) {
            initForCategories();
        } else {
            initForProducts();
        }
    }

    private void initForCategories() {
        toolbar.setTitle(categoryModel.getTitle());
        categoryAdapter = new CategoryRecyclerAdapter(this);
        categoryAdapter.setOnCategoryItemClickListener(this);
        recyclerView.setAdapter(categoryAdapter);
        loadCategory();
    }

    private void initForProducts() {
        productsAdapter = new ProductsRecyclerAdapter(this, this);
        recyclerView.setAdapter(productsAdapter);
        if (searchRequestModel == null) {
            toolbar.setTitle(categoryModel.getTitle());
            searchRequestModel = new SearchProductRequestModel();
            searchRequestModel.setCategoryId(categoryModel.getId());
        }else{
            toolbar.setTitle(searchRequestModel.getTitle());
        }
        searchRequestModel.setTake(MAX_PRODUCT_NEEDED);
        searchRequestModel.setSort(SearchProductRequestModel.Sorts.UpdateDateAsc);
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
        productRequest.enqueue(new Callback<OauthResponse<ProductSearchResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<ProductSearchResponseModel>> call, Response<OauthResponse<ProductSearchResponseModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        productsAdapter.addItems(response.body().getData().getItems());
                        if (productsAdapter.getItemCount() == 0) {
                            showEmptyResultMessage();
                        }
                        if (response.body().getData().getItems().size() == MAX_PRODUCT_NEEDED) {
                            productsAdapter.setLoadMoreEnabled(true);
                            searchRequestModel.setPage(searchRequestModel.getPage() + 1);
                        }
                    } else {
                        // TODO: handle error
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<ProductSearchResponseModel>> call, Throwable t) {
                hideLoading();
                // TODO: handle error
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
                        if (response.body().getData().size() > 0) {
                            categoryAdapter.setData(response.body().getData());
                        } else {
                            showEmptyResultMessage();
                        }
                    } else {
                        showRetryDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {
                showRetryDialog(null);
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
        ProductActivity.show(this, productModel);
    }

    private void showRetryDialog(String message) {
        String msg = message;
        if (TextUtils.isEmpty(msg))
            msg = getResources().getString(R.string.errorHappendInReceivingData);
        String title = getResources().getString(R.string.errorInDataReceiving);
        String rightButton = getResources().getString(R.string.retry);
        String leftButton = getResources().getString(R.string.cancel);
        final MessageDialog messageDialog = MessageDialog.newInstance(R.drawable.ic_warning_orange_48dp,
                title,
                msg,
                rightButton,
                leftButton);
        boolean isCancellable = false;
        messageDialog.show(getSupportFragmentManager(), isCancellable, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                messageDialog.getDialog().cancel();
                if (button == LEFT_BUTTON) {
                    finish();
                } else if (button == RIGHT_BUTTON) {
                    loadCategory();
                }
            }

            @Override
            public void onDialogMessageDismiss() {
                messageDialog.getDialog().cancel();
            }
        });
    }

    private void showEmptyResultMessage() {
        int message = R.string.thereIsNotResultAvailable;
        if (isFromSearch) {
            message = R.string.yourSearchHasNotResult;
        }
        messageView.show(R.drawable.ic_warning_orange_48dp, message, R.string.back, new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                onBackPressed();
            }
        });
    }

}
