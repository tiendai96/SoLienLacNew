package com.hdpro.solienlac;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 07/11/2016.
 */

public class FireBaseIDTask extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        try
        {
            URL url=new URL("http://files.pinyeu.com/solienlac/firebaseCM/dangky.php?Token="+strings[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.getInputStream();
            return true;
        }
        catch (Exception ex)
        {
            Log.e("LOI",ex.toString());
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
