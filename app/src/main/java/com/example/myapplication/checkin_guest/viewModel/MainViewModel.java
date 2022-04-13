package com.example.myapplication.checkin_guest.viewModel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

public class MainViewModel extends ViewModel {
    private final String TAG = LoginViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;

    public MainViewModel(){
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    // activity setting
    public void setParentContext(Activity parentContext){
        mActivityRef = new WeakReference<>(parentContext);
    }

}
