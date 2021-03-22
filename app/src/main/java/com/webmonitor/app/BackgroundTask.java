package com.webmonitor.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import androidx.core.app.NotificationCompat;

import com.example.webmonitor.R;
import com.webmonitor.model.DummyPages;
import com.webmonitor.model.Page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BackgroundTask extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        List<Page> pageList = new ArrayList<Page>();

        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, TaskService.class);
            context.startService(serviceIntent);
        } else {
            Long currentTime = Calendar.getInstance().getTimeInMillis();
            for (Page page: DummyPages.data) {
                Long timeToCheck = page.getLastTime() + page.getTimeInterval()*60*1000;
                if(timeToCheck < currentTime){
//                    pageList.add(page);
                    sendNotification(context, intent, page);
                    page.setLastTime(currentTime);
                }
            }
        }
    }


    private void sendNotification(Context context, Intent intent, Page page){

        Intent newIntent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        Notification.Builder notBuilder= new Notification.Builder(context, "WebMonitor")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Página atualizada:")
                .setContentText(page.getTitle())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.notify(page.getTimeInterval().intValue(), notBuilder.build());
    }


}
