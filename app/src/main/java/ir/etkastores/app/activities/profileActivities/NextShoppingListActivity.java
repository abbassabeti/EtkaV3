package ir.etkastores.app.activities.profileActivities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.BaseActivity;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.ProductsRecyclerAdapter;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.ProductModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.ui.views.MessageView;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.DialogHelper;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Obfuscate
public class NextShoppingListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener, ProductsRecyclerAdapter.ProductsRecyclerCallbacks {

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, NextShoppingListActivity.class);
        activity.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.messageView)
    MessageView messageView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    @BindView(R.id.linearProgress)
    ProgressBar linearProgress;

    private Call<OauthResponse<List<ProductModel>>> savedProductsReq;
    private Call<OauthResponse<Long>> deleteProductReq;
    private ProductsRecyclerAdapter adapter;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfileManager.isGuest()) {
            finish();
            return;
        }
        setContentView(R.layout.activity_next_shopping_list);
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Next Shopping List Activity");
    }

    private void initViews() {
        adapter = new ProductsRecyclerAdapter(this, this);
        adapter.setNextShoppingListMode(true);
        recyclerView.setAdapter(adapter);
        loadProducts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (savedProductsReq != null) savedProductsReq.cancel();
        if (deleteProductReq != null) deleteProductReq.cancel();
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    private void showEmptyMessage() {
        messageView.show(R.drawable.ic_warning_orange_48dp, R.string.yourNextShoppingListIsEmpty, 0, null);
    }

    private void loadProducts() {
        showLoading();
        savedProductsReq = ApiProvider.getAuthorizedApi().getSavedProducts();
        savedProductsReq.enqueue(new Callback<OauthResponse<List<ProductModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<ProductModel>>> call, Response<OauthResponse<List<ProductModel>>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.addItems(response.body().getData());
                        if (adapter.getItemCount() == 0) showEmptyMessage();
                    } else {
                        showLoadingErrorDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<ProductModel>>> call, Throwable throwable) {
                if (isFinishing()) return;
                hideLoading();
                showLoadingErrorDialog(null);
            }
        });
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onProductItemClick(ProductModel productModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenProductFromNextShoppingList);
        if (productModel.getRelatedProducts() == null || productModel.getRelatedProducts().size() == 0)
            productModel.setRelatedProducts(adapter.getItems());
        ProductActivity.show(this, productModel);
    }

    @Override
    public void onProductSavedDeleteClick(final ProductModel productModel) {
        final MessageDialog messageDialog = MessageDialog.sureToDeleteProductFromNextShoppingList();
        messageDialog.show(getSupportFragmentManager(), true, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    deleteProductFromList(productModel);
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showLoadingErrorDialog(String message) {
        String msg = message;
        if (TextUtils.isEmpty(msg))
            msg = getResources().getString(R.string.errorInLoadingNextShoppingList);
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error), msg);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    initViews();
                } else {
                    if (adapter.getItemCount() == 0) {
                        finish();
                    }
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }


    private ProductModel tempProductForDelete;

    private void deleteProductFromList(ProductModel productModel) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.DeleteFromNextShoppingList);
        tempProductForDelete = productModel;
        loadingDialog = DialogHelper.showLoading(NextShoppingListActivity.this, R.string.inDeletingProductFromYourNextShoppingList);
        deleteProductReq = ApiProvider.getAuthorizedApi().deleteSavedProduct(productModel.getId());
        deleteProductReq.enqueue(new Callback<OauthResponse<Long>>() {
            @Override
            public void onResponse(Call<OauthResponse<Long>> call, Response<OauthResponse<Long>> response) {
                if (isFinishing()) return;
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        Toaster.show(NextShoppingListActivity.this, R.string.productDeletedSuccessfully);
                        adapter.deleteItem(adapter.getItems().indexOf(tempProductForDelete));
                        tempProductForDelete = null;
                    } else {
                        showDeleteErrorDialog(response.body().getMeta().getMessage());
                    }
                } else {
                    onFailure(call, null);
                }
                loadingDialog.cancel();
            }

            @Override
            public void onFailure(Call<OauthResponse<Long>> call, Throwable throwable) {
                if (isFinishing()) return;
                loadingDialog.cancel();
                showDeleteErrorDialog(null);
            }
        });
    }

    private void showDeleteErrorDialog(String message) {
        String msg = message;
        if (TextUtils.isEmpty(msg))
            msg = getResources().getString(R.string.errorInDeletingProductTryLater);
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error), msg);
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    deleteProductFromList(tempProductForDelete);
                } else {
                    if (adapter.getItemCount() == 0) {
                        finish();
                    }
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    private void showLoading() {
        if (adapter.getItemCount() == 0) circularProgress.setVisibility(View.VISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        linearProgress.setVisibility(View.GONE);
        circularProgress.setVisibility(View.GONE);
    }

}