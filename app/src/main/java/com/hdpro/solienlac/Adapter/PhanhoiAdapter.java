package com.hdpro.solienlac.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hdpro.solienlac.Phanhoi;
import com.hdpro.solienlac.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 11/11/2016.
 */

public class PhanhoiAdapter extends ArrayAdapter<Phanhoi> {
    Activity context; int resource; ArrayList<Phanhoi> objects;
    public PhanhoiAdapter(Activity context, int resource, ArrayList<Phanhoi> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);
        TextView txtTieudePH= (TextView) view.findViewById(R.id.textViewTieudePH);
        txtTieudePH.setText(objects.get(position).getTieudePH());
        TextView txtNoidungPH= (TextView) view.findViewById(R.id.textViewNoidungPH);
        if(objects.get(position).getNoidungPH().length()<34){
            txtNoidungPH.setText(objects.get(position).getNoidungPH());
        }else {
            txtNoidungPH.setText((objects.get(position).getNoidungPH()).substring(0, 34) + "...");
        }
        TextView txtNgayguiPhanhoi = (TextView) view.findViewById(R.id.txtNgayguiPhanhoi);
        txtNgayguiPhanhoi.setText(objects.get(position).getNgayguiPH());

        return view;
    }
}
