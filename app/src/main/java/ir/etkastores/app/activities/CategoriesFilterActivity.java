package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.productFilter.CategoryItem;
import ir.etkastores.app.adapters.recyclerViewAdapters.productFilter.ProductFilterListRecyclerAdapter;
import ir.etkastores.app.fragments.searchFragments.CategoriesFragment;
import ir.etkastores.app.fragments.searchFragments.ProductsListFragment;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;

public class CategoriesFilterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoriesFragment.OnCategoryItemClickListener, ProductFilterListRecyclerAdapter.FilterCallback {

    private final static String ID = "ID";
    private final static String SEARCH_TERM = "SEARCH_TERM";
    private final static String IS_SEARCH = "IS_SEARCH";

    public static void show(Context context, CategoryModel categoryModel) {
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(ID, categoryModel.getId());
        intent.putExtra(IS_SEARCH, false);
        context.startActivity(intent);
    }

    public static void show(Context context, String searchTerm) {
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(SEARCH_TERM, searchTerm);
        intent.putExtra(IS_SEARCH, true);
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

    private void initViews() {
        toolbar.setActionListeners(this);
        long id = getIntent().getLongExtra(ID, 0);
        String searchTerm = getIntent().getExtras().getString(SEARCH_TERM, null);
        boolean isFromSearch = getIntent().getExtras().getBoolean(IS_SEARCH);
        if (isFromSearch) {
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.setTitle(searchTerm);
            ProductsListFragment productsListFragment = ProductsListFragment.newInstance(searchProductRequestModel);
            ActivityUtils.addFragment(this, R.id.categoriesFrame, productsListFragment, "CATEGORY_FILTER", false);
            unlockDrawer();
        } else {
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(id);
            categoriesFragment.setOnCategoryItemClickListener(this);
            ActivityUtils.addFragment(this, R.id.categoriesFrame, categoriesFragment, "CATEGORY_FILTER", false);
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
        if (categoryModel.hasChild()) {
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categoryModel.getId());
            categoriesFragment.setOnCategoryItemClickListener(this);
            addFragmentToBackStack(categoriesFragment);
            lockDrawer();
        } else {
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.addCategoryId(categoryModel.getId());
            productsListFragment = ProductsListFragment.newInstance(searchProductRequestModel);
            filterAdapter = new ProductFilterListRecyclerAdapter(this);
            recyclerView.setAdapter(filterAdapter);
            filterAdapter.setFilterCallback(this);
            addFragmentToBackStack(productsListFragment);
            unlockDrawer();
        }
    }

    private void lockDrawer(){
        toolbar.showMenu(false);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer(){
        toolbar.showMenu(true);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onSelectSort(int sort) {
        Log.e("selected sort is",""+sort);
        List<CategoryItem> items = new ArrayList<>();
        items.add(new CategoryItem("یک",1));
        items.add(new CategoryItem("دو",2));
        items.add(new CategoryItem("سه",3));
        items.add(new CategoryItem("چهار",4));
        items.add(new CategoryItem("پنج",5));
        items.add(new CategoryItem("شیش",6));
        items.add(new CategoryItem("هفت",7));
        items.add(new CategoryItem("هشت",8));
        items.add(new CategoryItem("نه",9));
        items.add(new CategoryItem("ده",10));
        items.add(new CategoryItem("یازده",11));
        items.add(new CategoryItem("دوازده",12));
        items.add(new CategoryItem("سیزده",13));
        items.add(new CategoryItem("چهارده",14));
        items.add(new CategoryItem("پانزده",15));
        items.add(new CategoryItem("شانزده",16));
        items.add(new CategoryItem("هفده",17));
        items.add(new CategoryItem("هجده",18));
        items.add(new CategoryItem("نوزده",19));
        items.add(new CategoryItem("بیست",20));
        items.add(new CategoryItem("بیست و یک",21));
        items.add(new CategoryItem("بیست و دو",22));
        items.add(new CategoryItem("بیست و سه",23));
        items.add(new CategoryItem("بیست و چهار",24));
        items.add(new CategoryItem("بیست و پنج",25));
        items.add(new CategoryItem("بیست و شش",26));
        items.add(new CategoryItem("بیست و هفت",27));
        items.add(new CategoryItem("بیست و هشت",28));
        items.add(new CategoryItem("بیست و نه",29));
        items.add(new CategoryItem("سی",30));
        filterAdapter.setCategories(items);
    }

    @Override
    public void onSelectCategory(List<CategoryItem> categories) {
        Log.e("selected categories","....");
    }

}
