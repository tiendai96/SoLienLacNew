package com.hdpro.solienlac.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hdpro.solienlac.Adapter.DiemAdapter;
import com.hdpro.solienlac.Diem;
import com.hdpro.solienlac.MainActivity;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 02/11/2016.
 */

public class DiemFragment extends Fragment {
    MyDatabas_Helper myDatabasHelper;
    ListView lvBangdiemHK1,lvBangdiemHK2;
    TabHost tabHost;
    TextView textViewTrangthaiBD,textViewTrangthaiBD2;
    SwipeRefreshLayout swipeRefreshLayout,swipeRefreshLayout2;
    FloatingActionButton floatingActionButton;
    ProgressDialog progressDialog;

    ArrayList<Diem> dsDiemhk1,dsDiemhk2 = null;
    DiemAdapter diemAdapter1,diemAdapter2;
    int IDHS;
    int trangthaiBangdiem1=0;
    int trangthaiBangdiem2=0;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_diemmonhoc,null);
        IDHS = getArguments().getInt("idhs");
        myDatabasHelper = new MyDatabas_Helper(getActivity());

        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.sang_phai);
        view.setAnimation(animation);

        addControl();
        getDulieuDiem1();
        getDulieuDiem2();
        addEvents();
        
        return view;
    }

    private void addControl() {
        textViewTrangthaiBD = (TextView) view.findViewById(R.id.textViewTrangthaiBD);
        textViewTrangthaiBD2 = (TextView) view.findViewById(R.id.textViewTrangthaiBD2);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabBtn);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F44336"),Color.parseColor("#4CAF50"),Color.parseColor("#FF9800"),Color.parseColor("#3F51B5"));
        swipeRefreshLayout2 = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout2);
        swipeRefreshLayout2.setColorSchemeColors(Color.parseColor("#F44336"),Color.parseColor("#4CAF50"),Color.parseColor("#FF9800"),Color.parseColor("#3F51B5"));

        // LISTVIEW
        lvBangdiemHK1 = (ListView) view.findViewById(R.id.listViewBangdiemHK1);
        lvBangdiemHK2 = (ListView) view.findViewById(R.id.listViewBangdiemHK2);
        // TAB HOST
        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabHost.setup();
        TabHost.TabSpec tab1= tabHost.newTabSpec("t1");
        tab1.setIndicator("Điểm kỳ 1");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2= tabHost.newTabSpec("t2");
        tab2.setIndicator("Điểm kỳ 2");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }//Ánh xạ

    private void addEvents() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        taidulieu();
                    }
                }, 3000);
            }
        });
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        taidulieu();
                    }
                }, 3000);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCompat.animate(floatingActionButton).rotation(360f).withLayer().setDuration(1000).setInterpolator(new OvershootInterpolator()).start();
                taidulieu();
            }
        });
    }//Sự kiện click
    class DocJsonHK1 extends AsyncTask<String, Void,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            function docjon= new function();
            return docjon.docNoidungtuURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            try {
                myDatabasHelper.DropDiem(IDHS);
                JSONArray mangBD = new JSONArray(s);
                for(int i = 0;i<mangBD.length();i++){
                    JSONObject bangdiem = mangBD.getJSONObject(i);
                    String tenmonhoc = bangdiem.getString("tenmonhoc");
                    String diem1=bangdiem.getString("diem1");
                    String diem2=bangdiem.getString("diem2");
                    String diem3=bangdiem.getString("diem3");
                    myDatabasHelper.ThemDiem(IDHS,tenmonhoc,diem1,diem2,diem3,1);
                }
                if(mangBD.length()==0){
                    trangthaiBangdiem1=1;
                }
                getDulieuDiem1();
                diemAdapter1.notifyDataSetChanged();
                new function().thongbaoThanhcong("Tải dữ liệu hoàn tất", view, getActivity());//Thông báo thành công
            } catch (JSONException e) {
                e.printStackTrace();
                lvBangdiemHK1.setVisibility(view.GONE);
                textViewTrangthaiBD.setText("Lỗi kết lối Internet :(");
                new function().thongbaoLoi("Lỗi kết nối",view,getActivity());//Thông báo thành công
            }
        }
    }//tải dữ liệu kỳ 1
    class DocJsonHK2 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            function docjsonk2 = new function();
            return docjsonk2.docNoidungtuURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray mangBD2=new JSONArray(s);
                for(int i =0;i<mangBD2.length();i++){
                    JSONObject bangdiem2= mangBD2.getJSONObject(i);
                    String tenmonhoc = bangdiem2.getString("tenmonhoc");
                    String diem1=bangdiem2.getString("diem1");
                    String diem2=bangdiem2.getString("diem2");
                    String diem3=bangdiem2.getString("diem3");
                    myDatabasHelper.ThemDiem(IDHS,tenmonhoc,diem1,diem2,diem3,2);
                }
                if(mangBD2.length()==0){
                    trangthaiBangdiem2=1;
                }
                getDulieuDiem2();
                diemAdapter2.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
                lvBangdiemHK2.setVisibility(view.GONE);
                textViewTrangthaiBD.setText("Lỗi kết lối Internet :(");
            }

        }
    }//tải dữ liệu kỳ 2
    private void taidulieu() {
        DocJsonHK1 docJsonBangdiemK1 = new DocJsonHK1();
        docJsonBangdiemK1.execute("http://files.pinyeu.com/solienlac/json/json_bangdiem.php?hk=1&hs="+IDHS);
        DocJsonHK2 docJsonBangdiemK2 = new DocJsonHK2();
        docJsonBangdiemK2.execute("http://files.pinyeu.com/solienlac/json/json_bangdiem.php?hk=2&hs="+IDHS);
    }
    private void getDulieuDiem1() {
        // Kỳ 1
        dsDiemhk1 = new ArrayList<Diem>();
        dsDiemhk1 = myDatabasHelper.LayDSBD(IDHS,1);
        if(dsDiemhk1.size()==0){
            if(trangthaiBangdiem1==1){
                lvBangdiemHK1.setVisibility(view.GONE);
                textViewTrangthaiBD.setText("Chưa có Thời khóa biểu");
            }else {
                taidulieu();
            }
        }
        diemAdapter1 = new DiemAdapter(getActivity(),R.layout.item_bangdiem_listview,dsDiemhk1);
        lvBangdiemHK1.setAdapter(diemAdapter1);
    }
    private void getDulieuDiem2(){
        //Kỳ 2
        dsDiemhk2 = new ArrayList<Diem>();
        dsDiemhk2 = myDatabasHelper.LayDSBD(IDHS,2);
        if(dsDiemhk2.size()==0){
            if(trangthaiBangdiem2==1){
                lvBangdiemHK2.setVisibility(view.GONE);
                textViewTrangthaiBD2.setText("Chưa có Thời khóa biểu");
            }
        }
        diemAdapter2 = new DiemAdapter(getActivity(),R.layout.item_bangdiem_listview,dsDiemhk2);
        lvBangdiemHK2.setAdapter(diemAdapter2);
    }
}
