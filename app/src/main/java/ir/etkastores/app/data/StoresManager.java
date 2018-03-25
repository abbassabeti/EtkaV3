package ir.etkastores.app.data;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by garshasbi on 2/21/18.
 */

public class StoresManager {

    private static StoresManager instance;

    private List<StoreModel> stores;
    private Call<OauthResponse<List<StoreModel>>> req;

    private StoresCallback storesCallback;

    private final int MAX_RETRY_COUNT = 5;

    private int retryCounter = 0;

    public static StoresManager getInstance() {
        if (instance == null) instance = new StoresManager();
        return instance;
    }

    private StoresManager() {
        stores = new ArrayList<>();
    }

    public void setStores(List<StoreModel> stores) {
        this.stores = stores;
    }

    public List<StoreModel> getStores() {
        return stores;
    }

    private void loadData() {
        req = ApiProvider.getAuthorizedApi().getStores();
        req.enqueue(new Callback<OauthResponse<List<StoreModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<StoreModel>>> call, Response<OauthResponse<List<StoreModel>>> response) {
                if (response.isSuccessful() && response.body().isSuccessful()) {
                    stores = response.body().getData();
                    if (storesCallback != null) storesCallback.onStoresFetchSuccess(stores);
                } else {
                    onFailure(null, null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<StoreModel>>> call, Throwable throwable) {
                if (call != null && call.isCanceled()) return;
                if (retryCounter <= MAX_RETRY_COUNT) {
                    retryCounter += 1;
                    loadData();
                } else {
                    if (storesCallback != null) storesCallback.onStoresFetchError();
                }
            }
        });
    }

    public void fetchStores(StoresCallback callback) {
        if (req != null) req.cancel();
        if (stores != null && stores.size()>0){
            if (callback != null) callback.onStoresFetchSuccess(stores);
        }else{
            this.storesCallback = callback;
            retryCounter = 0;
            loadData();
        }
    }

    public interface StoresCallback {
        void onStoresFetchSuccess(List<StoreModel> stores);

        void onStoresFetchError();
    }

}
