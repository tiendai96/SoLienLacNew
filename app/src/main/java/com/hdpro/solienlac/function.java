package com.hdpro.solienlac;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by User on 09/11/2016.
 */

public class function {
    public String docNoidungtuURL(String myURL){
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(myURL);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine())!=null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    public void thongbaoLoi(String text,View view,Context context){
        Snackbar snackbar=Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundEror));
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackView.setLayoutParams(params);
        TextView txtSnack = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        txtSnack.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public void thongbaoThanhcong(String text,View view,Context context){
        Snackbar snackbar=Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundSuccess));
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackView.setLayoutParams(params);
        TextView txtSnack = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        txtSnack.setTextColor(Color.WHITE);
        snackbar.show();
    }
    public void thongbaoTinnhan(String text,View view,Context context){
        Snackbar snackbar=Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundNewMail));
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackView.getLayoutParams();
        params.gravity = Gravity.TOP;
        snackView.setLayoutParams(params);
        TextView txtSnack = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        txtSnack.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
