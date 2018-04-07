package ir.etkastores.app.fragments.home;

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
import ir.etkastores.app.activities.HekmatProductsActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.HekmatRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 12/2/17.
 */

public class HekmatWaresSlide extends Fragment implements HekmatRecyclerAdapter.OnHekmatItemClickListener {

    public static int TAB_POSITION_ID = 2;

    public static HekmatWaresSlide newInstance() {
        return new HekmatWaresSlide();
    }

    View view;

    @BindView(R.id.hekmatRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar progressBar;

    @BindView(R.id.messageView)
    MessageView messageView;

    private boolean isDataLoaded = false;

    private HekmatRecyclerAdapter adapter;

    private Call<OauthResponse<List<HekmatModel>>> hekmatReq;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment__home_hekmat, container, false);
            ButterKnife.bind(this, view);
        }
        if (getUserVisibleHint()) checkToInitViews();
        return view;
    }

    private void initViews() {
        adapter = new HekmatRecyclerAdapter(getActivity(), this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        showLoading();
        messageView.hide();
        hekmatReq = ApiProvider.getAuthorizedApi().getHekmat();
        hekmatReq.enqueue(new Callback<OauthResponse<List<HekmatModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<HekmatModel>>> call, Response<OauthResponse<List<HekmatModel>>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.setItems(response.body().getData());
                        isDataLoaded = true;
                    } else {
                        showErrorView(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<HekmatModel>>> call, Throwable t) {
                if (!isAdded()) return;
                hideLoading();
                showErrorView(getResources().getString(R.string.errorHappendInReceivingData));
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            checkToInitViews();
        }
    }

    private void checkToInitViews() {
        if (!isDataLoaded) initViews();
    }

    @Override
    public void onHekmatItemClick(HekmatModel hekmatModel) {
        HekmatProductsActivity.show(getActivity(), hekmatModel);
    }

    private void showErrorView(String message) {
        messageView.show(R.drawable.ic_warning_orange_48dp, message, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
            @Override
            public void onMessageViewButtonClick() {
                loadData();
            }
        });
    }

}
