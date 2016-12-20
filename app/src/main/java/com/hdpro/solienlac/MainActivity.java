package com.hdpro.solienlac;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hdpro.solienlac.Adapter.NaviAdapter;
import com.hdpro.solienlac.Fragment.DiemFragment;
import com.hdpro.solienlac.Fragment.MainFragment;
import com.hdpro.solienlac.Fragment.PhanhoiFragment;
import com.hdpro.solienlac.Fragment.ThoikhoabieuFragment;
import com.hdpro.solienlac.Fragment.TinnhanFragment;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {
    TextView txtTenhocsinh;
    LinearLayout linearLayoutNavi,linearLayoutTenhocsinh;
    DrawerLayout drawerlayout;
    ListView lvNavigation;
    ArrayList<Navigation> dsMenuNavi;
    NaviAdapter naviAdapter;
    ActionBarDrawerToggle drawertoggle;
    String title;
    String drawertitle;
    MainFragment mainFragment=new MainFragment();
    //
    int idLayoutFragment =0;
    public static int IDHS;
    String LOPHS,TENHS;
    Bundle bundleTaikhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setThongtinHS();
        //
        FirebaseMessaging.getInstance().subscribeToTopic("Test");
        String token= FirebaseInstanceId.getInstance().getToken()+"&Idhs="+IDHS;
        new FireBaseIDTask().execute(token);
        addControl();//Ánh xạ
        loadMain();
        addEvents();// Sự kiện
    }

    private void loadMain() {
        TinnhanFragment tinnhanFragment = new TinnhanFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        // truyền dữ liệu bundle từ tin nhắn, main đến sang fragment
        getIdLayoutFragment();
        // gui du lieu tai khoan den MainFragment
        mainFragment.setArguments(bundleTaikhoan);
        // main
        fragmentManager.beginTransaction()
                .add(R.id.content_frame,mainFragment,"main")
                .commit();
        title = "Thông tin chung";
        switch (idLayoutFragment){
            case 1:
                //gui id toi tin nhan
                tinnhanFragment.setArguments(bundleTaikhoan);
                //Tin nhan
                fragmentManager.beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame,tinnhanFragment)
                        .commit();
                title = "Tin nhắn đến";
                break;
        }
    }

    private void getIdLayoutFragment() {
        Intent intent = getIntent();
        Bundle noidungtinnhan=intent.getBundleExtra("Taikhoan");
        if(noidungtinnhan!=null){
            idLayoutFragment = noidungtinnhan.getInt("idLayout");
        }
    }

    private void addEvents() {
        linearLayoutTenhocsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventClickItemMenuNavi(10);
            }
        });
    }

    private void eventClickItemMenuNavi(int i) {
        PhanhoiFragment phanhoiFragment = new PhanhoiFragment();
        ThoikhoabieuFragment thoikhoabieuFragment = new ThoikhoabieuFragment();
        DiemFragment diemFragment = new DiemFragment();
        TinnhanFragment tinnhanFragment = new TinnhanFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (i){
            case 0:
                tinnhanFragment.setArguments(bundleTaikhoan);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right).addToBackStack(null)
                        .replace(R.id.content_frame,tinnhanFragment)
                        .commit();
                drawerlayout.closeDrawer(linearLayoutNavi);
                title = "Tin nhắn";
                break;
            case 1:
                diemFragment.setArguments(bundleTaikhoan);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right).addToBackStack(null)
                        .replace(R.id.content_frame,diemFragment)
                        .commit();
                title = "Điểm môn học";
                drawerlayout.closeDrawer(linearLayoutNavi);
                break;
            case 2:
                thoikhoabieuFragment.setArguments(bundleTaikhoan);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right).addToBackStack(null)
                        .replace(R.id.content_frame,thoikhoabieuFragment)
                        .commit();
                title = "Thời khóa biểu";
                drawerlayout.closeDrawer(linearLayoutNavi);
                break;
            case 3:
                phanhoiFragment.setArguments(bundleTaikhoan);
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right).addToBackStack(null)
                        .replace(R.id.content_frame,phanhoiFragment).addToBackStack(null)
                        .commit();
                title = "Phản hồi";
                drawerlayout.closeDrawer(linearLayoutNavi);
                break;
            case 4:
                dangxuat();
                break;
            case 5:
                super.finish();
                break;
            case 10:
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right)
                        .replace(R.id.content_frame,mainFragment,"main")
                        .commit();
                title = "Thông tin chung";
                drawerlayout.closeDrawer(linearLayoutNavi);
                break;
        }
    }

    private void addControl() {
        linearLayoutNavi = (LinearLayout) findViewById(R.id.LinearLayoutNavi);
        linearLayoutTenhocsinh= (LinearLayout) findViewById(R.id.LinearLayoutTenhocsinh);

        ///// gán dữ liệu cho Navigation
        txtTenhocsinh = (TextView) findViewById(R.id.textViewitemNavi);
        txtTenhocsinh.setText(TENHS);

        dsMenuNavi = new ArrayList<Navigation>();
        dsMenuNavi.add(new Navigation("Tin nhắn",R.drawable.ic_sms));
        dsMenuNavi.add(new Navigation("Điểm môn học",R.drawable.ic_diem));
        dsMenuNavi.add(new Navigation("Thời khóa biểu",R.drawable.ic_thoikhoabieu));
        dsMenuNavi.add(new Navigation("Phản hồi",R.drawable.ic_reply2));
        dsMenuNavi.add(new Navigation("Đăng xuất",R.drawable.ic_out));
        dsMenuNavi.add(new Navigation("Thoát",R.drawable.ic_thoat));

        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvNavigation = (ListView) findViewById(R.id.lvMenu);
        naviAdapter = new NaviAdapter(this,R.layout.item_navigation_listview,dsMenuNavi);
        lvNavigation.setAdapter(naviAdapter);
        lvNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventClickItemMenuNavi(i);
            }
        });
        // Khởi tạo Navigation Drawer
        title=drawertitle= (String) getTitle();
        drawertoggle = new ActionBarDrawerToggle(
                this,
                drawerlayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("HungSchool");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu();
            }
        };
        drawerlayout.setDrawerListener(drawertoggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }
    ////Sự kiện click nút góc trái trên Action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawertoggle.onOptionsItemSelected(item))
            return true;

        //....dieu kien cac action bar item khac
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean dangmo = drawerlayout.isDrawerOpen(linearLayoutNavi);
        /// hien thi icon menu dong/mo
        if(dangmo){
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
        }else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        }
        // hien thi menu theo su kien dong/mo
        menu.findItem(R.id.search_action).setVisible(!dangmo);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void finish() {
        if(mainFragment.isVisible())
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            DialogInterface.OnClickListener dialogOnclick = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case DialogInterface.BUTTON_POSITIVE:
                            MainActivity.super.finish();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            //kiểm tra xem drawerlay đang đóng hay mở
            boolean dangmo = drawerlayout.isDrawerOpen(linearLayoutNavi);
            if(dangmo){
                drawerlayout.closeDrawer(linearLayoutNavi);
            }else {
                builder.setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton("Có",dialogOnclick)
                        .setNegativeButton("Không",dialogOnclick).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"cc",Toast.LENGTH_LONG).show();
        }
    }

    private void dangxuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener dialogOnclick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(MainActivity.this,Login.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("dangxuat",true);
                        intent.putExtra("LOGOUT",bundle);
                        startActivity(intent);
                        MainActivity.super.finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        builder.setMessage("Bạn có muốn tiếp tục đăng xuất tài khoản?")
                .setPositiveButton("Tiếp tục",dialogOnclick)
                .setNegativeButton("Hủy",dialogOnclick).show();
    }
    public void setThongtinHS(){
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle=intent.getBundleExtra("Taikhoan");
        String TENGV="",SDTGV="";
        if(bundle!=null){
            IDHS=bundle.getInt("idhs");
            TENHS=bundle.getString("tenhs");
            LOPHS=bundle.getString("lophs");
            TENGV=bundle.getString("tengv");
            SDTGV=bundle.getString("sdtgv");
        }
        bundleTaikhoan = new Bundle();
        bundleTaikhoan.putInt("idhs",IDHS);
        bundleTaikhoan.putString("tenhs",TENHS);
        bundleTaikhoan.putString("lophs",LOPHS);
        bundleTaikhoan.putString("tengv",TENGV);
        bundleTaikhoan.putString("sdtgv",SDTGV);
    }

    @Override
    public void onBackPressed() {
        if (mainFragment.isVisible()){
            finish();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
