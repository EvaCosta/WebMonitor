package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webmonitor.R;
import com.webmonitor.adapter.AdapterPage;
import com.webmonitor.model.DummyPages;

import java.io.File;
import java.io.FileReader;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    ListView list;

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdapterPage adapter=new AdapterPage(this, new DummyPages().getData());
        list=(ListView)findViewById(R.id.recyclerView);
        list.setAdapter(adapter);

        //Elimina as notificações quando o app é aberto
        startAlarmBroadcast(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearNotifications(this);
        activityVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityVisible = false;
    }

    private void clearNotifications(Context context){
        AlertNotification.removeNotifications(this);
    }

    private void startAlarmBroadcast(Context context){
        Log.i("WebMonitor", "MainActivity.startAlarmBroadcast");
        Intent intent = new Intent(context, AutoStartAlarmReceiver.class);
        context.sendBroadcast(intent);
    }


}
