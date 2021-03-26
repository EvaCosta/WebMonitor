package com.webmonitor.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.webmonitor.model.Page;

import java.util.List;

public class AdapterPage extends ArrayAdapter<Page> {
    private Activity activity;
    private List<Page> pagesList;
    private static LayoutInflater inflater = null;

    public AdapterPage (Activity activity, List<Page> pagesList) {
        super(activity, R.layout.pages_list, pagesList);

            this.activity = activity;
            this.pagesList = pagesList;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater= activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.pages_list, null,true);

        TextView titleText = rowView.findViewById(R.id.main_title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        TextView idText = rowView.findViewById(R.id.id_text);

        ImageButton btnDelete = rowView.findViewById(R.id.delete_btn);
        ImageButton btnEdit = rowView.findViewById(R.id.edit_btn);

        titleText.setText(pagesList.get(position).getTitle());
        subtitleText.setText(pagesList.get(position).getUrl());
        idText.setText(pagesList.get(position).getId().toString());

        titleText.setOnClickListener(v -> openPage(subtitleText));
        subtitleText.setOnClickListener(v -> openPage(subtitleText));
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

        Long id = Long.parseLong(idText);

        ImageButton btn = (ImageButton) activity.findViewById(R.id.delete_btn);

        AlertDialog.Builder box = new AlertDialog.Builder(activity);
        box.setTitle("Excluir");
        box.setIcon(android.R.drawable.ic_menu_delete);
        box.setMessage("Tem certeza que deseja excluir este item?");

        box.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                for(Page p : pagesList){
                    if(id.equals(p.getId())) {
                        Toast.makeText(activity, "Item excluído: " + p.getTitle(), Toast.LENGTH_LONG).show();
                        System.out.println(position);

                        notifyDataSetChanged();

                    }
                }
                pagesList.remove(position); // isso exclui o que vem da dummy pages
            }
        });
        box.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        box.show();
    }

}
