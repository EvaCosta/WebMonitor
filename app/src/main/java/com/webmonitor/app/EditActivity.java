package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.webmonitor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EditActivity extends AppCompatActivity {

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        String site, descricao;
        site = getIntent().getStringExtra("URL"); /// url
        descricao = getIntent().getStringExtra("Descricao"); /// title

        id = Long.parseLong(getIntent().getStringExtra("ID"));
        Long min = Long.parseLong(getIntent().getStringExtra("Minutos")); /// TimeInterval
        Integer percentage = Integer.parseInt(getIntent().getStringExtra("Porcentagem")); /// Percentage
        Integer dadosMoveis = Integer.parseInt(getIntent().getStringExtra("DataMobile")); /// AllowMobileConnection

        ((TextInputEditText) findViewById(R.id.url)).setText(site);
        ((TextInputEditText) findViewById(R.id.descricao)).setText(descricao);
        ((TextInputEditText) findViewById(R.id.minutes)).setText(String.format("%d", TimeUnit.MILLISECONDS.toMinutes(min)));
        ((TextInputEditText) findViewById(R.id.percentage)).setText(percentage.toString());

        Boolean dados = dadosMoveis == 0 ? true : false;
        Switch aSwitch = (Switch)findViewById(R.id.dadosMoveisSwitch);

        aSwitch.setChecked(dados);
    }

    public void editPage(View view) {
        Database db = new Database(this);
        Page page = new Page();

        TextInputEditText descricao = findViewById(R.id.descricao);
        TextInputEditText url = findViewById(R.id.url);
        TextInputEditText minutes = findViewById(R.id.minutes);
        TextInputEditText porcentagem = findViewById(R.id.percentage);
        Switch connection = findViewById(R.id.dadosMoveisSwitch);

        page.setId(id);
        page.setImageSource("");
        page.setLastTime(new Date());

        /// Incluindo os dados recebidos pela tela de cadastro no objeto
        page.setTitle(descricao.getText().toString());
        page.setUrl(url.getText().toString());

        /// Conversão de minutos para milisegundos
        Long min = Long.parseLong(minutes.getText().toString());
        page.setTimeInterval(TimeUnit.MINUTES.toMillis(min));

        /// Porcentagem de alteração
        Integer percent = Integer.parseInt(porcentagem.getText().toString());
        page.setPercentage(percent);

        page.setAllowMobileConnection(connection.isChecked());

        /// Atualiza a página no banco de dados.
        db.update(page);

        Toast.makeText(this, page.getTitle().toString() + " alterado com sucesso!", Toast.LENGTH_LONG).show();

        /// Retorna para a lista principal
        Intent intent = new Intent(this, MainActivity.class);
        if (intent != null)
            startActivity(intent);
    }
}