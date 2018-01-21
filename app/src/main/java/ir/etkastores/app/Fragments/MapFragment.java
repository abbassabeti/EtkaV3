package ir.etkastores.app.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.StoreActivity;
import ir.etkastores.app.Models.OauthResponse;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.WebService.ApiProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sajad on 9/1/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private View view;

    private GoogleMap map;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.storeInfoHolder)
    View storeInfoHolder;

    @BindView(R.id.infoStoreName)
    TextView storeName;

    private StoreModel selectedStore;
    private HashMap<Marker,StoreModel> storesHashMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initViews();
    }

    private LatLng chamranPosition = new LatLng(35.686169,51.4065863);

    private void initViews() {
        storesHashMap = new HashMap<>();
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);

        loadStores();

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(chamranPosition,15));
    }

    private void addMarker(StoreModel store){
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(store.getLatitude(),store.getLongitude()));
        if (store.getRanking().contentEquals("اتکا ممتاز")){
            marker.icon(bitmapDescriptorFromVector(R.drawable.ic_store_orange));
        }else if (store.getRanking().contentEquals("اتکا بازار")){
            marker.icon(bitmapDescriptorFromVector(R.drawable.ic_store_yellow));
        }else if (store.getRanking().contentEquals("اتکا محله")){
            marker.icon(bitmapDescriptorFromVector(R.drawable.ic_store_blue));
        }
        marker.anchor(0.5f, 0.5f);
        storesHashMap.put(map.addMarker(marker),store);;
    }

    @OnClick(R.id.storeInfoHolder)
    void onStoreInfoClick(){
        StoreActivity.show(getActivity(),selectedStore);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    Call<OauthResponse<List<StoreModel>>> storesRequest;
    private void loadStores(){
        storesRequest = ApiProvider.getAuthorizedApi().getStores();
        storesRequest.enqueue(new Callback<OauthResponse<List<StoreModel>>>() {
            @Override
            public void onResponse(Call<OauthResponse<List<StoreModel>>> call, Response<OauthResponse<List<StoreModel>>> response) {
                if (response.isSuccessful()){
                    if (response.body().isSuccessful()){
                        for (StoreModel store: response.body().getData()) addMarker(store);
                    }else{

                    }
                }else{
                    onFailure(null,null);
                }
            }

            @Override
            public void onFailure(Call<OauthResponse<List<StoreModel>>> call, Throwable t) {
                Log.e("failure","map stores");
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        selectedStore = storesHashMap.get(marker);
        storeName.setText(selectedStore.getName());
        storeInfoHolder.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        selectedStore = null;
        storeInfoHolder.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }
}
