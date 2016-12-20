package com.hdpro.solienlac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.Thoikhoabieu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by User on 09/12/2016.
 */

public class ThoikhoabieuExpandableListviewAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> parent;
    HashMap<String, List<Thoikhoabieu>> child;
    public ThoikhoabieuExpandableListviewAdapter(Context context, List<String> Parent, HashMap<String, List<Thoikhoabieu>> Child){
        this.context = context;
        this.parent = Parent;
        this.child = Child;
    }
    @Override
    public int getGroupCount() {
        return this.parent.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.child.get(this.parent.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.parent.get(i);
    }

    @Override
    public ArrayList<Thoikhoabieu> getChild(int i, int i1) {
        ArrayList<Thoikhoabieu> arrayList = new ArrayList<Thoikhoabieu>();
        arrayList.add(new Thoikhoabieu(this.child.get(this.parent.get(i)).get(i1).getThu(),this.child.get(this.parent.get(i)).get(i1).getMonhoc(),this.child.get(this.parent.get(i)).get(i1).getTiethoc()));
        return arrayList;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View rowParent = view;
        if(rowParent ==null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowParent = inflater.inflate(R.layout.item_parent_thoikhoabieu,viewGroup,false);
        }
        String txtThu = (String) getGroup(i);
        TextView textViewThu = (TextView) rowParent.findViewById(R.id.textViewThuTKB);
        textViewThu.setText(txtThu);
        TextView txtSotiet = (TextView) rowParent.findViewById(R.id.textViewSotiet);
        txtSotiet.setText("Số tiết: "+getChildrenCount(i));

        return rowParent;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View rowChild = view;
        if(rowChild ==null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowChild = inflater.inflate(R.layout.item_child_thoikhoabieu,viewGroup,false);
        }
        ArrayList<Thoikhoabieu> tiethoc = getChild(i,i1);
        String txtMonhoc = tiethoc.get(0).getMonhoc();
        String Tiethoc = "Tiết "+ String.valueOf(tiethoc.get(0).getTiethoc());
        TextView txtTiethoc = (TextView) rowChild.findViewById(R.id.textViewTiethocTKB);
        txtTiethoc.setText(Tiethoc);
        TextView textview = (TextView) rowChild.findViewById(R.id.textViewMonhocTKB);
        textview.setText(txtMonhoc);

        return rowChild;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
