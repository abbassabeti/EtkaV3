package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, CategoryRecyclerAdapter.OnCategoryItemClickListener {

    private final static String MODEL = "MODEL";

    public static void show(Activity activity, CategoryModel model){
        Intent intent = new Intent(activity,CategoryActivity.class);
        intent.putExtra(MODEL,new Gson().toJson(model));
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.categoryRecyclerView)
    RecyclerView categoryRecyclerView;

    CategoryModel categoryModel;

    CategoryRecyclerAdapter adapter;

    Call<OauthResponse<List<CategoryModel>>> request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        categoryModel = new Gson().fromJson(getIntent().getExtras().getString(MODEL),CategoryModel.class);
        initViews();
    }

    private void initViews(){
        toolbar.setActionListeners(this);
        toolbar.setTitle(categoryModel.getTitle());
        adapter = new CategoryRecyclerAdapter(this);
        adapter.setOnCategoryItemClickListener(this);
        categoryRecyclerView.setAdapter(adapter);
        loadData();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void loadData(){
        request = ApiProvider.getAuthorizedApi().getCategory(categoryModel.getId());
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        adapter.setData(response.body().getData());
                    }else{

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCategoryItemClick(CategoryModel model, int position) {
        if (model.hasChild()){
            CategoryActivity.show(this,model);
        }
    }

}
