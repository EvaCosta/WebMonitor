package com.webmonitor.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AutoStartAlarmReceiver extends BroadcastReceiver {

    private final Long interval = 60000L; //1 minuto

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("WebMonitor", "AutoStartAlarmReceiver.onReceive");

        createAlarmManager(context);

    }

    private void createAlarmManager(Context context){
        Log.i("WebMonitor", "AutoStartAlarmReceiver.createAlarmManager");

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        boolean alarmRunning = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(alarmRunning){
            alarmManager.cancel(pendingIntent);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, this.interval, pendingIntent);
    }

}
