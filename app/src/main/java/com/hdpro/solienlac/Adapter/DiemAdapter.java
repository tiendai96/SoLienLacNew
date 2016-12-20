package com.hdpro.solienlac.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdpro.solienlac.Diem;
import com.hdpro.solienlac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03/11/2016.
 */

public class DiemAdapter extends ArrayAdapter<Diem> {
    Activity context; int resource; ArrayList<Diem> objects;
    public DiemAdapter(Activity context, int resource, ArrayList<Diem> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_bangdiem_listview,null);

        TextView txtHeso1 = (TextView) row.findViewById(R.id.textViewHeso1);
        txtHeso1.setText(objects.get(position).getTxtHeso1());
        TextView txtHeso2 = (TextView) row.findViewById(R.id.textViewHeso2);
        txtHeso2.setText(objects.get(position).getTxtHeso2());
        TextView txtDiemthi = (TextView) row.findViewById(R.id.textViewThi);
        txtDiemthi.setText(objects.get(position).getTxtDiemthi());
        TextView txtTenmonhoc = (TextView) row.findViewById(R.id.textViewTenmonhoc);
        txtTenmonhoc.setText(objects.get(position).getTenmonhoc());
        return row;
    }
}
