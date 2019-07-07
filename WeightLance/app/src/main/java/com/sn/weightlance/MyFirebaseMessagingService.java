package com.androiddeft.loginandregistration;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remotemsg) {

        Log.d(TAG, "From -> " + remotemsg.getFrom());
        Log.d(TAG, "Demo Notification Body -> " + remotemsg.getNotification().getBody());
        sendNotification(remotemsg.getNotification().getBody());
    }


    private void sendNotification(String messageBody) {
        Intent intent;
        PendingIntent pendingIntent = null;
        if(messageBody.equals("Exercise")) {
             intent = new Intent(this, ExerciseActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }

        else if(messageBody.equals("Food")) {
            intent = new Intent(this, FoodListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("C# Corner")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}