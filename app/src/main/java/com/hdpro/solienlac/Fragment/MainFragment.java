package com.hdpro.solienlac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hdpro.solienlac.HuongdanActivity;
import com.hdpro.solienlac.MainActivity;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.chat_room;

/**
 * Created by User on 02/11/2016.
 */

public class MainFragment extends Fragment {

    private Button btnTinnhan,btnBangdiem,btnPhanhoi,btnThoikhoabieu,btnHuongdan,btnChat,btnChat_All;
    View view;
    int IDHS;
    String TENHS,LOPHS,TENGV,SDTGV;
    TextView txtHoten,txtTentruong,txtNamhoc;
    Animation animation;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        view = inflater.inflate(R.layout.layout_main,null);
        //
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.sang_phai);
        view.setAnimation(animation);
        getThongtintaikhoan();
        addControl();
        addEvents();
        // Đọc Database trên Firebase
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference tentruongRef = firebaseDatabase.getReference("School");
        tentruongRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtTentruong.setText(dataSnapshot.child("Ten").getValue(String.class));
                txtNamhoc.setText(dataSnapshot.child("namhoc").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void getThongtintaikhoan() {
        IDHS=getArguments().getInt("idhs");
        TENHS=getArguments().getString("tenhs");
        LOPHS=getArguments().getString("lophs");
        TENGV = getArguments().getString("tengv");
        SDTGV = getArguments().getString("sdtgv");
    }

    private void addControl() {
        animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate);
        btnHuongdan= (Button) view.findViewById(R.id.btnHuongdan);
        btnTinnhan= (Button) view.findViewById(R.id.btnTinnhan);
        btnBangdiem= (Button) view.findViewById(R.id.btnBangdiem);
        btnPhanhoi= (Button) view.findViewById(R.id.btnPhanhoi);
        btnThoikhoabieu= (Button) view.findViewById(R.id.btnThoikhoabieu);
        btnChat = (Button) view.findViewById(R.id.btnChat);
        btnChat_All = (Button) view.findViewById(R.id.btnChatAll);

        txtTentruong = (TextView) view.findViewById(R.id.textViewTruong);
        txtNamhoc = (TextView) view.findViewById(R.id.txtThoigian);
        txtHoten = (TextView) view.findViewById(R.id.txtTenhocsinh);
        txtHoten.setText(TENHS+" - LỚP "+LOPHS);
    }

    private void addEvents() {
        btnTinnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinnhan();
            }
        });
        btnBangdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bangdiem();
            }
        });
        btnThoikhoabieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thoikhoabieu();
            }
        });
        btnPhanhoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phanhoi();
            }
        });
        btnHuongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                huongdan();
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat();
            }
        });
        btnChat_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_all();
            }
        });
    }

    private void chat() {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("idhs",IDHS);
        bundle.putString("lophs",LOPHS);
        chatFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.sang_trai,R.anim.exit_to_right)
                .replace(R.id.content_frame,chatFragment).commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Chat");
    }
    private void chat_all() {
        Intent it = new Intent(getActivity(),chat_room.class);
        startActivity(it);

    }

    private void huongdan() {
        Intent intent = new Intent(getActivity(), HuongdanActivity.class);
        startActivity(intent);
    }

    private void phanhoi() {

        PhanhoiFragment phanhoiFragment = new PhanhoiFragment();
        phanhoiFragment.setArguments(bundleTaikhoan());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right)
                .replace(R.id.content_frame,phanhoiFragment).addToBackStack(null).commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Phản hồi");
    }

    private void thoikhoabieu() {
//        btnThoikhoabieu.startAnimation(animation);
        ThoikhoabieuFragment thoikhoabieuFragment = new ThoikhoabieuFragment();
        Bundle bundleThoikhoabieu = new Bundle();
        bundleThoikhoabieu.putString("tenhs",TENHS);
        bundleThoikhoabieu.putString("lophs",LOPHS);
        thoikhoabieuFragment.setArguments(bundleThoikhoabieu);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right)
                .replace(R.id.content_frame,thoikhoabieuFragment).addToBackStack(null).commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Thời khóa biểu");
    }

    private void bangdiem() {
//        btnBangdiem.startAnimation(animation);

        DiemFragment diemFragment = new DiemFragment();
        Bundle bundleBangdiem = new Bundle();
        bundleBangdiem.putInt("idhs",IDHS);
        diemFragment.setArguments(bundleBangdiem);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right)
                .replace(R.id.content_frame,diemFragment).commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Bảng điểm");
    }

    private void tinnhan() {
        TinnhanFragment tinnhanFragment = new TinnhanFragment();
        Bundle bundleTaikhoan = new Bundle();
        bundleTaikhoan.putInt("idhs",IDHS);
        tinnhanFragment.setArguments(bundleTaikhoan);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.sang_trai, R.anim.exit_to_right)
                .replace(R.id.content_frame,tinnhanFragment).addToBackStack(null).commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tin nhắn");
    }
    private Bundle bundleTaikhoan() {
        Bundle bundle = new Bundle();
        bundle.putInt("idhs",IDHS);
        bundle.putString("tenhs",TENHS);
        bundle.putString("lophs",LOPHS);
        bundle.putString("tengv",TENGV);
        bundle.putString("sdtgv",SDTGV);
        return bundle;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Sổ liên lạc");
    }
}
