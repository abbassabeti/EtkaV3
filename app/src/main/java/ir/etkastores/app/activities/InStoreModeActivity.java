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

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.dialogs.MessageDialog;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.IntentHelper;

@Obfuscate
public class InStoreModeActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener {

    private final static String STORE_MODEL = "STORE_MODEL";

    public static void show(Context context, StoreModel storeModel) {
        Intent intent = new Intent(context, InStoreModeActivity.class);
        intent.putExtra(STORE_MODEL, new Gson().toJson(storeModel));
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private StoreModel storeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_store_mode);
        storeModel = StoreModel.fromJson(getIntent().getExtras().getString(STORE_MODEL));
        ButterKnife.bind(this);
        toolbar.setActionListeners(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("In Store Mode Activity");
    }

    private void initViews() {
        toolbar.setTitle(String.format(getResources().getString(R.string.InStoreModeForBranchX), storeModel.getName()));
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
                showLoadingErrorDialog();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(storeModel.getInStoreModeUrl());
    }

    @Override
    public void onToolbarBackClick() {
        super.onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @OnClick(R.id.openInBrowserButton)
    public void onOpenInInBrowserClick(){
        IntentHelper.showWeb(this,storeModel.getInStoreModeUrl());
    }

    private void showLoadingErrorDialog() {
        final MessageDialog messageDialog = MessageDialog.warningRetry(getResources().getString(R.string.error),getResources().getString(R.string.errorInLoadingInStoreMode));
        messageDialog.show(getSupportFragmentManager(), false, new MessageDialog.MessageDialogCallbacks() {
            @Override
            public void onDialogMessageButtonsClick(int button) {
                if (button == RIGHT_BUTTON){
                    initViews();
                }else{
                    finish();
                }
                messageDialog.getDialog().cancel();
            }

            @Override
            public void onDialogMessageDismiss() {

            }
        });
    }

}
