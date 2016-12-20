package com.hdpro.solienlac;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.HashMap;
        import java.util.Iterator;
        import java.util.Map;


public class chat  extends AppCompatActivity{

    private Button btn_gui;
    private EditText edit_tin_nhan;
    private TextView txt_tin_nhan;
    private String ten_tk,ten_phong;
    private DatabaseReference root ;
    private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);

        btn_gui = (Button) findViewById(R.id.btn_gui);
        edit_tin_nhan = (EditText) findViewById(R.id.edit_noidung);
        txt_tin_nhan = (TextView) findViewById(R.id.txt_noidung);

        ten_tk = getIntent().getExtras().get("tranfer_ten_tk").toString();
        ten_phong = getIntent().getExtras().get("tranfer_ten_phong").toString();

        this.setTitle(" Tên phòng :  "+ten_phong);

        root = FirebaseDatabase.getInstance().getReference().child(ten_phong);

        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",ten_tk);
                map2.put("msg",edit_tin_nhan.getText().toString());

                message_root.updateChildren(map2);
            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                them_noidung_chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                them_noidung_chat(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String noidung,taikhoan;

    private void them_noidung_chat(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            noidung = (String) ((DataSnapshot)i.next()).getValue();
            taikhoan = (String) ((DataSnapshot)i.next()).getValue();

            txt_tin_nhan.append(taikhoan +" : "+noidung +" \n");
        }

    }
}