package com.hdpro.solienlac;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class chat_room extends AppCompatActivity {

    private String taikhoan;
    private Button btn_them_phong;
    private TextView txt_tenphong;
    private ListView listView;
    private ArrayList<String> DSPhong = new ArrayList<>();
    private ArrayAdapter <String> arrayAdapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btn_them_phong = (Button)findViewById(R.id.btn_themphong);
        txt_tenphong = (TextView)findViewById(R.id.txt_tenphong);
        listView = (ListView)findViewById(R.id.list_phong_chat);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,DSPhong);
        listView.setAdapter(arrayAdapter);

        yeucau_taikhoan();
        btn_them_phong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put(txt_tenphong.getText().toString(),"");
                root.updateChildren(map); // cập nhật database
                //Toast.makeText(getApplicationContext(),root.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator(); // đối tượng đọc database

                while (i.hasNext()){ // đọc từng dòng
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                DSPhong.clear();
                DSPhong.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(),chat.class);
                // Toast.makeText(getApplicationContext(),((TextView)view).getText().toString()+taikhoan,Toast.LENGTH_SHORT).show();
                intent.putExtra("tranfer_ten_phong",arrayAdapter.getItem(i));
                intent.putExtra("tranfer_ten_tk",taikhoan);
                Toast.makeText(getApplicationContext(),arrayAdapter.getItem(i),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),taikhoan,Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
        });
    }


    private void yeucau_taikhoan() {
        AlertDialog.Builder thongbao = new AlertDialog.Builder(this);
        thongbao.setTitle("Nhập tên tài khoản: ");

        final EditText txt_taikhoan = new EditText(this);
        txt_taikhoan.setText("dai");
        thongbao.setView(txt_taikhoan);

        thongbao.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taikhoan = txt_taikhoan.getText().toString();
            }
        });
        thongbao.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                yeucau_taikhoan();
            }
        });
        thongbao.show();
    }


}
