package ir.etkastores.app.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.views.EtkaToolbar;

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
        toolbar.setTitle(String.format(getResources().getString(R.string.InStoreModeForBranchX),storeModel.getName()));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("loading finished", "....");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("loading started", "....");
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

}
