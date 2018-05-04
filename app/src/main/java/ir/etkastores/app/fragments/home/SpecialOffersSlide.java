package ir.etkastores.app.fragments.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.home.HomeItemsModel;
import ir.etkastores.app.R;
import ir.etkastores.app.ui.views.CategoryGroupHorizontalView;
import ir.etkastores.app.ui.views.HomeSliderItemView;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 12/2/17.
 */

public class SpecialOffersSlide extends Fragment implements CategoryGroupHorizontalView.OnProductClickListener {

    public static int TAB_POSITION_ID = 1;

    public static SpecialOffersSlide newInstance() {
        return new SpecialOffersSlide();
    }

    View view;

    @BindView(R.id.itemsHolder)
    LinearLayout itemsHolder;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    boolean isDataLoaded = false;

    private Call<OauthResponse<List<HomeItemsModel>>> offersReq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_slide, container, false);
            ButterKnife.bind(this, view);
        }
        if (getUserVisibleHint()) checkToInitViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews() {
        loadOffers();
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

    private void loadOffers() {
        showLoading();
        messageView.hide();
        offersReq = ApiProvider.getAuthorizedApi().getOffers("TempOffers");
        offersReq.enqueue(new Callback<OauthResponse<List<HomeItemsModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<HomeItemsModel>>> call, Response<OauthResponse<List<HomeItemsModel>>> response) {
                if (!isAdded()) return;
                hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().getData().size() == 0) {
                        showMessageView(getResources().getString(R.string.thereIsNotResultAvailable), false);
                    } else {
                        addItems(response.body().getData());
                    }
                    isDataLoaded = true;
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<HomeItemsModel>>> call, Throwable throwable) {
                if (!isAdded()) return;
                hideLoading();
                showMessageView(getResources().getString(R.string.errorInDataReceiving), true);
            }
        });
    }

    private void showMessageView(String message, boolean hasRetry) {
        if (hasRetry) {
            String buttonTitle = getResources().getString(R.string.retry);
            messageView.show(R.drawable.ic_warning_orange_48dp, message, buttonTitle, new MessageView.OnMessageViewButtonClick() {
                @Override
                public void onMessageViewButtonClick() {
                    loadOffers();
                }
            });
        } else {
            messageView.show(R.drawable.ic_warning_orange_48dp, message, null, null);
        }
    }

    private void addItems(List<HomeItemsModel> items) {
        for (HomeItemsModel offersItem : items) {
            if (offersItem.getBanners() != null && offersItem.getBanners().size() > 0) {
                itemsHolder.addView(new HomeSliderItemView(getActivity(), offersItem.getBanners()));
            }
            if (offersItem.getProducts() != null && offersItem.getProducts().size() > 0) {
                CategoryGroupHorizontalView row = new CategoryGroupHorizontalView(getActivity(), offersItem.getTitle(), offersItem.getProducts());
                row.setOnProductClickListener(this);
                itemsHolder.addView(row);
            }
        }
    }

    private void showLoading() {
        circularProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
    }

    @Override
    public void onProductClick(ProductModel productModel) {
        ProductActivity.show(getActivity(), productModel);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (offersReq != null) offersReq.cancel();
    }

}
