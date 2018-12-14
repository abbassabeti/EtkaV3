package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.R;
import ir.etkastores.app.data.ProfileManager;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.webServices.ApiStatics;

public class InStoreModeActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private static String MODEL = "MODEL";

    public static void show(Context context, StoreModel storeModel) {
        Intent intent = new Intent(context, InStoreModeActivity.class);
        intent.putExtra(MODEL, storeModel.toJson());
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.inStoreMapButton)
    View inStoreMapButton;

    @BindView(R.id.inStoreSurveyButton)
    View inStoreSurveyButton;

    @BindView(R.id.inStoreOffersButton)
    View inStoreOffersButton;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_store_mode);
        ButterKnife.bind(this);
        storeModel = StoreModel.fromJson(getIntent().getStringExtra(MODEL));
        initViews();
    }

    private void initViews() {
        toolbar.setTitle(String.format(getResources().getString(R.string.InStoreModeForBranchX), storeModel.getName()));
        toolbar.setActionListeners(this);
        if (storeModel.isHasInStoreMap()) {
            onInStoreMapButtonClick();
        } else if (storeModel.isHasInStoreSurvey()) {
            onInStoreSurveyButtonClick();
        } else {
            onInStoreOffersButtonClick();
        }

        if (!storeModel.isHasInStoreMap()) {
            inStoreMapButton.setVisibility(View.INVISIBLE);
        }

        if (!storeModel.isHasInStoreSurvey()) {
            inStoreSurveyButton.setVisibility(View.INVISIBLE);
        }

        if (!storeModel.isHasInStoreOffer()) {
            inStoreOffersButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.inStoreMapButton)
    public void onInStoreMapButtonClick() {
        if (inStoreMapButton.isSelected() || !storeModel.isHasInStoreMap()) return;
        setAllToDefault();
        inStoreMapButton.setBackgroundResource(R.color.colorPrimaryDark);
        inStoreMapButton.setSelected(true);
        loadUrl(ApiStatics.getInStoreMapUrl(storeModel.getCode()));
    }

    @OnClick(R.id.inStoreSurveyButton)
    public void onInStoreSurveyButtonClick() {

        if (!storeModel.isHasInStoreSurvey()) return;
        if (ProfileManager.getInstance().isGuest()) {
            LoginWithSMSActivity.show(this);
            return;
        }

        if (inStoreSurveyButton.isSelected()) return;
        setAllToDefault();
        inStoreSurveyButton.setBackgroundResource(R.color.colorPrimaryDark);
        inStoreSurveyButton.setSelected(true);
        loadUrl(ApiStatics.getInStoreSurveyUrl(storeModel.getCode()));
    }

    @OnClick(R.id.inStoreOffersButton)
    public void onInStoreOffersButtonClick() {
        if (inStoreOffersButton.isSelected() || !storeModel.isHasInStoreOffer()) return;
        setAllToDefault();
        inStoreOffersButton.setBackgroundResource(R.color.colorPrimaryDark);
        inStoreOffersButton.setSelected(true);
        loadUrl(ApiStatics.getInStoreOffersUrl(storeModel.getCode()));
    }

    private void setAllToDefault() {
        inStoreMapButton.setBackgroundResource(R.color.transparent);
        inStoreMapButton.setSelected(false);
        inStoreSurveyButton.setBackgroundResource(R.color.transparent);
        inStoreSurveyButton.setSelected(false);
        inStoreOffersButton.setBackgroundResource(R.color.transparent);
        inStoreOffersButton.setSelected(false);
    }

    private void loadUrl(final String url) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isFinishing()) return;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (isFinishing()) return;
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (isFinishing()) return;
                progressBar.setVisibility(View.GONE);
                showLoadingErrorDialog(url);
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private void showLoadingErrorDialog(final String url) {
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error), getResources().getString(R.string.errorInLoadingInStoreMode));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON) {
                    loadUrl(url);
                } else {
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }
}
