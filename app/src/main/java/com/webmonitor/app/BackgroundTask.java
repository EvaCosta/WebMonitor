package com.webmonitor.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.webmonitor.R;
import com.webmonitor.model.DummyPages;
import com.webmonitor.model.Page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BackgroundTask extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Log.i("WebMonitor", "Alarme Disparou");
        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, TaskService.class);
            context.startService(serviceIntent);
        } else {
            Date currentTime = Calendar.getInstance().getTime();
            for (Page page: DummyPages.data) {
                Long timeToCheck = page.getLastTime().getTime() + page.getTimeInterval()*60*1000;
                if(timeToCheck < currentTime.getTime()){
                    AlertNotification.sendNotification(context, intent, page);
                    page.setLastTime(currentTime);
                }
            }
        }
    }





}
