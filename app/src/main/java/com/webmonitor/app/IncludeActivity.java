package com.webmonitor.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.webmonitor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class IncludeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_include);
    }

    /// Faz o cadastro da página no banco.
    public void includePage(View view) throws IOException {

        Database db = new Database(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                Page page = new Page();

                /// CAMPOS
                TextInputEditText descricao = findViewById(R.id.descricao);
                TextInputEditText url = findViewById(R.id.url);
                TextInputEditText minutes = findViewById(R.id.minutes);
                TextInputEditText porcentagem = findViewById(R.id.percentage);
                CheckBox connection = findViewById(R.id.dadosMoveis);

                /// Incluindo os dados recebidos pela tela de cadastro no objeto
                page.setUrl(Objects.requireNonNull(url.getText()).toString());

                if (!page.getUrl().startsWith("http")) {
                    page.setUrl("https://" + page.getUrl());
                }

                try {
                    document = Jsoup.connect(page.getUrl()).get();
                    page.setTitle(document.title());
                    page.setContent(document.body().text());

                    if(descricao.getText().toString().length() > 1)
                        page.setTitle(Objects.requireNonNull(descricao.getText()).toString());

                    /// Conversão de minutos para milisegundos
                    long min = Long.parseLong(Objects.requireNonNull(minutes.getText()).toString());
                    page.setTimeInterval(TimeUnit.MINUTES.toMillis(min));
                    page.setLastTime(new Date());

                    /// Porcentagem de alteração
                    Integer percent = Integer.parseInt(Objects.requireNonNull(porcentagem.getText()).toString());
                    page.setPercentage(percent);

                    page.setAllowMobileConnection(connection.isSelected());

                    /// Insere a página no banco de dados.
                    db.insert(page);

                    runOnUiThread(() -> Toast.makeText(view.getContext(), page.getTitle() + " cadastrado com sucesso!", Toast.LENGTH_LONG).show());

                    /// Retorna para a lista principal
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(view.getContext(), "URL inválida!", Toast.LENGTH_LONG).show());
                    return;
                }
            }
        });
        thread.start();

    } // includePage()
}