package ir.etkastores.app.Fragments.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Activities.HekmatProductsActivity;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.HekmatRecyclerAdapter;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.hekmat.HekmatModel;
import ir.etkastores.app.R;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 12/2/17.
 */

public class HekmatWaresSlide extends Fragment implements PageTrigger, HekmatRecyclerAdapter.OnHekmatItemClickListener {

    public static HekmatWaresSlide newInstance(){
        return new HekmatWaresSlide();
    }

    View view;

    @BindView(R.id.hekmatRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar progressBar;

    private HekmatRecyclerAdapter adapter;

    private boolean isFirstSelect = true;

    private Call<OauthResponse<List<HekmatModel>>> hekmatReq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment__home_hekmat, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    private void initViews(){
        adapter = new HekmatRecyclerAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData(){
        showLoading();
        hekmatReq = ApiProvider.getAuthorizedApi().getHekmat();
        hekmatReq.enqueue(new Callback<OauthResponse<List<HekmatModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<HekmatModel>>> call, Response<OauthResponse<List<HekmatModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        adapter.setItems(response.body().getData());
                    }else{

                    }
                }else{
                    onFailure(null,null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<HekmatModel>>> call, Throwable t) {
                hideLoading();
            }
        });
    }

    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageSelected() {
        if (!isFirstSelect) return;
        isFirstSelect = false;
        initViews();
    }

    @Override
    public void onHekmatItemClick(HekmatModel hekmatModel) {
        HekmatProductsActivity.show(getActivity(),hekmatModel);
    }

}
