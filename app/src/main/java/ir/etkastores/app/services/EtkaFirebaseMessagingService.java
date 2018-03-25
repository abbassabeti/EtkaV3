package ir.etkastores.app.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.models.notification.NotificationModel;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/11/18.
 */

public class EtkaFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("FCM", "messae received" + remoteMessage.getFrom());

        if (remoteMessage.getData() == null) return;

        if (!TextUtils.isEmpty(remoteMessage.getData().get("etkaNotificationObject"))) {
            NotificationModel notificationModel = NotificationModel.fromJson(remoteMessage.getData().get("etkaNotificationObject"));
            showNotification(notificationModel);
        }

    }

    public void showNotification(NotificationModel notification) {

        if (notification.getIntent() == null) return;

        Context context = EtkaApp.getInstance().getApplicationContext();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(EtkaApp.getInstance().getApplicationContext(), "notify_001");
        PendingIntent pendingIntent = PendingIntent.getActivity(EtkaApp.getInstance().getApplicationContext(), 0, notification.getIntent(), 0);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_etka_notify);
        mBuilder.setContentTitle(notification.getTitle());
        mBuilder.setContentText(notification.getMessage());
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setAutoCancel(true);
        mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        mBuilder.setLights(Color.RED, 1, 1);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(notification.getAction(), mBuilder.build());
    }

}
