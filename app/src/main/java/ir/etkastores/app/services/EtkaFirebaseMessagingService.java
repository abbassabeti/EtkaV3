package ir.etkastores.app.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.ConnectException;

import ir.etkastores.app.Activities.SplashActivity;
import ir.etkastores.app.EtkaApp;
import ir.etkastores.app.R;

/**
 * Created by Sajad on 2/11/18.
 */

public class EtkaFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("FCM","messae received"+remoteMessage.getFrom());
        showNotification(remoteMessage);
    }

    private void showNotification(RemoteMessage message){
        Context context = EtkaApp.getInstnace().getApplicationContext();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(EtkaApp.getInstnace().getApplicationContext(), "notify_001");
        Intent ii = new Intent(EtkaApp.getInstnace().getApplicationContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(EtkaApp.getInstnace().getApplicationContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("BIG TEST");
        bigText.setBigContentTitle("Title");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.ic_audiotrack_light);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}
