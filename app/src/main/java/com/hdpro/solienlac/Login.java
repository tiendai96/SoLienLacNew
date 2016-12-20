package com.hdpro.solienlac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.LoggingEventHandler;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private TextView txtTendangnhap,txtMatkhau;
    private Button btnDangnhap;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editorSP;
    boolean kiemtraGhinho;
    boolean connected = false;
    int IDlayoutTN=0;
    int IDHS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        kiemtraSharedPreferenced();
        addEvens();
    }

    private void kiemtraSharedPreferenced() {
        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        Intent intent2=getIntent();
        Bundle bundle = new Bundle();
        bundle = intent2.getBundleExtra("LOGOUT");
        if(bundle!=null){
            editorSP = sharedPreferences.edit();
            editorSP.clear();
            editorSP.commit();
        }
        kiemtraGhinho = sharedPreferences.getBoolean("remember",false);
        IDHS = sharedPreferences.getInt("idHS",0);
        if(kiemtraGhinho==true){
            toMain();
        }
    }

    private void addEvens() {
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemtraDangnhap();
            }
        });
    }
    private void kiemtraDangnhap() {
        if(txtTendangnhap.getText().toString().equals("")){
            new function().thongbaoLoi("Bạn chưa nhập tên đăng nhập",getWindow().getDecorView().getRootView(),getApplicationContext());
        }else if(txtMatkhau.getText().toString().equals("")){
            new function().thongbaoLoi("Bạn chưa nhập mật khẩu",getWindow().getDecorView().getRootView(),getApplicationContext());
        }else {
            if(kiemtraKetnoiNET()){//connected = true or false
                KiemtraDangnhap kiemtra = new KiemtraDangnhap();
                kiemtra.execute("http://files.pinyeu.com/solienlac/json/kiemtradangnhap.php");
            }else {
                Toast.makeText(getApplicationContext(),"Kiểm tra lại kết nối mạng",Toast.LENGTH_SHORT).show();
            }
        }
    }
    class KiemtraDangnhap extends AsyncTask<String, Void, String>{
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
            if(!s.equals("false")){
                try {
                    JSONArray mangTK = new JSONArray(s);
                    JSONObject taikhoan = mangTK.getJSONObject(0);
                    String idTK = taikhoan.getString("id");
                    String tenTK = taikhoan.getString("ten");
                    String lopTK = taikhoan.getString("lop");
                    String tengv = taikhoan.getString("tengv");
                    String sdtgv = taikhoan.getString("sdtgv");

                    editorSP=sharedPreferences.edit();
                    editorSP.putBoolean("remember",true);
                    editorSP.putInt("idHS",Integer.parseInt(idTK));
                    editorSP.putString("tenHS",tenTK);
                    editorSP.putString("lopHS",lopTK);
                    editorSP.putString("tenGV",tengv);
                    editorSP.putString("sdtGV",sdtgv);
                    editorSP.commit();
                    toMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Kiểm tra lại kết nối",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void toMain() {
        kiemtraTinnhanFirebase();
        //
        Intent toMain = new Intent(Login.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("idhs",sharedPreferences.getInt("idHS",0));
        bundle.putString("tenhs",sharedPreferences.getString("tenHS","Nguyễn Văn A"));
        bundle.putString("lophs",sharedPreferences.getString("lopHS","0C"));
        bundle.putString("tengv",sharedPreferences.getString("tenGV","Phạm Công Hòa"));
        bundle.putString("sdtgv",sharedPreferences.getString("sdtGV","099999999"));
        bundle.putInt("idLayout",IDlayoutTN);
        toMain.putExtra("Taikhoan",bundle);
        startActivity(toMain);
        finish();
    }

    private void kiemtraTinnhanFirebase() {
        Intent tinnhan = getIntent();
        Bundle bundleTN = tinnhan.getBundleExtra("DulieuNotification");
        if(bundleTN!=null) {
            IDlayoutTN =bundleTN.getInt("idLayout");
            String NoidungTN = bundleTN.getString("thongdieptinnhan");
            String ThoigianTN = bundleTN.getString("thoigiantinnhan");
            if(NoidungTN !=null && NoidungTN!="") {
                MyDatabas_Helper myDatabas_helper = new MyDatabas_Helper(this);
                myDatabas_helper.themTN(IDHS, NoidungTN, ThoigianTN);
            }
        }
    }

    private String makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("user",txtTendangnhap.getText().toString() ));
        nameValuePair.add(new BasicNameValuePair("pass",txtMatkhau.getText().toString() ));

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
    private void addControl() {
        btnDangnhap = (Button) findViewById(R.id.buttonDangnhap);
        txtMatkhau = (TextView) findViewById(R.id.editTextMatkhau);
        txtTendangnhap = (TextView) findViewById(R.id.editTextTendangnhap);

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Thông báo");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
    private boolean kiemtraKetnoiNET() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
           return connected = true;
        } else
            return connected = false;
    }
}
