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


    private static void createNotificationChannel(Context context) {

        Log.i("WebMonitor", "AlertNotification.createNotificationChannel");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "webmonitor";
            String description = "descrição do canal do webmonitor";
            NotificationChannel channel = new NotificationChannel("WebMonitor", name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public static void sendUpdateNotification(Context context, Page page){

        //Não cria outro canal se já existir o mesmo
        createNotificationChannel(context);

        Log.i("WebMonitor", "AlertNotification.sendNotification");

        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        Notification.Builder notBuilder= new Notification.Builder(context, "WebMonitor")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Página atualizada:")
                .setContentText(page.getTitle())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.notify(page.getId().intValue(), notBuilder.build());

    }

    public static void sendNoConectivityNotification(Context context){

        //Não cria outro canal se já existir o mesmo
        createNotificationChannel(context);

        Log.i("WebMonitor", "AlertNotification.sendNotification");

        Intent newIntent = new Intent(context, MainActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, 0);

        Notification.Builder notBuilder= new Notification.Builder(context, "WebMonitor")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Sem internet:")
                .setContentText("Não é possível verificar nenhuma página")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.notify(-1, notBuilder.build());

    }

    public static void removeAllNotifications(Context context){
        Log.i("WebMonitor", "AlertNotification.sendNotification");

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.cancelAll();
    }


    public static void removeNoConectivityNotifications(Context context){
        Log.i("WebMonitor", "AlertNotification.sendNotification");

        NotificationManager notManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notManager.cancel(-1);
    }

}
