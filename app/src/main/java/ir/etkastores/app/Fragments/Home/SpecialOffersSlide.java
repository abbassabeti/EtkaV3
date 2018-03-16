package ir.etkastores.app.Fragments.Home;

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
import ir.etkastores.app.Activities.ProductActivity;
import ir.etkastores.app.Models.ProductModel;
import ir.etkastores.app.Models.home.OffersItemModel;
import ir.etkastores.app.Models.home.OffersResponseModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.CategoryGroupHorizontalView;
import ir.etkastores.app.UI.Views.MessageView;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 12/2/17.
 */

public class SpecialOffersSlide extends Fragment implements PageTrigger, CategoryGroupHorizontalView.OnProductClickListener {

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

    boolean isFirstSelect = true;

    private Call<OffersResponseModel> offersReq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home_slide, container, false);
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //we call this method here, because this fragment is the first slide of fragment
        onPageSelected();
    }

    private void initViews() {
        loadOffers();
    }

    @Override
    public void onPageSelected() {
        if (!isFirstSelect) return;
        isFirstSelect = false;
        initViews();
    }

    private void loadOffers() {
        showLoading();
        offersReq = ApiProvider.getAuthorizedApi().getOffers("offers");
        offersReq.enqueue(new Callback<OffersResponseModel>() {
            @Override
            public void onResponse(Call<OffersResponseModel> call, Response<OffersResponseModel> response) {
                if (!isAdded()) return;
                hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().getTotalItemsCount() == 0) {
                        showMessageView(getResources().getString(R.string.thereIsNotResultAvailable), false);
                    } else {
                        addItems(response.body().getItems());
                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<OffersResponseModel> call, Throwable throwable) {
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

    private void addItems(List<OffersItemModel> items) {
        for (OffersItemModel offersItem : items) {
            CategoryGroupHorizontalView row = new CategoryGroupHorizontalView(getActivity(), offersItem.getTitle(), offersItem.getProducts());
            row.setOnProductClickListener(this);
            itemsHolder.addView(row);

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
