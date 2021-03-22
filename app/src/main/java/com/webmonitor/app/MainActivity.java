package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
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

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdapterPage adapter=new AdapterPage(this, DummyPages.data);
        list=(ListView)findViewById(R.id.recyclerView);
        list.setAdapter(adapter);


        /************* cria um AlarmManager ***************/

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //cria a intenção
        Intent alarmIntent = new Intent(this, Notifica.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        //agenda o alarme de 10 em 10 seg
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, pendingIntent);

    }
}
