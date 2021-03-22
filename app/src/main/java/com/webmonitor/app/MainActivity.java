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


        /************** cria um NotificationChanel *********/
        this.createNotificationChannel();

        /************* cria um AlarmManager ***************/
        this.createTask();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "webmonitor";
            String description = "descrição do canal do webmonitor";
            NotificationChannel channel = new NotificationChannel("WebMonitor", name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createTask(){
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, BackgroundTask.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        //agenda uma tarefa para rodar de 1 em 1 minuto e verificar qual site deve ser verificado
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, 60000, pendingIntent);

    }
}
