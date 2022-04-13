package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public class MainViewModel extends ViewModel {
    private final String TAG = LoginViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;
    private FirebaseUser user;

    public MainViewModel(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

    //로그인 체크
    public boolean isLogin(){
        boolean check = false;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        this.user = mAuth.getCurrentUser();
        if(user != null){
            check = true;
        }
        Log.d(TAG, String.valueOf(check));
        return check;
    }
}
