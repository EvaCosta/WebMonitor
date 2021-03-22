package com.webmonitor.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.webmonitor.R;
import com.webmonitor.model.Page;

public class AlertNotification {


    public static void createNotificationChannel(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "webmonitor";
            String description = "descrição do canal do webmonitor";
            NotificationChannel channel = new NotificationChannel("WebMonitor", name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Log.i("WebMonitor", "Canal Criado");
        }
    }

    public static void sendNotification(Context context, Intent intent, Page page){

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

        Log.i("WebMonitor", "Notificação enviada!");
    }

}
