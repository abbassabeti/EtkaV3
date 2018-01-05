package ir.etkastores.app.Fragments.SearchFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.CategoryActivity;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 1/5/18.
 */

public class CategoriesFragment extends Fragment implements CategoryRecyclerAdapter.OnCategoryItemClickListener {

    private final static String CATEGORY_ID_KEY = "CATEGORY_ID_KEY";

    public static Fragment newInstance(int categoryId){
        CategoriesFragment  categoriesFragment = new CategoriesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID_KEY,categoryId);
        categoriesFragment.setArguments(bundle);
        return categoriesFragment;
    }

    @BindView(R.id.categoryRecyclerView)
    RecyclerView categoryRecyclerView;

    Call<OauthResponse<List<CategoryModel>>> request;

    private CategoryRecyclerAdapter adapter;
    private int categoryId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            categoryId = getArguments().getInt(CATEGORY_ID_KEY,0);
        }catch (Exception err){
            categoryId = 0;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_categories,container,false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    private void initViews(){
        adapter = new CategoryRecyclerAdapter(getActivity());
        adapter.setOnCategoryItemClickListener(this);
        categoryRecyclerView.setAdapter(adapter);
        if (categoryId==0){
            loadCategoryAtRootLevel();
        }else{
            loadCategory();
        }
    }

    private void loadCategory(){
        request = ApiProvider.getAuthorizedApi().getCategory(categoryId);
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        adapter.setData(response.body().getData());
                    }else{

                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {

            }
        });
    }

    private void loadCategoryAtRootLevel(){
        request = ApiProvider.getAuthorizedApi().getCategoryAtLevel(1);
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        adapter.setData(response.body().getData());
                    }else{

                    }
                }else{
                    onFailure(null,null);
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
            CategoryActivity.show(getActivity(),model);
        }
    }

}
