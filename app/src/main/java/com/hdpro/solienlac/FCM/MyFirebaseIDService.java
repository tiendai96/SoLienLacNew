package com.hdpro.solienlac.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.hdpro.solienlac.FireBaseIDTask;
import com.hdpro.solienlac.MainActivity;

/**
 * Created by User on 07/11/2016.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token= FirebaseInstanceId.getInstance().getToken()+"&Idhs="+MainActivity.IDHS;
        luuTokenVaoCSDLRieng(token);
    }
    private void luuTokenVaoCSDLRieng(String token) {
        new FireBaseIDTask().execute(token);
    }
}
