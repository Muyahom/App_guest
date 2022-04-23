package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.util.Log;

import com.example.myapplication.checkin_guest.callback.GetPushToken;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseMessageExcutor {
    private final String TAG = FirebaseMessageExcutor.class.getSimpleName();
    private GetPushToken getPushToken;
    private String token;


    public void setGetPushToken(GetPushToken getPushToken){
        this.getPushToken = getPushToken;
    }

    public void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Log.d(TAG, "Fetching FCM registration token failed : " + task.getException());
                        return;
                    }
                    Log.d(TAG, "Fetching FCM registration token success");
                    token = task.getResult();
                    Log.d(TAG, token);
                    getPushToken.onSuccess(token);
                });
    }
}
