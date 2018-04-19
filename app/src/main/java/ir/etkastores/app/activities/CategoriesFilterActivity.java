package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.productFilter.ProductFilterCategoryViewHolder;
import ir.etkastores.app.adapters.recyclerViewAdapters.productFilter.ProductFilterListRecyclerAdapter;
import ir.etkastores.app.fragments.searchFragments.CategoriesFragment;
import ir.etkastores.app.fragments.searchFragments.ProductsListFragment;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;

public class CategoriesFilterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoriesFragment.OnCategoryItemClickListener {

    private final static String ID = "ID";
    private final static String SEARCH_TERM = "SEARCH_TERM";
    private final static String IS_SEARCH = "IS_SEARCH";

    public static void show(Context context, CategoryModel categoryModel){
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(ID,categoryModel.getId());
        intent.putExtra(IS_SEARCH,false);
        context.startActivity(intent);
    }

    public static void show(Context context, String searchTerm){
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(SEARCH_TERM,searchTerm);
        intent.putExtra(IS_SEARCH,true);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.filterRecyclerView)
    RecyclerView recyclerView;

    private ProductFilterListRecyclerAdapter filterAdapter;

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

    private void initViews(){
        toolbar.setActionListeners(this);
        long id = getIntent().getLongExtra(ID,0);
        String searchTerm = getIntent().getExtras().getString(SEARCH_TERM,null);
        boolean isFromSearch = getIntent().getExtras().getBoolean(IS_SEARCH);
        if (isFromSearch){
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.setTitle(searchTerm);
            ProductsListFragment productsListFragment = ProductsListFragment.newInstance(searchProductRequestModel);
            ActivityUtils.addFragment(this,R.id.categoriesFrame,productsListFragment,"CATEGORY_FILTER",false);
        }else{
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(id);
            categoriesFragment.setOnCategoryItemClickListener(this);
            ActivityUtils.addFragment(this,R.id.categoriesFrame,categoriesFragment,"CATEGORY_FILTER",false);
        }

        filterAdapter = new ProductFilterListRecyclerAdapter(this);
        recyclerView.setAdapter(filterAdapter);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void addFragmentToBackStack(Fragment fragment){
        ActivityUtils.replaceFragment(this,R.id.categoriesFrame,fragment,"CATEGORY_FILTER",true);
    }

    @Override
    public void onCategoryClicked(CategoryModel categoryModel) {
        if (categoryModel.hasChild()){
            CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(categoryModel.getId());
            categoriesFragment.setOnCategoryItemClickListener(this);
            addFragmentToBackStack(categoriesFragment);
        }else{
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.addCategoryId(categoryModel.getId());
            ProductsListFragment productsListFragment = ProductsListFragment.newInstance(searchProductRequestModel);
            addFragmentToBackStack(productsListFragment);
        }
    }

}
