package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.webmonitor.R;
import com.webmonitor.adapter.AdapterPage;
import com.webmonitor.model.DummyPages;


public class MainActivity extends AppCompatActivity {

    ListView list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdapterPage adapter=new AdapterPage(this, DummyPages.data);
        list=(ListView)findViewById(R.id.recyclerView);
        list.setAdapter(adapter);


        startAlarmBroadcast(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        clearNotifications(this);
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
