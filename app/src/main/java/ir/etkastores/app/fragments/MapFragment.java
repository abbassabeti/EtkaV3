package ir.etkastores.app.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;
import ir.etkastores.app.activities.StoreActivity;
import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.utils.AdjustHelper;
import ir.etkastores.app.utils.FontUtils;
import ir.etkastores.app.utils.SuggestionArrayAdapter;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * Created by Sajad on 9/1/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        StoresManager.StoresCallback,
        AdapterView.OnItemClickListener,
        EasyPermissions.PermissionCallbacks, GoogleMap.OnMyLocationChangeListener {

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

    @BindView(R.id.findMyLocationButton)
    AppCompatImageView findMyLocationButton;

    @BindView(R.id.virtualTourButton)
    TextView virtualTourButton;

    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    private final static int PERMISSION_REQ_CODE = 1006;

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

    private void initViews() {
        if (!isAdded()) return;
        storesHashMap = new HashMap<>();
        markersHashMap = new HashMap<>();
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.683333, 51.416667), 4));

        if (StoresManager.getInstance().getStores().size() > 0) {
            addStoresToMap(StoresManager.getInstance().getStores());
        } else {
            StoresManager.getInstance().fetchStores(this);
        }

        if (!EasyPermissions.hasPermissions(getActivity(), permissions)) {
            findMyLocationButton.setVisibility(View.GONE);
            requestLocationPermission();
        } else {
            findMyLocationButton.setVisibility(View.VISIBLE);
            findUserLocation();
        }
        virtualTourButton.setTypeface(FontUtils.getBoldTypeFace());

    }

    private void addMarker(StoreModel store) {
        if (!isAdded()) return;
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(store.getLatitude(), store.getLongitude()));
        marker.icon(bitmapDescriptorFromVector(store.getIcon()));
        marker.anchor(0.5f, 0.5f);
        Marker m = map.addMarker(marker);
        storesHashMap.put(m, store);
        markersHashMap.put(store, m);
    }

    public void addStoresToMap(List<StoreModel> stores) {
        if (!isAdded()) return;
        List<SuggestionArrayAdapter.SearchViewItem> searchViewItems = new ArrayList<>();
        for (StoreModel store : stores) {
            addMarker(store);
            searchViewItems.add(new SuggestionArrayAdapter.SearchViewItem(store));
        }
        searchAdapter = new SuggestionArrayAdapter(getActivity(), searchViewItems);
        storeSearchInput.setAdapter(searchAdapter);
        storeSearchInput.setOnItemClickListener(this);
    }

    @Override
    public void onStoresFetchSuccess(List<StoreModel> stores) {
        if (!isAdded()) return;
        addStoresToMap(stores);
    }

    @Override
    public void onStoresFetchError() {

    }

    public void animateCameraToStore(StoreModel store) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(store.getLatitude(), store.getLongitude()), 17));
        onMarkerClick(markersHashMap.get(store));
    }

    @OnClick(R.id.storeInfoHolder)
    void onStoreInfoClick() {
        AdjustHelper.sendAdjustEvent(AdjustHelper.OpenStoreFromMap);
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
        marker.setIcon(bitmapDescriptorFromVector(R.drawable.ic_selected_marker));
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
        EtkaApp.getInstance().screenView("Map Fragment");
        try {
            if (mapView != null) mapView.onResume();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void clearSelectedMarker() {
        if (selectedStore != null) {
            if (!isAdded()) return;
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
            err.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mapView != null) mapView.onDestroy();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            if (mapView != null) mapView.onLowMemory();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AdjustHelper.sendAdjustEvent(AdjustHelper.SelectStoreFromSearchSuggestion);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(storeSearchInput.getWindowToken(), 0);
        animateCameraToStore(searchAdapter.getItem(position).getStoreModel());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        findMyLocationButton.setVisibility(View.VISIBLE);
        findUserLocation();
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void requestLocationPermission() {
        EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, PERMISSION_REQ_CODE, permissions)
                        .setRationale(R.string.locationPermissionRationalMessage)
                        .setPositiveButtonText(R.string.continue_)
                        .setNegativeButtonText(R.string.cancel)
                        .build());
    }

    @SuppressLint("MissingPermission")
    private void findUserLocation() {
        try {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(this);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        try {
            map.setOnMyLocationChangeListener(null);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @OnClick(R.id.findMyLocationButton)
    public void onFindMyLocationButtonClick() {
        if (map.getMyLocation() != null) {
            onMyLocationChange(map.getMyLocation());
        } else {
            AdjustHelper.sendAdjustEvent(AdjustHelper.FindMyLocationInMap);
            findUserLocation();
        }
    }

}
