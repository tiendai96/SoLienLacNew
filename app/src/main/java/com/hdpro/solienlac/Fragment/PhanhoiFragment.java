package com.hdpro.solienlac.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hdpro.solienlac.Adapter.PhanhoiAdapter;
import com.hdpro.solienlac.Phanhoi;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.function;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by User on 02/11/2016.
 */

public class PhanhoiFragment extends Fragment{
    TabHost tabhost;

    Snackbar snackbar;
    ListView lvPhanhoi;
    ArrayList<Phanhoi> dsPhanhoi=null;
    PhanhoiAdapter phanhoiAdapter;

    Button btnGoidien,btnGuiphanhoi;
    TextView txtSDT,txtTieude,txtNoidung,txtTenGV;
    int IDHS;String TENGV,SDTGV;
    String datenow;
    ProgressDialog progressDialog;

    MyDatabas_Helper myDatabas_helper;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_phanhoi,null);
        myDatabas_helper = new MyDatabas_Helper(getActivity());

        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.sang_phai);
        view.setAnimation(animation);

        getDulieuTaikhoan();
        addControl();
        addEvents();
        return view;
    }

    private void getDulieuTaikhoan() {
        IDHS = getArguments().getInt("idhs");
        TENGV = getArguments().getString("tengv");
        SDTGV = getArguments().getString("sdtgv");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        datenow = sdf.format(calendar.getTime());
    }

    private void addEvents() {
        tabhost.setup();
        TabHost.TabSpec tab1 = tabhost.newTabSpec("t1");
        tab1.setIndicator("Phản hồi");
        tab1.setContent(R.id.tab1);
        tabhost.addTab(tab1);

        TabHost.TabSpec tab2 = tabhost.newTabSpec("t2");
        tab2.setIndicator("DS Phản hồi");
        tab2.setContent(R.id.tabDsPH);
        tabhost.addTab(tab2);

        btnGoidien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goidien();
            }
        });
        btnGuiphanhoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guiphanhoi();
                }
            });

        lvPhanhoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hienthiPhanhoi(i);
            }
        });
    }
    private void hienthiPhanhoi(int i){
        final Dialog dialogTinnhan = new Dialog(getActivity());
        dialogTinnhan.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTinnhan.setContentView(R.layout.dialog_hienthitinnhan);
        dialogTinnhan.setCanceledOnTouchOutside(false);
        dialogTinnhan.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogTinnhan.show();
        TextView dialong_txtTieude = (TextView) dialogTinnhan.findViewById(R.id.textView8);
        dialong_txtTieude.setText(dsPhanhoi.get(i).getTieudePH());
        TextView dialog_txtNoidung = (TextView) dialogTinnhan.findViewById(R.id.dialog_txtNoidungtinnhan);
        dialog_txtNoidung.setText(dsPhanhoi.get(i).getNoidungPH());
        TextView dialog_txtNgaygui = (TextView) dialogTinnhan.findViewById(R.id.dialog_textViewNgaynhantinnhan);
        dialog_txtNgaygui.setText(dsPhanhoi.get(i).getNgayguiPH());
        Button btnDong = (Button) dialogTinnhan.findViewById(R.id.buttonDongTinnhan);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTinnhan.cancel();
            }
        });
    }

    private void addControl() {
        txtSDT = (TextView) view.findViewById(R.id.textViewSDT);
        txtSDT.setText(SDTGV.toString());
        txtTenGV = (TextView) view.findViewById(R.id.textViewGiaovienchunhiem);
        txtTenGV.setText(TENGV.toString());
        btnGoidien = (Button) view.findViewById(R.id.buttonGoidien);
        txtTieude = (TextView) view.findViewById(R.id.editTextTieudePhanhoi);
        txtNoidung = (TextView) view.findViewById(R.id.editTextNoidungPhanhoi);
        btnGuiphanhoi = (Button) view.findViewById(R.id.buttonGuiphanhoi);
        tabhost = (TabHost) view.findViewById(R.id.tabDiem);
        lvPhanhoi = (ListView) view.findViewById(R.id.lvDanhsachPhanhoi);
        registerForContextMenu(lvPhanhoi);
        getDulieuPhanhoi();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Đang gửi, vui lòng chờ");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void getDulieuPhanhoi() {
        dsPhanhoi = new ArrayList<Phanhoi>();
        dsPhanhoi = myDatabas_helper.layDSPhanhoi(IDHS);
        phanhoiAdapter = new PhanhoiAdapter(getActivity(),R.layout.item_phanhoi_listview,dsPhanhoi);
        lvPhanhoi.setAdapter(phanhoiAdapter);
    }

    private String makePostRequest(String url) {

        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(4);
        nameValuePair.add(new BasicNameValuePair("tieude",txtTieude.getText().toString() ));
        nameValuePair.add(new BasicNameValuePair("noidung",txtNoidung.getText().toString() ));
        nameValuePair.add(new BasicNameValuePair("idhs",String.valueOf(IDHS) ));
        nameValuePair.add(new BasicNameValuePair("ngaygui",datenow));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }
    private void goidien() {
        String number = txtSDT.getText().toString().trim();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        startActivity(intent);
    }
    private void guiphanhoi() {
        if(txtTieude.getText().toString().equals("")){
            new function().thongbaoLoi("Bạn chưa nhập Tiêu đề",view,getActivity());
        }else if(txtNoidung.getText().toString().equals("")){
            new function().thongbaoLoi("Bạn chưa nhập Nội dung",view,getActivity());
        }else {
            DocjsonPhanhoi docjsonPhanhoi=new DocjsonPhanhoi();
            docjsonPhanhoi.execute("http://files.pinyeu.com/solienlac/json/json_phanhoi.php");
        }
    }

    class DocjsonPhanhoi extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return makePostRequest(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s!=null && s!="") {
                if (s.equals("false")) {
                    new function().thongbaoLoi("Gửi phản hồi thất bại",view,getActivity());//Thông báo không thành công
                } else {
                    new function().thongbaoThanhcong("Gửi phản hồi thành công",view,getActivity());//Thông báo thành công
                    myDatabas_helper.ThemPhanhoi(IDHS,txtTieude.getText().toString(),txtNoidung.getText().toString(),datenow);
                    getDulieuPhanhoi();
                    phanhoiAdapter.notifyDataSetChanged();
                }
            }else {
                new function().thongbaoLoi("Lỗi kết nối",view,getActivity());//Thông báo thành công
            }
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
                if(myDatabas_helper.DeleteItemPhanhoi(phanhoiAdapter.getItem(info.position).getTieudePH(),phanhoiAdapter.getItem(info.position).getNoidungPH())>0){
                    phanhoiAdapter.remove(phanhoiAdapter.getItem(info.position));
                    new function().thongbaoThanhcong("Đã xóa",view,getActivity());
                }else {
                    new function().thongbaoLoi("Xóa không thành công",view,getActivity());
                }
                return true;
            case R.id.deleteall:
                if(myDatabas_helper.DeleteAllPhanhoi(IDHS)>0){
                    phanhoiAdapter.clear();
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
