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
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdpro.solienlac.Adapter.ThoikhoabieuExpandableListviewAdapter;
import com.hdpro.solienlac.Adapter.TkbAdapter;
import com.hdpro.solienlac.Login;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.Thoikhoabieu;
import com.hdpro.solienlac.function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 02/11/2016.
 */

public class ThoikhoabieuFragment extends Fragment {
    private TextView txtTenhocsinh;

    private ExpandableListView expandableListViewTKB;
    private List<String> dsThu;

    private FloatingActionButton floatingActionButton;
    private ProgressDialog progressDialog;

    private String tenHS;
    private String tenLop;
    MyDatabas_Helper myDatabas_helperl;
    SwipeRefreshLayout swipeRefreshLayout;
    int trangthaiTKB =0;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        view = inflater.inflate(R.layout.layout_thoikhoabieu2,null);
        getThongtinHocsinh();
        myDatabas_helperl = new MyDatabas_Helper(getActivity());

        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.sang_phai);
        view.setAnimation(animation);

        addControl();
        addEvents();
        getDulieuthoikhoabieu();
        return view;
    }

    private void taiDulieuThoikhoabieu() {
        new DocjsonTBK().execute("http://files.pinyeu.com/solienlac/json/json_thoikhoabieu.php?lop="+tenLop);
    }

    private void getThongtinHocsinh() {
        tenLop = getArguments().getString("lophs");
        tenHS = getArguments().getString("tenhs");
    }

    private void addEvents() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        taiDulieuThoikhoabieu();
                    }
                }, 2000);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewCompat.animate(floatingActionButton).rotation(360f).withLayer().setDuration(1000).setInterpolator(new OvershootInterpolator()).start();
                taiDulieuThoikhoabieu();
            }
        });
    }

    private void addControl() {
        expandableListViewTKB = (ExpandableListView) view.findViewById(R.id.expandableListviewThoikhoabieu);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F44336"),Color.parseColor("#4CAF50"),Color.parseColor("#FF9800"),Color.parseColor("#3F51B5"));

        txtTenhocsinh = (TextView) view.findViewById(R.id.txtTenhocsinh);
        txtTenhocsinh.setText(tenHS+" - "+tenLop);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
    class DocjsonTBK extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            function docjson = new function();
            return docjson.docNoidungtuURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            JSONArray mangTKB = null;
            try {
                mangTKB = new JSONArray(s);
                myDatabas_helperl.DropThoikhoabieucu(tenLop);
                for(int i = 0;i<mangTKB.length();i++){
                    JSONObject thoikhoabieu = mangTKB.getJSONObject(i);
                    int thu = thoikhoabieu.getInt("thu");
                    String monhoc = thoikhoabieu.getString("monhoc");
                    int tiethoc = thoikhoabieu.getInt("tiethoc");
                    switch (thu){
                        case 2:
                            myDatabas_helperl.ThemThoikhoabieu(2,monhoc,tiethoc,tenLop);
                            break;
                        case 3:
                            myDatabas_helperl.ThemThoikhoabieu(3,monhoc,tiethoc,tenLop);
                            break;
                        case 4:
                            myDatabas_helperl.ThemThoikhoabieu(4,monhoc,tiethoc,tenLop);
                            break;
                        case 5:
                            myDatabas_helperl.ThemThoikhoabieu(5,monhoc,tiethoc,tenLop);
                            break;
                        case 6:
                            myDatabas_helperl.ThemThoikhoabieu(6,monhoc,tiethoc,tenLop);
                            break;
                        case 7:
                            myDatabas_helperl.ThemThoikhoabieu(7,monhoc,tiethoc,tenLop);
                            break;
                    }
                }
                if(mangTKB.length()==0){
                    trangthaiTKB=1;
                }
                getDulieuthoikhoabieu();
                new function().thongbaoThanhcong("Tải dữ liệu hoàn tất",view,getActivity());//Thông báo thành công

            } catch (JSONException e) {
                e.printStackTrace();
                new function().thongbaoLoi("Lỗi kết nối",view,getActivity());//Thông báo thành công
            }
        }
    }
    private void getDulieuthoikhoabieu() {
        dsThu = new ArrayList<String>();
        dsThu.add("Thứ 2");
        dsThu.add("Thứ 3");
        dsThu.add("Thứ 4");
        dsThu.add("Thứ 5");
        dsThu.add("Thứ 6");
        dsThu.add("Thứ 7");
        HashMap<String, List<Thoikhoabieu>> dsTiethocChild = returnGroupChildItems();
        if(dsTiethocChild.get(dsThu.get(0)).size() ==0
                && dsTiethocChild.get(dsThu.get(1)).size()==0
                && dsTiethocChild.get(dsThu.get(2)).size()==0
                && dsTiethocChild.get(dsThu.get(3)).size()==0
                && dsTiethocChild.get(dsThu.get(4)).size()==0
                && dsTiethocChild.get(dsThu.get(5)).size()==0) {
            if (trangthaiTKB == 0) {
                taiDulieuThoikhoabieu();
            } else {
                Toast.makeText(getActivity(), "Chưa có dữ liệu Thời khóa biểu", Toast.LENGTH_LONG).show();
            }
        }else {
            ThoikhoabieuExpandableListviewAdapter adtThoikhoabieu = new ThoikhoabieuExpandableListviewAdapter(getActivity(), dsThu, dsTiethocChild);
            expandableListViewTKB.setAdapter(adtThoikhoabieu);

            Calendar calendar = Calendar.getInstance();
            int i = calendar.get(Calendar.DAY_OF_WEEK);
            if (i != 1) {
                expandableListViewTKB.expandGroup(i - 2);
            }
            expandableListViewTKB.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent,
                                            View v, int groupPosition, long id) {
                    return false; // This way the expander cannot be
                    // collapsed
                }
            });
        }
    }

    private HashMap<String, List<Thoikhoabieu>> returnGroupChildItems() {
        HashMap<String, List<Thoikhoabieu>> dsTiethocChildItems = new HashMap<String, List<Thoikhoabieu>>();
        List<Thoikhoabieu> dsTiethocThu2 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu2 = myDatabas_helperl.layDSMonhoc(tenLop,2);
        List<Thoikhoabieu> dsTiethocThu3 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu3 = myDatabas_helperl.layDSMonhoc(tenLop,3);
        List<Thoikhoabieu> dsTiethocThu4 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu4 = myDatabas_helperl.layDSMonhoc(tenLop,4);
        List<Thoikhoabieu> dsTiethocThu5 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu5 = myDatabas_helperl.layDSMonhoc(tenLop,5);
        List<Thoikhoabieu> dsTiethocThu6 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu6 = myDatabas_helperl.layDSMonhoc(tenLop,6);
        List<Thoikhoabieu> dsTiethocThu7 = new ArrayList<Thoikhoabieu>();
        dsTiethocThu7 = myDatabas_helperl.layDSMonhoc(tenLop,7);

        dsTiethocChildItems.put(dsThu.get(0),dsTiethocThu2);
        dsTiethocChildItems.put(dsThu.get(1),dsTiethocThu3);
        dsTiethocChildItems.put(dsThu.get(2),dsTiethocThu4);
        dsTiethocChildItems.put(dsThu.get(3),dsTiethocThu5);
        dsTiethocChildItems.put(dsThu.get(4),dsTiethocThu6);
        dsTiethocChildItems.put(dsThu.get(5),dsTiethocThu7);
        return dsTiethocChildItems;
    }


}
