package com.webmonitor.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.webmonitor.R;
import com.webmonitor.adapter.AdapterPage;
import com.webmonitor.db.Database;


public class MainActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list= findViewById(R.id.recyclerView);
        Database db = new Database(getApplicationContext());
        AdapterPage adapter=new AdapterPage(this, db, db.all());
        list.setAdapter(adapter);

        //Elimina as notificações quando o app é aberto
        startAlarmBroadcast(this);

    }

    private void startAlarmBroadcast(Context context){
        Log.i("WebMonitor", "MainActivity.startAlarmBroadcast");
        Intent intent = new Intent(context, AutoStartAlarmReceiver.class);
        context.sendBroadcast(intent);
    }

    public void cadastro(View view) {
        Intent intent = new Intent(this, IncludeActivity.class);
        startActivity(intent);
    }
}
