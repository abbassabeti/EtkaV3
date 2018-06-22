package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.productFilter.ProductFilterListRecyclerAdapter;
import ir.etkastores.app.fragments.searchFragments.CategoriesFragment;
import ir.etkastores.app.fragments.searchFragments.ProductsListFragment;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFilterActivity extends BaseActivity implements
        EtkaToolbar.EtkaToolbarActionsListener,
        CategoriesFragment.OnCategoryItemClickListener,
        ProductFilterListRecyclerAdapter.FilterCallback {

    private final static String CATEGORY = "CATEGORY";
    private final static String SEARCH_REQUEST = "SEARCH_REQUEST";
    private final static String IS_SEARCH = "IS_SEARCH";
    private final static String TITLE = "TITLE";

    public static void show(Context context, CategoryModel categoryModel) {
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(CATEGORY, new Gson().toJson(categoryModel));
        intent.putExtra(IS_SEARCH, false);
        context.startActivity(intent);
    }

    public static void show(Context context, SearchProductRequestModel searchProductRequestModel) {
        show(context,searchProductRequestModel,"");
    }

    public static void show(Context context, SearchProductRequestModel searchProductRequestModel, String title) {
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(SEARCH_REQUEST, new Gson().toJson(searchProductRequestModel));
        intent.putExtra(IS_SEARCH, true);
        intent.putExtra(TITLE,title);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.filterRecyclerView)
    RecyclerView recyclerView;

    private ProductFilterListRecyclerAdapter filterAdapter;

    private ProductsListFragment productsListFragment;

    private Call<OauthResponse<List<CategoryModel>>> categoryReq;

    private SearchProductRequestModel productRequestModel;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_filter);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Categories Filter");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (categoryReq != null && categoryReq.isExecuted()) categoryReq.cancel();
    }

    private void initViews() {
        toolbar.setActionListeners(this);
        toolbar.setTitle("");
        CategoryModel categoryModel = CategoryModel.fromJson(getIntent().getStringExtra(CATEGORY));
        productRequestModel = SearchProductRequestModel.fromJson(getIntent().getExtras().getString(SEARCH_REQUEST, null));
        title = getIntent().getStringExtra(TITLE);
        boolean isFromSearch = getIntent().getExtras().getBoolean(IS_SEARCH);
        if (isFromSearch) {
            ProductsListFragment productsListFragment = ProductsListFragment.newInstance(productRequestModel);
            ActivityUtils.addFragment(this, R.id.categoriesFrame, productsListFragment, "CATEGORY_FILTER", false);
            lockDrawer();
            if (!TextUtils.isEmpty(productRequestModel.getTitle())) {
                toolbar.setTitle(productRequestModel.getTitle());
            }else if (!TextUtils.isEmpty(title)){
                toolbar.setTitle(title);
            }
        } else {
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categoryModel.getId(),categoryModel.getTitle());
            categoriesFragment.setOnCategoryItemClickListener(this);
            ActivityUtils.addFragment(this, R.id.categoriesFrame, categoriesFragment, "CATEGORY_FILTER", false);
            toolbar.setTitle(categoryModel.getTitle());
            lockDrawer();
        }
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lockDrawer();
    }

    @Override
    public void onActionClick(int actionCode) {
        if (actionCode == MENU_BUTTON) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        }
    }

    private void addFragmentToBackStack(Fragment fragment) {
        ActivityUtils.replaceFragment(this, R.id.categoriesFrame, fragment, "CATEGORY_FILTER", true);
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.SelectCategoryFromSearch);
        if (categoryModel.hasChild()) {
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categoryModel.getId(),categoryModel.getTitle());
            categoriesFragment.setOnCategoryItemClickListener(this);
            addFragmentToBackStack(categoriesFragment);
            lockDrawer();
        } else {
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.addCategoryId(categoryModel.getId());
            searchProductRequestModel.setSort(SearchProductRequestModel.SortOptions.Default);
            productsListFragment = ProductsListFragment.newInstance(searchProductRequestModel);
            filterAdapter = new ProductFilterListRecyclerAdapter(this);
            recyclerView.setAdapter(filterAdapter);
            filterAdapter.setFilterCallback(this);
            addFragmentToBackStack(productsListFragment);
            loadMenuCategories(categoryModel.getId());
            unlockDrawer();
        }
        toolbar.setTitle(categoryModel.getTitle());
    }

    @Override
    public void onUpdateTitle(String title) {
        toolbar.setTitle(title);
    }

    private void lockDrawer() {
        toolbar.showFilter(false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer() {
        toolbar.showFilter(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onSelectSort(int sort) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.FilterSort);
        String sortValue = "";
        switch (sort) {
            case DEFAULT:
                sortValue = SearchProductRequestModel.SortOptions.Default;
                break;
            case TOP_OFFER_SORT:
                sortValue = SearchProductRequestModel.SortOptions.OfferPrice;
                break;

            case TOP_SALE_SORT:
                sortValue = SearchProductRequestModel.SortOptions.PriceAsc;
                break;

            case TOP_RATE_SORT:
                sortValue = SearchProductRequestModel.SortOptions.PointDesc;
                break;

            case NEWEST_SORT:
                sortValue = SearchProductRequestModel.SortOptions.UpdateDateDesc;
                break;
        }
        SearchProductRequestModel searchProductRequestModel = productsListFragment.getSearchRequestModel();
        searchProductRequestModel.setSort(sortValue);
        productsListFragment.refreshResult(searchProductRequestModel);
        drawerLayout.closeDrawers();
    }

    @Override
    public void onSelectCategory(List<CategoryModel> categories) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.FilterCategory);
        SearchProductRequestModel searchProductRequestModel = productsListFragment.getSearchRequestModel();
        List<Long> ids = new ArrayList<>();
        for (CategoryModel model : categories) {
            ids.add(model.getId());
        }
        searchProductRequestModel.setCategoryId(ids);
        productsListFragment.refreshResult(searchProductRequestModel);
    }

    private void loadMenuCategories(final long id) {
        categoryReq = ApiProvider.getAuthorizedApi().getCategory(id);
        categoryReq.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        List<CategoryModel> items = new ArrayList<>();
                        for (CategoryModel categoryModel : response.body().getData()) {
                            items.add(new CategoryModel(categoryModel.getTitle(), categoryModel.getId()));
                        }
                        filterAdapter.setCategories(items);
                    } else {

                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable throwable) {
                if (isFinishing() || call.isCanceled()) return;

            }
        });
    }

}
