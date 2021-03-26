package com.webmonitor.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.example.webmonitor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import java.util.concurrent.TimeUnit;

public class IncludeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include);
    }

    /// Faz o cadastro da página no banco.
    public void includePage(View view) {
        Database db = new Database(this);
        Page page = new Page();

        /// CAMPOS
        TextInputEditText descricao = findViewById(R.id.descricao);
        TextInputEditText url = findViewById(R.id.url);
        TextInputEditText minutes = findViewById(R.id.minutes);
        TextInputEditText porcentagem = findViewById(R.id.percentage);
        CheckBox connection = findViewById(R.id.dadosMoveis);

        /// Incluindo os dados recebidos pela tela de cadastro no objeto
        page.setTitle(descricao.getText().toString());
        page.setUrl(url.getText().toString());

        /// Conversão de minutos para milisegundos
        Long min = Long.parseLong(minutes.getText().toString());
        page.setTimeInterval(TimeUnit.MINUTES.toMillis(min));

        /// Porcentagem de alteração
        Integer percent = Integer.parseInt(porcentagem.getText().toString());
        page.setPercentage(percent);

        page.setAllowMobileConnection(connection.isSelected());

        /// Insere a página no banco de dados.
        db.insert(page);

    } // includePage()
}