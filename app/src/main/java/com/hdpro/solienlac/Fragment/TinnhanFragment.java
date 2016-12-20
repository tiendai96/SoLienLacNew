package com.hdpro.solienlac.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdpro.solienlac.Adapter.TinnhanAdapter;
import com.hdpro.solienlac.MainActivity;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.Tinnhan;
import com.hdpro.solienlac.function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 02/11/2016.
 */

public class TinnhanFragment extends Fragment {
    TextView txtTieude,txtNgaynhan,txtTrangthaiTN;
    ArrayList<Tinnhan> dsTinnhan = null;
    TinnhanAdapter tinnhanAdapter;
    ListView lvTinnhan;
    MyDatabas_Helper myDatabas_helper;
    int IDHS;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_tinnhan,null);
        IDHS=getArguments().getInt("idhs");
        myDatabas_helper = new MyDatabas_Helper(getActivity());

        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.sang_phai);
        view.setAnimation(animation);

        addControl();
        registerForContextMenu(lvTinnhan);
        addEvents();
        return view;
    }

    private void addEvents() {
    }

    private void addControl() {
        txtTieude = (TextView) view.findViewById(R.id.textViewTieudeTinnhan);
        txtNgaynhan = (TextView) view.findViewById(R.id.txtNgaynhanTinNhan);
        txtTrangthaiTN = (TextView) view.findViewById(R.id.textViewTrangthaiTN);
        lvTinnhan= (ListView) view.findViewById(R.id.lvTinnhan);
        dsTinnhan = new ArrayList<Tinnhan>();
        getDulieutinnhan();
        tinnhanAdapter = new TinnhanAdapter(getActivity(),R.layout.item_tinnhan_listview,dsTinnhan);
        lvTinnhan.setAdapter(tinnhanAdapter);
        tinnhanAdapter.notifyDataSetChanged();
        lvTinnhan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hienthiTinnhan(i);
            }
        });
    }

    private void hienthiTinnhan(int i) {
        final Dialog dialogTinnhan = new Dialog(getActivity());
        dialogTinnhan.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTinnhan.setContentView(R.layout.dialog_hienthitinnhan);
        dialogTinnhan.setCanceledOnTouchOutside(false);
        dialogTinnhan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTinnhan.show();
        ////Anh xa - gan du lieu
        TextView dialog_txtNoidung = (TextView) dialogTinnhan.findViewById(R.id.dialog_txtNoidungtinnhan);
        dialog_txtNoidung.setText(dsTinnhan.get(i).getNoidungTinnhan());
        TextView dialog_txtNgaygui = (TextView) dialogTinnhan.findViewById(R.id.dialog_textViewNgaynhantinnhan);
        dialog_txtNgaygui.setText(dsTinnhan.get(i).getNgayguiTinnhan());
        Button btnDong = (Button) dialogTinnhan.findViewById(R.id.buttonDongTinnhan);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTinnhan.cancel();
            }
        });
        // thay doi trang thai cua tin nha (chua doc-> da doc)
        myDatabas_helper.SuaTN(IDHS,dsTinnhan.get(i).getIdTinnhan());
        dsTinnhan.get(i).setTrangthaiTinnhan(1);
        tinnhanAdapter.notifyDataSetChanged();
    }

    private void getDulieutinnhan() {
        dsTinnhan = myDatabas_helper.layDSTN(IDHS);
        if(dsTinnhan.size()==0){
            lvTinnhan.setVisibility(view.GONE);
            txtTrangthaiTN.setTextSize(30);
            txtTrangthaiTN.setText("Không có tin nhắn");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_tuychon_listview,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete:
                if(myDatabas_helper.DeleteItemTinnhan(tinnhanAdapter.getItem(info.position).getIdTinnhan())>0){
                    tinnhanAdapter.remove(tinnhanAdapter.getItem(info.position));
                    new function().thongbaoThanhcong("Đã xóa",view,getActivity());
                }else {
                    new function().thongbaoLoi("Xóa không thành công",view,getActivity());
                }
                return true;
            case R.id.deleteall:
                if(myDatabas_helper.DeleteAllTinnhan(IDHS)>0){
                    tinnhanAdapter.clear();
                    new function().thongbaoThanhcong("Đã xóa tất cả",view,getActivity());
                }else {
                    new function().thongbaoLoi("Xóa không thành công",view,getActivity());
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
