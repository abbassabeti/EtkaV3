package ir.etkastores.app.fragments.searchFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.ProductsRecyclerAdapter;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.models.search.ProductSearchResponseModel;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by garshasbi on 4/16/18.
 */

public class ProductsListFragment extends Fragment implements ProductsRecyclerAdapter.ProductsRecyclerCallbacks {

    private final static String REQUEST_MODEL = "REQUEST_MODEL";

    public static ProductsListFragment newInstance(SearchProductRequestModel searchProductRequestModel) {
        ProductsListFragment fragment = new ProductsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REQUEST_MODEL, new Gson().toJson(searchProductRequestModel));
        fragment.setArguments(bundle);
        return fragment;
    }

    private View view;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    @BindView(R.id.messageView)
    MessageView messageView;

    private SearchProductRequestModel requestModel;
    private Call<OauthResponse<ProductSearchResponseModel>> req;
    private ProductsRecyclerAdapter productsAdapter;
    private final int MAX_PRODUCT_NEEDED = 20;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestModel = SearchProductRequestModel.fromJson(getArguments().getString(REQUEST_MODEL));
        requestModel.setPage(1);
        requestModel.setTake(MAX_PRODUCT_NEEDED);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_categories, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        return view;
    }

    private void initViews() {
        productsAdapter = new ProductsRecyclerAdapter(getActivity(), this);
        recyclerView.setAdapter(productsAdapter);
        loadProducts();
    }

    private void loadProducts() {
        showLoading();
        messageView.hide();
        req = ApiProvider.getAuthorizedApi().searchProduct(requestModel);
        productsAdapter.setLoadMoreEnabled(false);
        req.enqueue(new Callback<OauthResponse<ProductSearchResponseModel>>() {
            @Override
            public void onResponse(Call<OauthResponse<ProductSearchResponseModel>> call, Response<OauthResponse<ProductSearchResponseModel>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        productsAdapter.addItems(response.body().getData().getItems());
                        if (productsAdapter.getItemCount() == 0) {
                            showProductErrorMessage(getActivity().getResources().getString(R.string.thereIsNotResultAvailable), false);
                        }
                        if (response.body().getData().getItems().size() == MAX_PRODUCT_NEEDED) {
                            productsAdapter.setLoadMoreEnabled(true);
                            requestModel.setPage(requestModel.getPage() + 1);
                        }
                        if (response.body().getData().getTotalItemsCount() == 0) {
                            showProductErrorMessage(getResources().getString(R.string.thereIsNotResultAvailable), false);
                        }
                    } else {
                        showProductErrorMessage(response.body().getMeta().getMessage(), true);
                    }
                } else {
                    onFailure(null, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<ProductSearchResponseModel>> call, Throwable t) {
                if (!isAdded() || call.isCanceled()) return;
                hideLoading();
                showProductErrorMessage(getResources().getString(R.string.errorHappendInReceivingData), true);
            }
        });
    }

    public void showProductErrorMessage(String message, boolean showRetry) {
        String messageText = getResources().getString(R.string.errorHappendInReceivingData);
        if (!TextUtils.isEmpty(message)) {
            messageText = message;
        }
        if (showRetry && productsAdapter != null && productsAdapter.getItemCount() == 0) {
            messageView.show(R.drawable.ic_warning_orange_48dp, messageText, getResources().getString(R.string.retry), new MessageView.OnMessageViewButtonClick() {
                @Override
                public void onMessageViewButtonClick() {
                    loadProducts();
                }
            });
        } else if (!showRetry && productsAdapter != null && productsAdapter.getItemCount() == 0) {
            messageView.show(R.drawable.ic_warning_orange_48dp, messageText, getResources().getString(R.string.back), new MessageView.OnMessageViewButtonClick() {
                @Override
                public void onMessageViewButtonClick() {
                    getActivity().onBackPressed();
                }
            });
        } else {
            if (showRetry) {
                final MessageDialog dialog = MessageDialog.warningRetry(getResources().getString(R.string.error), message);
                dialog.show(getChildFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
                    @Override
                    public void onDialogMessageButtonsClick(int button) {
                        dialog.getDialog().cancel();
                        loadProducts();
                    }

                    @Override
                    public void onDialogMessageDismiss() {

                    }
                });
            } else {
                Toaster.show(getActivity(), message);
            }
        }
    }

    private void showLoading() {
        if (requestModel == null || requestModel.getPage() == 1)
            circularProgress.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        circularProgress.setVisibility(View.GONE);
        linearProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoadMore() {
        loadProducts();
    }

    @Override
    public void onProductItemClick(ProductModel productModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.SelectProductFromSearch);
        if (productModel.getRelatedProducts() == null || productModel.getRelatedProducts().size() == 0)
            productModel.setRelatedProducts(productsAdapter.getItems());
        ProductActivity.show(getActivity(), productModel);
    }

    @Override
    public void onProductSavedDeleteClick(ProductModel productModel) {

    }

    public void refreshResult(SearchProductRequestModel searchProductRequestModel) {
        if (req != null && req.isExecuted()) req.cancel();
        requestModel = searchProductRequestModel;
        productsAdapter.clear();
        requestModel.setPage(1);
        requestModel.setTake(MAX_PRODUCT_NEEDED);
        loadProducts();
    }

    public SearchProductRequestModel getSearchRequestModel() {
        return requestModel;
    }

}
