package ir.etkastores.app.Fragments.SearchFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.CategoryActivity;
import ir.etkastores.app.Activities.ProductActivity;
import ir.etkastores.app.Activities.ScannerActivity;
import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.Models.search.SearchProductRequestModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Toaster;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.Utils.ActivityUtils;
import ir.etkastores.app.Utils.StringUtils;

/**
 * Created by Sajad on 9/1/17.
 */

public class SearchTabFragment extends Fragment implements TextView.OnEditorActionListener {

    private View view;

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.searchEditText)
    EditText searchInput;

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

    private void initViews() {
        if (BuildConfig.DEBUG){
            searchInput.setText("#6260100601000");
        }
        showCategories();
        searchInput.setOnEditorActionListener(this);
    }

    private void showCategories() {
        ActivityUtils.addChildFragment(this, R.id.searchContentFrame, CategoriesFragment.newInstance(0), "", false);
    }

    private void showAdvancedSearch() {
        ActivityUtils.replaceChildFragment(this, R.id.searchContentFrame, SearchAdvancedFragment.newInstance(), "", false);
    }

    @OnClick(R.id.scanButton)
    public void onScanCameraButtonClick() {
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
                ProductActivity.show(getActivity(), code, format);
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
            ProductActivity.show(getActivity(), StringUtils.toEnglishDigit(txt.replace("#", "")), "");
        } else {
            SearchProductRequestModel searchReq = new SearchProductRequestModel();
            searchReq.setTitle(searchInput.getText().toString());
            CategoryActivity.show(getActivity(), searchReq);
        }
    }

}
