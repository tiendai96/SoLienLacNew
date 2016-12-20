package com.hdpro.solienlac.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Thoikhoabieu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 19/11/2016.
 */

public class TkbAdapter extends ArrayAdapter<Thoikhoabieu>{
    Activity context; int resource; ArrayList<Thoikhoabieu> objects;
    public TkbAdapter(Activity context, int resource, ArrayList<Thoikhoabieu> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource= resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);

        TextView txtTiet = (TextView) view.findViewById(R.id.txtTiet);
        txtTiet.setText("Tiết "+objects.get(position).getTiethoc()+": ");
        TextView txtMonhoc = (TextView) view.findViewById(R.id.txtMonhoc);
        txtMonhoc.setText(objects.get(position).getMonhoc());

        return view;
    }
}
