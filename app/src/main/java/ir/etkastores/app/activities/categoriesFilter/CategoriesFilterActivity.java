package ir.etkastores.app.activities.categoriesFilter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.fragments.searchFragments.CategoriesFragment;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.ActivityUtils;

public class CategoriesFilterActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoriesFragment.OnCategoryItemClickListener {

    private static String ID = "ID";;

    public static void show(Context context, CategoryModel categoryModel){
        Intent intent = new Intent(context, CategoriesFilterActivity.class);
        intent.putExtra(ID,categoryModel.getId());
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

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
        CategoriesFragment categoriesFragment = CategoriesFragment.newInstance(id);
        categoriesFragment.setOnCategoryItemClickListener(this);
        ActivityUtils.addFragment(this,R.id.categoriesFrame,categoriesFragment,"CATEGORY_FILTER",false);

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
        }
    }

}
