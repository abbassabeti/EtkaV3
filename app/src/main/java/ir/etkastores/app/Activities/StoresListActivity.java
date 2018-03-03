package ir.etkastores.app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.etkastores.app.Adapters.RecyclerViewAdapters.StoresRecyclerAdapter;
import ir.etkastores.app.Models.hekmat.HekmatProductModel;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.UI.Views.EtkaToolbar;
import ir.etkastores.app.data.StoresManager;

public class StoresListActivity extends BaseActivity implements EtkaToolbar.EtkaToolbarActionsListener,
        StoresRecyclerAdapter.OnStoreSelectListener,
        StoresManager.StoresCallback {

    private static final int OPEN_STORE_MODE = 1;
    private static final int SELECT_STORE_MODE = 2;
    private static final String MODE = "MODE";
    private static final String PRODUCT_MODEL = "PRODUCT_MODEL";

    public static final int SELECT_STORE_REQ_CODE = 1005;
    public static final String SELECTED_STORE = "SELECTED_STORE";

    public static void showForOpenStore(Activity activity, HekmatProductModel productModel) {
        Intent intent = new Intent(activity, StoresListActivity.class);
        intent.putExtra(MODE, OPEN_STORE_MODE);
        intent.putExtra(PRODUCT_MODEL, new Gson().toJson(productModel));
        activity.startActivity(intent);
    }

    public static void showForSelectStore(Activity activity) {
        Intent intent = new Intent(activity, StoresListActivity.class);
        intent.putExtra(MODE, SELECT_STORE_MODE);
        activity.startActivityForResult(intent, SELECT_STORE_REQ_CODE);
    }

    public static void showForSelectStore(Fragment fragment) {
        Intent intent = new Intent(fragment.getActivity(), StoresListActivity.class);
        intent.putExtra(MODE, SELECT_STORE_MODE);
        fragment.startActivityForResult(intent, SELECT_STORE_REQ_CODE);
    }

    @BindView(R.id.toolbar)
    EtkaToolbar toolbar;

    @BindView(R.id.storesRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.searchInputEt)
    EditText searchInputEt;

    private StoresRecyclerAdapter adapter;
    private int mode;
    private HekmatProductModel hekmatProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores_list);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mode = getIntent().getExtras().getInt(MODE, OPEN_STORE_MODE);
        hekmatProductModel = HekmatProductModel.fromJson(getIntent().getExtras().getString(PRODUCT_MODEL));
        toolbar.setActionListeners(this);
        if (hekmatProductModel != null){
            toolbar.setTitle(R.string.distributionCenters);
        }else{
            toolbar.setTitle(R.string.selectStore);
        }
        adapter = new StoresRecyclerAdapter(this);
        adapter.setOnStoreSelectListener(this);
        recyclerView.setAdapter(adapter);
        searchInputEt.setEnabled(false);
        searchInputEt.addTextChangedListener(searchTextWatcher);
        recyclerView.requestFocus();
        StoresManager.getInstance().fetchStores(this);
    }

    @Override
    public void onToolbarBackClick() {
        onBackPressed();
    }

    @Override
    public void onActionClick(int actionCode) {

    }

    @Override
    public void onStoreSelect(StoreModel store) {
        if (mode == OPEN_STORE_MODE) {
            StoreActivity.show(this,store);
        } else if (mode == SELECT_STORE_MODE) {

        }
    }

    @Override
    public void onStoresFetchSuccess(List<StoreModel> stores) {
        List<StoreModel> items = new ArrayList<>();
        if (hekmatProductModel != null){
            for (int id : hekmatProductModel.getStores()){
                for (StoreModel store: stores){
                    if (store.getId() == id){
                        items.add(store);
                        break;
                    }
                }
            }
        }else{
            items = stores;
        }
        adapter.setStores(items);
        searchInputEt.setEnabled(true);
    }

    @Override
    public void onStoresFetchError() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mode == SELECT_STORE_MODE) {

        }
    }

    TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (searchInputEt.getText().length()>2){
                adapter.filterKeyword(searchInputEt.getText().toString());
            }
        }
    };

}
