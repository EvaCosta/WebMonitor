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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

        //Elimina as notificações quando o app é aberto
        clearNotifications(this);

        //Chama o BroadcastReceiver para iniciar o AlarmMonitor
        startAlarmBroadcast(this);

    }

    private void clearNotifications(Context context){
        AlertNotification.removeNotifications(this);
    }

    private void startAlarmBroadcast(Context context){
        Log.i("WebMonitor", "MainActivity.startAlarmBroadcast");
        Intent intent = new Intent(context, AutoStartAlarmReceiver.class);
        context.sendBroadcast(intent);
    }

    public void exibirConfirmacao(View view){
        ImageButton btn = (ImageButton) findViewById(R.id.delete_btn);

        AlertDialog.Builder box = new AlertDialog.Builder(this);
        box.setTitle("Excluir");
        box.setIcon(android.R.drawable.ic_menu_delete);
        box.setMessage("Tem certeza que deseja excluir este item?");

        box.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Item excluído", Toast.LENGTH_LONG).show();
            }
        });
        box.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Item excluído", Toast.LENGTH_LONG).show();
            }
        });
        box.show();
    }


}
