package com.webmonitor.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.webmonitor.R;
import com.webmonitor.model.Page;

import java.util.ArrayList;
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
        View rowView=inflater.inflate(R.layout.pages_list, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.main_title);
       // ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(pagesList.get(position).getTitle());
        //imageView.setImageResource(imgid[position]);
        subtitleText.setText(pagesList.get(position).getUrl());

        return rowView;

    }
}
