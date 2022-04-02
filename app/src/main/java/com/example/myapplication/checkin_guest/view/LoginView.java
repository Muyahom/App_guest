package com.example.myapplication.checkin_guest.view;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.myapplication.checkin_guest.viewModel.ActionListener;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.Logger;


public class LoginView {

    private final String TAG = LoginView.class.getSimpleName();
    private View mView;
    private ActionListener mActionListener;
    private LifecycleOwner mLifecycleOwner;

    public LoginView(View view, LifecycleOwner lifecycleOwner) {
        this.mView = view;
        this.mLifecycleOwner = lifecycleOwner;
    }

    public void setFirebaseUserLiveData(LiveData<FirebaseUser> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            if (liveData.getValue() == null) {
                //로그인 실패시
                Logger.d(TAG, "Login failed");
                return;
            }
            //로그인 성공시(activity에 notify)
            Logger.d(TAG, "Login success");
            mActionListener.NotifySignInSuccess();
        });
    }

    public void setThrowableUserLiveData(LiveData<Throwable> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            Logger.d(TAG, data.getMessage());
        });
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
}
