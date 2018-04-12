package ir.etkastores.app.utils;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import ir.etkastores.app.data.StoresManager;
import ir.etkastores.app.models.store.StoreModel;
import ir.etkastores.app.services.StoresGeofenceTransitionsIntentService;

/**
 * Created by garshasbi on 4/9/18.
 */

public class StoresGeofencingManager {

    private final static String GEOFENCING_IS_ENABLED = "GEOFENCING_IS_ENABLED";

    private static StoresGeofencingManager instance;

    public static StoresGeofencingManager getInstance(Activity activity) {
        if (instance == null) instance = new StoresGeofencingManager(activity);
        return instance;
    }

    private PendingIntent pendingIntent;
    private GeofencingClient geofencingClient;
    private GeofencingRequest geofencingRequest;
    private List<Geofence> geofences;
    private Activity activity;

    private StoresGeofencingManager(Activity activity) {
        this.activity = activity;
        Intent intent = new Intent(activity, StoresGeofenceTransitionsIntentService.class);
        pendingIntent = PendingIntent.getService(activity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        geofencingClient = LocationServices.getGeofencingClient(activity);
        geofencingRequest = getGeofencingRequest();
    }

    private void initGeofenceList() {
        if (geofences == null) {
            geofences = new ArrayList<>();
            for (StoreModel storeModel : StoresManager.getInstance().getStores()){
                if (storeModel.isGeofencingEnabled()){
                    geofences.add(new Geofence.Builder()
                            .setCircularRegion(storeModel.getLatitude(), storeModel.getLongitude(), storeModel.getGeofencingRadius())
                            .setRequestId(String.valueOf(storeModel.getId()))
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                            .build());
                }
            }
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        initGeofenceList();
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
    }

    public void enable() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Stores geofencing add", "Success");
                DiskDataHelper.putBool(GEOFENCING_IS_ENABLED,true);
            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Stores geofencing add", "Failure! ");
                e.printStackTrace();
            }
        });
    }

    private void disable() {
        geofencingClient.removeGeofences(pendingIntent);
        DiskDataHelper.putBool(GEOFENCING_IS_ENABLED,false);
    }

    public boolean isEnabled(){
        return DiskDataHelper.getBool(GEOFENCING_IS_ENABLED);
    }

}
