package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    }
}
