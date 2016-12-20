package com.hdpro.solienlac.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hdpro.solienlac.Fragment.TinnhanFragment;
import com.hdpro.solienlac.Login;
import com.hdpro.solienlac.MainActivity;
import com.hdpro.solienlac.R;
import com.hdpro.solienlac.Sqlite.MyDatabas_Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by User on 07/11/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        hienThiThongBao(remoteMessage.getData().get("thongdiep"),remoteMessage.getData().get("tieude"));
    }
    private void hienThiThongBao(String thongdiep,String tieude) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = sdf.format(c.getTime());
        //
        Intent intent=new Intent(this,Login.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idLayout",1);
        bundle.putString("thongdieptinnhan",thongdiep);
        bundle.putString("thoigiantinnhan",strDate);
        intent.putExtra("DulieuNotification",bundle);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(tieude)
                .setContentText(thongdiep)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, builder.build());
    }
}
