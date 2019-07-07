package com.androiddeft.loginandregistration;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;



public class AlarmNotificationService extends IntentService {
    private NotificationManager alarmNotificationManager;


    public static final int NOTIFICATION_ID = 1;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {


        sendNotification("Wake Up! Wake Up! Alarm started!!");
    }


    private void sendNotification(String msg) {
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, AlarmActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);


        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());
    }


}