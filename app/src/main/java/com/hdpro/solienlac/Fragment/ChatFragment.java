package com.hdpro.solienlac.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hdpro.solienlac.Adapter.ChatAdapter;
import com.hdpro.solienlac.Phuhuynh;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;
import com.hdpro.solienlac.function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 14/12/2016.
 */

public class ChatFragment extends Fragment {
    View view;
    private TextView txtTrangthaiPH;
    private ListView lvChat;
    private ArrayList<Phuhuynh> dsPhuhuynh;
    private MyDatabas_Helper myDatabas_helper;
    String TENLOP;
    int IDHS;
    int trangthaiPhuhuynh = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_chat,null);
        myDatabas_helper=new MyDatabas_Helper(getActivity());
        TENLOP = getArguments().getString("lophs");
        IDHS = getArguments().getInt("idhs");
        addControls();
        addEvents();

        return view;
    }

    private void addEvents() {

    }

    private void addControls() {
        txtTrangthaiPH = (TextView) view.findViewById(R.id.textViewTrangthaiPhuhuynh);
        lvChat = (ListView) view.findViewById(R.id.lvChat);
        dsPhuhuynh = new ArrayList<Phuhuynh>();
        getDulieuPhuhuynh();
    }

    private void getDulieuPhuhuynh() {
        dsPhuhuynh = myDatabas_helper.LayDSPhuHuynh(TENLOP,IDHS);
        if(dsPhuhuynh.size()==0){
            if(trangthaiPhuhuynh==1){
                lvChat.setVisibility(view.GONE);
                txtTrangthaiPH.setText("Không có dữ liệu");
            }else {
                new DocJsonPhuhuynh().execute("http://files.pinyeu.com/solienlac/json/json_phuhuynh.php?lop="+TENLOP);
            }
        }
        ChatAdapter chatAdapter = new ChatAdapter(getActivity(),R.layout.item_chat_listview,dsPhuhuynh);
        lvChat.setAdapter(chatAdapter);
    }

    class DocJsonPhuhuynh extends AsyncTask<String, Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            function docjson = new function();
            return docjson.docNoidungtuURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray mangPhuHuynh = new JSONArray(s);
                for(int i=0;i<mangPhuHuynh.length();i++){
                    JSONObject phuhuynh = mangPhuHuynh.getJSONObject(i);
                    int id = phuhuynh.getInt("id");
                    String tenPhuhuynh = phuhuynh.getString("tenPH");
                    String tenHocsinh = phuhuynh.getString("tenHS");
                    if(mangPhuHuynh.length()==0){
                        trangthaiPhuhuynh=1;
                    }
                    if(id !=IDHS){
                        myDatabas_helper.ThemPhuhuynh(id,TENLOP,tenPhuhuynh,tenHocsinh);
                    }
                }
                getDulieuPhuhuynh();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
