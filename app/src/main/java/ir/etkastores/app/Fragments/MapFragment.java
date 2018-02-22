package ir.etkastores.app.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.Activities.StoreActivity;
import ir.etkastores.app.Models.store.StoreModel;
import ir.etkastores.app.R;
import ir.etkastores.app.Utils.SuggestionArrayAdapter;
import ir.etkastores.app.data.StoresManager;

/**
 * Created by Sajad on 9/1/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        StoresManager.StoresCallback,
        AdapterView.OnItemClickListener {

    private View view;

    private GoogleMap map;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.storeInfoHolder)
    View storeInfoHolder;

    @BindView(R.id.infoStoreName)
    TextView storeName;

    @BindView(R.id.storeSearchInput)
    AutoCompleteTextView storeSearchInput;

    private StoreModel selectedStore;
    private Marker selectedMarker;
    private HashMap<Marker, StoreModel> storesHashMap;
    private HashMap<StoreModel, Marker> markersHashMap;
    private SuggestionArrayAdapter searchAdapter;

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

    private LatLng chamranPosition = new LatLng(35.686169, 51.4065863);

    private void initViews() {
        storesHashMap = new HashMap<>();
        markersHashMap = new HashMap<>();
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);

        if (StoresManager.getInstance().getStores().size() > 0) {
            addStoresToMap(StoresManager.getInstance().getStores());
        } else {
            StoresManager.getInstance().fetchStores(this);
        }

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(chamranPosition, 15));
    }

    private void addMarker(StoreModel store) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(store.getLatitude(), store.getLongitude()));
        marker.icon(bitmapDescriptorFromVector(store.getIcon()));
        marker.anchor(0.5f, 0.5f);
        Marker m = map.addMarker(marker);
        storesHashMap.put(m, store);
        markersHashMap.put(store,m);
    }

    public void addStoresToMap(List<StoreModel> stores){
        List<SuggestionArrayAdapter.SearchViewItem> searchViewItems = new ArrayList<>();
        for (StoreModel store : stores){
            addMarker(store);
            searchViewItems.add(new SuggestionArrayAdapter.SearchViewItem(store));
        }
        searchAdapter = new SuggestionArrayAdapter(getActivity(),searchViewItems);
        storeSearchInput.setAdapter(searchAdapter);
        storeSearchInput.setOnItemClickListener(this);
    }

    @Override
    public void onStoresFetchSuccess(List<StoreModel> stores) {
        addStoresToMap(stores);
    }

    @Override
    public void onStoresFetchError() {

    }

    public void animateCameraToStore(StoreModel store){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(store.getLatitude(),store.getLongitude()), 17));
        onMarkerClick(markersHashMap.get(store));
    }

    @OnClick(R.id.storeInfoHolder)
    void onStoreInfoClick() {
        StoreActivity.show(getActivity(), selectedStore);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(getActivity(), vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        clearSelectedMarker();
        selectedStore = storesHashMap.get(marker);
        storeName.setText(selectedStore.getName());
        storeInfoHolder.setVisibility(View.VISIBLE);
        selectedMarker = marker;
        marker.setIcon(bitmapDescriptorFromVector(R.drawable.ic_selected_marker_35dp));
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        clearSelectedMarker();
        storeInfoHolder.setVisibility(View.GONE);
        storeSearchInput.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (mapView != null) mapView.onResume();
        } catch (Exception err) {
            Log.e("failure", "map stores");
        }
    }

    private void clearSelectedMarker(){
        if (selectedStore != null){
            selectedMarker.setIcon(bitmapDescriptorFromVector(selectedStore.getIcon()));
            selectedMarker = null;
            selectedStore = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mapView != null) mapView.onPause();
        } catch (Exception err) {
            Log.e("failure", "map stores");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mapView != null) mapView.onDestroy();
        } catch (Exception err) {
            Log.e("failure", "map stores");
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            if (mapView != null) mapView.onLowMemory();
        } catch (Exception err) {
            Log.e("failure", "map stores");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(storeSearchInput.getWindowToken(), 0);
        animateCameraToStore(searchAdapter.getItem(position).getStoreModel());
    }

}
