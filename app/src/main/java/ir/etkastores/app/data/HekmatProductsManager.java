package ir.etkastores.app.data;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.models.OauthResponse;
import ir.etkastores.app.models.hekmat.HekmatModel;
import ir.etkastores.app.models.hekmat.HekmatProductModel;
import ir.etkastores.app.webServices.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by garshasbi on 4/17/18.
 */

public class HekmatProductsManager {

    private static HekmatProductsManager instance;

    public static HekmatProductsManager getInstance() {
        if (instance == null) instance = new HekmatProductsManager();
        return instance;
    }

    private List<HekmatModel> products;
    private int retryCounter = 5;
    private boolean isLoading = false;
    private OnHekmatCallbacksListener callbacksListener;
    Long filteredStoreId = null;

    private HekmatProductsManager(){
        request();
    }

    private void request(){
        isLoading = true;
        ApiProvider.getAuthorizedApi().getHekmat().enqueue(new Callback<OauthResponse<List<HekmatModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<HekmatModel>>> call, Response<OauthResponse<List<HekmatModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        products = response.body().getData();
                        retryCounter = 5;
                        isLoading = false;
                        if (filteredStoreId != null){
                            if (callbacksListener != null) callbacksListener.onProductsLoaded(getFilteredByIdProducts(filteredStoreId));
                        }else{
                            if (callbacksListener != null) callbacksListener.onProductsLoaded(products);
                        }
                    }else{
                        onFailure(call,null);
                    }
                }else{
                    onFailure(call,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<HekmatModel>>> call, Throwable throwable) {
                if (retryCounter>0){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            retryCounter --;
                            request();
                        }
                    },500);
                }else{
                    isLoading = false;
                    if (callbacksListener != null) callbacksListener.onLoadFailure();
                }
            }
        });
    }

    public void getProducts(OnHekmatCallbacksListener callbacksListener,Long filteredStoreId) {
        if (products != null){
            this.filteredStoreId = filteredStoreId;
            if (filteredStoreId != null){
                if (callbacksListener != null) callbacksListener.onProductsLoaded(getFilteredByIdProducts(filteredStoreId));
            }else{
                if (callbacksListener != null) callbacksListener.onProductsLoaded(products);
            }
            return;
        }else{
            this.filteredStoreId = filteredStoreId;
            this.callbacksListener = callbacksListener;
        }
        if (!isLoading){
            request();
        }
    }

    public interface OnHekmatCallbacksListener{
        void onProductsLoaded(List<HekmatModel> products);
        void onLoadFailure();
    }

    private List<HekmatModel> getFilteredByIdProducts(long storeId){
        List<HekmatModel> tmp = new ArrayList<>();
        for (HekmatModel h : products){
            for (HekmatProductModel hp : h.getProducts()){
                for (long hsid : hp.getStores()){
                    if (hsid == storeId){
                        tmp.add(h.getCopy());
                    }
                }
            }
        }
        return tmp;
    }

}
