package ir.etkastores.app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Models.CategoryModel;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/1/17.
 */

public class SearchFragment extends Fragment {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_search,container,false);
            ButterKnife.bind(this,view);
            initViews();
        }
        return view;
    }

    Call<OauthResponse<List<CategoryModel>>> categoryRequest;

    private void initViews(){

        categoryRequest = ApiProvider.getAuthorizedApi().getCategory();
        categoryRequest.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        Log.e("response",""+response.body().getData().size());
                    }else{
                        Log.e("response error",""+response.body().getMeta().getMessage());
                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable t) {
                Log.e("request failure","...");
            }
        });

    }


}
