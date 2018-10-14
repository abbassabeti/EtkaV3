package ir.etkastores.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import io.michaelrocks.paranoid.Obfuscate;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.adapters.recyclerViewAdapters.StoresRecyclerAdapter;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.models.hekmat.HekmatProductModel;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.ui.views.EtkaToolbar;
import ir.etkastores.app.utils.AdjustHelper;

@Obfuscate
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

    @Override
    protected void onResume() {
        super.onResume();
        EtkaApp.getInstance().screenView("Stores List Activity");
    }

    private void initViews() {
        mode = getIntent().getExtras().getInt(MODE, OPEN_STORE_MODE);
        hekmatProductModel = HekmatProductModel.fromJson(getIntent().getExtras().getString(PRODUCT_MODEL));
        toolbar.setActionListeners(this);
        if (hekmatProductModel != null) {
            toolbar.setTitle(R.string.distributionCenters);
        } else {
            toolbar.setTitle(R.string.selectStore);
        }
        adapter = new StoresRecyclerAdapter(this);
        adapter.setOnStoreSelectListener(this);
        recyclerView.setAdapter(adapter);
        searchInputEt.setEnabled(false);
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
            AdjustHelper.sendAdjustEvent(AdjustHelper.OpenStoreFromList);
            StoreActivity.show(this, store);
        } else if (mode == SELECT_STORE_MODE) {
            Intent intent = new Intent();
            intent.putExtra(SELECTED_STORE, new Gson().toJson(store));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onStoresFetchSuccess(List<StoreModel> stores) {
        List<StoreModel> items = new ArrayList<>();
        if (hekmatProductModel != null) {
            for (StoreModel s : stores) {
                for (long id : hekmatProductModel.getStores()) {
                    if (s.getId() == id) {
                        items.add(s);
                        break;
                    }
                }
            }
        } else {
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
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @OnTextChanged(R.id.searchInputEt)
    public void onSearchTextChanged(CharSequence s) {
        adapter.filterKeyword(searchInputEt.getText().toString());
    }

}
