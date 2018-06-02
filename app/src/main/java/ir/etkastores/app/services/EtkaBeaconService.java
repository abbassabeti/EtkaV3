//package ir.etkastores.app.services;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.github.florent37.rxbeacon.RxBeacon;
//import com.github.florent37.rxbeacon.RxBeaconRange;
//
//import org.altbeacon.beacon.Beacon;
//
//import io.reactivex.functions.Consumer;
//import ir.etkastores.app.models.notification.NotificationModel;
//
//public class EtkaBeaconService extends Service {
//
//    public final static String IBeaconParser = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
//    private final static String ETKA_BEACON_UUID = "d57092ac-dfaa-446c-8ef3-c81aa22815b5";
//    private final static int ETKA_BEACON_MAJOR = 1;
//    private final static int ETKA_BEACON_MINOR = 5000;
//
//    private int lastMajor = 0;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e("Beacon Service started", "....");
//        RxBeacon.with(this)
//                .addBeaconParser(IBeaconParser)
//                .beaconsInRegion()
//                .subscribe(new Consumer<RxBeaconRange>() {
//                    @Override
//                    public void accept(@NonNull RxBeaconRange rxBeaconRange) throws Exception {
//                        rxBeaconRange.getBeacons();
//                        if (rxBeaconRange.getBeacons() != null)
//                            for (Beacon beacon : rxBeaconRange.getBeacons()) {
//                                if (isEtkaBeacon(beacon)) {
//                                    if (getMajor(beacon) == -1) return;
//                                    if (getMajor(beacon) == lastMajor) return;
//                                    NotificationModel notificationModel = NotificationModel.fromBeacon(beacon);
//                                    if (notificationModel != null){
//                                        EtkaFirebaseMessagingService.showNotification(notificationModel);
//                                        lastMajor = getMajor(beacon);
//                                    }
//                                }
//                            }
//                    }
//                });
//        return START_NOT_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    boolean isEtkaBeacon(Beacon beacon){
//        try {
//            if (beacon.getIdentifiers().get(0).toString().contentEquals(ETKA_BEACON_UUID)) {
//                if (String.valueOf(beacon.getIdentifiers().get(2)).contentEquals(String.valueOf(ETKA_BEACON_MINOR))) {
//                    return true;
//                }
//                return false;
//            }
//            return false;
//        } catch (Exception err) {
//            err.printStackTrace();
//            return false;
//        }
//    }
//
//    private int getMajor(Beacon beacon){
//        try {
//            return beacon.getId2().toInt();
//        }catch (Exception err){
//            return -1;
//        }
//    }
//
//}
