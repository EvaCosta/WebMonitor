package com.webmonitor.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.webmonitor.R;


public class Notifica extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent serviceIntent = new Intent(context, NotificaService.class);
            context.startService(serviceIntent);
        } else {
            Log.i("Alarme", "Disparou");
            sendNotification(context, intent);
        }
    }

    private void sendNotification(Context context, Intent intent){
        Intent newIntent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        NotificationCompat.Builder notBuilder= new NotificationCompat.Builder(context, "123")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("webmonitor")
                .setContentText("Notificação agendada pelo AlarmManager")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.notify(100, notBuilder.build());
    }


}
