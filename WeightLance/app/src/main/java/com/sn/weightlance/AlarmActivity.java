package com.androiddeft.loginandregistration;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {


    private PendingIntent pendingIntent;

    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;


    private static final int ALARM_REQUEST_CODE = 133;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AlarmActivity.this,DashboardActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        secondsRadioButton = (RadioButton) findViewById(R.id.seconds_radio_button);
        minutesRadioButton = (RadioButton) findViewById(R.id.minutes_radio_button);
        hoursRadioButton = (RadioButton) findViewById(R.id.hours_radio_button);
        Intent alarmIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);
        final EditText editText = (EditText) findViewById(R.id.input_interval_time);

        findViewById(R.id.start_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInterval = editText.getText().toString().trim();//get interval from edittext

                //check interval should not be empty and 0
                if (!getInterval.equals("") && !getInterval.equals("0"))
                    //finally trigger alarm manager
                    triggerAlarmManager(getTimeInterval(getInterval));

            }
        });


        findViewById(R.id.stop_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopAlarmManager();
            }
        });

    }


    private int getTimeInterval(String getInterval) {
        int interval = Integer.parseInt(getInterval);
        if (secondsRadioButton.isChecked())
            return interval;
        if (minutesRadioButton.isChecked())
            return interval * 60;
        if (hoursRadioButton.isChecked()) return interval * 60 * 60;
        return 0;
    }
    public void triggerAlarmManager(int alarmTriggerTime) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, alarmTriggerTime);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();
    }
    public void stopAlarmManager() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        stopService(new Intent(AlarmActivity.this, AlarmSoundService.class));
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
        Toast.makeText(this, "Alarm Stopped ", Toast.LENGTH_SHORT).show();
    }
}
