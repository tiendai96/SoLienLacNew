package com.hdpro.solienlac.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Tinnhan;

import java.util.ArrayList;

/**
 * Created by User on 04/11/2016.
 */

public class TinnhanAdapter extends ArrayAdapter<Tinnhan> {
    Activity context; int resource; ArrayList<Tinnhan> objects;
    public TinnhanAdapter(Activity context, int resource, ArrayList<Tinnhan> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tinnhan_listview,null);

        TextView txtNoidung = (TextView) view.findViewById(R.id.txtNoidungTinnhan);

        if(objects.get(position).getNoidungTinnhan().length()<34){
            txtNoidung.setText(objects.get(position).getNoidungTinnhan());
        }else {
            txtNoidung.setText((objects.get(position).getNoidungTinnhan()).substring(0, 34) + "...");
        }
        TextView txtNgaynhan = (TextView) view.findViewById(R.id.txtNgaynhanTinNhan);
        txtNgaynhan.setText(objects.get(position).getNgayguiTinnhan());
        ImageView imgTrangthai= (ImageView) view.findViewById(R.id.imgTrangthaiTinnhan);
        if(objects.get(position).getTrangthaiTinnhan()==1){
            imgTrangthai.setImageResource(R.drawable.ic_mail_open);
        }else {
            imgTrangthai.setImageResource(R.drawable.ic_mail_close);
        }

        return view;
    }
}
