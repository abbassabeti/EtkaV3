package ir.etkastores.app.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by garshasbi on 3/16/18.
 */

public class GeolocationUtils {

    public static double calculateDistanceBetweenTwoLocationInMeters(LatLng source, LatLng destination) {

        double earthRadius = 3958.75;
        double lat_b = destination.latitude;
        double lat_a = source.latitude;
        double lng_b = destination.longitude;
        double lng_a = source.longitude;
        double latDiff = Math.toRadians(lat_b-lat_a);
        double lngDiff = Math.toRadians(lng_b-lng_a);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

}
