package com.hdpro.solienlac.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hdpro.solienlac.Phuhuynh;
import com.hdpro.solienlac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 14/12/2016.
 */

public class ChatAdapter extends ArrayAdapter<Phuhuynh> {
    Activity context;
    int resource;
    ArrayList<Phuhuynh> objects;
    public ChatAdapter(Activity context, int resource, ArrayList<Phuhuynh> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        TextView txtTenphuhuynh = (TextView) view.findViewById(R.id.textViewtenPhuHuynh);
        txtTenphuhuynh.setText(objects.get(position).getTenPH());
        TextView txtTenhocsinh = (TextView) view.findViewById(R.id.textViewTenHocsinh);
        txtTenhocsinh.setText("Phụ huynh của "+objects.get(position).getTenHS());
        return view;
    }
}
