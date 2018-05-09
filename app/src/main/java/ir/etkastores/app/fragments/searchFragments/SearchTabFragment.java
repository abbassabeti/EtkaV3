package ir.etkastores.app.fragments.searchFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.activities.ProductActivity;
import ir.etkastores.app.activities.ScannerActivity;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.activities.CategoriesFilterActivity;
import ir.etkastores.app.activities.StoreActivity;
import ir.etkastores.app.adapters.recyclerViewAdapters.CategoryRecyclerAdapter;
import ir.etkastores.app.data.StaticsData;
import ir.etkastores.app.models.CategoryModel;
import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.R;
import ir.etkastores.app.models.search.SearchProductRequestModel;
import ir.etkastores.app.ui.Toaster;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.IntentHelper;
import ir.etkastores.app.utils.StringUtils;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/1/17.
 */

public class SearchTabFragment extends Fragment implements TextView.OnEditorActionListener, CategoryRecyclerAdapter.OnCategoryItemClickListener {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.searchEditText)
    EditText searchInput;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.circularProgress)
    ProgressBar circularProgress;

    private Call<OauthResponse<List<CategoryModel>>> request;
    private CategoryRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search_tab, container, false);
            ButterKnife.bind(this, view);
            initViews();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Search Tab Fragment");
    }

    private void initViews() {
        if (BuildConfig.DEBUG) {
            searchInput.setText("#6260100601000");
        }
        loadCategories();
        searchInput.setOnEditorActionListener(this);
    }

    private void loadCategories() {
        adapter = new CategoryRecyclerAdapter(getActivity());
        adapter.setOnCategoryItemClickListener(this);
        recyclerView.setAdapter(adapter);
        request = ApiProvider.getAuthorizedApi().getCategoryAtLevel(1);
        showLoading();
        request.enqueue(new Callback<OauthResponse<List<CategoryModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<CategoryModel>>> call, Response<OauthResponse<List<CategoryModel>>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {
                        adapter.setData(response.body().getData());
                    } else {

                    }
                } else {
                    onFailure(call, null);
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<OauthResponse<List<CategoryModel>>> call, Throwable throwable) {
                if (!isAdded()) return;
                hideLoading();
            }
        });
    }

    @OnClick(R.id.scanButton)
    public void onScanCameraButtonClick() {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenBarcodeScanner);
        ScannerActivity.show(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {

            case ScannerActivity.SCAN_REQUEST_CODE:
                String format = data.getStringExtra(ScannerActivity.FORMAT);
                String code = data.getStringExtra(ScannerActivity.DATA);
                if (code.startsWith(StaticsData.etkaStoreScheme)){
                    IntentHelper.showWeb(getActivity(),code);
                }else{
                    ProductActivity.show(getActivity(),code);
                }
                Log.i("scanned code is", "" + format + " | " + code);
                break;

        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearchButtonClick();
            return true;
        }
        return false;
    }


    @OnClick(R.id.goButton)
    public void onSearchButtonClick() {
        if (TextUtils.isEmpty(searchInput.getText().toString())) {
            Toaster.show(getActivity(), R.string.writeSomeThingsToSearch);
            return;
        }
        String txt = searchInput.getText().toString();
        if (txt.startsWith("#")) {
            ProductActivity.show(getActivity(), StringUtils.toEnglishDigit(txt.replace("#", "")));
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.SearchKeyword);
            SearchProductRequestModel searchProductRequestModel = new SearchProductRequestModel();
            searchProductRequestModel.setTitle(searchInput.getText().toString());
            CategoriesFilterActivity.show(getActivity(),searchProductRequestModel);
        }
    }

    @Override
    public void onCategoryItemClick(CategoryModel model, int position) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.SelectCategoryFromSearch);
        CategoriesFilterActivity.show(getActivity(), model);
    }

    private void showLoading(){
        circularProgress.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        circularProgress.setVisibility(View.GONE);
    }

}
