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

import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Thoikhoabieu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 05/11/2016.
 */

public class ThoikhoabieuAdapter extends ArrayAdapter<Thoikhoabieu> {
    Activity context; int resource; ArrayList<Thoikhoabieu> objects;
    public ThoikhoabieuAdapter(Activity context, int resource, ArrayList<Thoikhoabieu> objects) {
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

//        TextView txtTiet1= (TextView) view.findViewById(R.id.txtTiet1);
//        txtTiet1.setText(objects.get(position).getTiet1());
//        TextView txtTiet2= (TextView) view.findViewById(R.id.txtTiet2);
//        txtTiet2.setText(objects.get(position).getTiet2());
//        TextView txtTiet3= (TextView) view.findViewById(R.id.txtTiet3);
//        txtTiet3.setText(objects.get(position).getTiet3());
//        TextView txtTiet4= (TextView) view.findViewById(R.id.txtTiet4);
//        txtTiet4.setText(objects.get(position).getTiet4());
//        TextView txtTiet5= (TextView) view.findViewById(R.id.txtTiet5);
//        txtTiet5.setText(objects.get(position).getTiet5());
//        ImageView imgIconthu = (ImageView) view.findViewById(R.id.imgIconthu);
//        int imgIcon;
//        switch (objects.get(position).getMaThu()){
//            case 2:
//                imgIcon=R.drawable.ic_thu2;
//                break;
//            case 3:
//                imgIcon=R.drawable.ic_thu3;
//                break;
//            case 4:
//                imgIcon=R.drawable.ic_thu4;
//                break;
//            case 5:
//                imgIcon=R.drawable.ic_thu5;
//                break;
//            case 6:
//                imgIcon=R.drawable.ic_thu6;
//                break;
//            default:
//                imgIcon=R.drawable.ic_thu7;
//                break;
//        }
//        imgIconthu.setImageResource(imgIcon);

        return view;
    }
}
