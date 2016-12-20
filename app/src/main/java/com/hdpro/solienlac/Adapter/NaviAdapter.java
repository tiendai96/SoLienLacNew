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

import com.hdpro.solienlac.Navigation;
import com.hdpro.solienlac.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by User on 03/11/2016.
 */

public class NaviAdapter extends ArrayAdapter<Navigation> {
    Activity context; int resource; ArrayList<Navigation> objects;
    public NaviAdapter(Activity context, int resource, ArrayList<Navigation> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_navigation_listview,null);

        TextView txtTieude= (TextView) row.findViewById(R.id.textViewitemNavi);
        txtTieude.setText(objects.get(position).getTxtTieude());
        ImageView imgIcon = (ImageView)row.findViewById(R.id.imageViewitemNavi);
        imgIcon.setImageResource(objects.get(position).getResIcon());
        return row;
    }
}
