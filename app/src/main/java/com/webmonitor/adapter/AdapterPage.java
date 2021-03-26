package com.webmonitor.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webmonitor.R;
import com.webmonitor.db.Database;
import com.webmonitor.model.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterPage extends ArrayAdapter<Page> {
    private Activity activity;
    private Database db;
    private List<Page> pagesList;
    private static LayoutInflater inflater = null;

    public AdapterPage (Activity activity, Database db, List<Page> pages) {
        super(activity, R.layout.pages_list, pages);
        this.activity = activity;
        this.db = db;
        this.pagesList = pages;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater= activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.pages_list, null,true);

        TextView titleText = rowView.findViewById(R.id.main_title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        TextView idText = rowView.findViewById(R.id.id_text);
        TextView datetime = rowView.findViewById(R.id.datetime);

        ImageButton btnDelete = rowView.findViewById(R.id.delete_btn);
        ImageButton btnEdit = rowView.findViewById(R.id.edit_btn);

        titleText.setText(pagesList.get(position).getTitle());
        subtitleText.setText(pagesList.get(position).getUrl());
        idText.setText(pagesList.get(position).getId().toString());
        datetime.setText(dateFormat(pagesList.get(position).getLastTime()));

        titleText.setOnClickListener(v -> openPage(subtitleText));
        subtitleText.setOnClickListener(v -> openPage(subtitleText));
        datetime.setOnClickListener(v -> openPage(subtitleText));
        btnDelete.setOnClickListener(v -> showDeleteDialog(position, String.valueOf(idText.getText())));
        btnEdit.setOnClickListener(v -> editPage());


        return rowView;

    }

    public void editPage(){
        //Janela para edição da pagina do Paulo e da Eva será chamada aqui
    }

    public void openPage(View view){

        try {
            TextView txt = (TextView) view;
            String urlText = txt.getText().toString();

            if (!urlText.startsWith("http://") && !urlText.startsWith("https://"))
                urlText = "http://" + urlText;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlText));
            activity.startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(activity,
                    "Não foi possível abrir a página selecionada", Toast.LENGTH_LONG).show();
        }

    }

    public void showDeleteDialog(int position, String idText){

        long id = Long.parseLong(idText);

        ImageButton btn = activity.findViewById(R.id.delete_btn);

        AlertDialog.Builder box = new AlertDialog.Builder(activity);
        box.setTitle("Excluir");
        box.setIcon(android.R.drawable.ic_menu_delete);
        box.setMessage("Tem certeza que deseja excluir este item?");

        box.setPositiveButton("Sim", (dialog, which) -> {
            db.delete(id);
            pagesList.remove(position);
            notifyDataSetChanged();
        });
        box.setNegativeButton("Não", (dialog, which) -> {

        });
        box.show();
    }

    public String dateFormat(Date date){
        String data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        String hora = new SimpleDateFormat("HH:mm:ss").format(date);

        return data + " " + hora;
    }

}
